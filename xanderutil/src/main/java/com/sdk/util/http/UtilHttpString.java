package com.sdk.util.http;

import com.sdk.util.http.core.ConfigHttp;
import com.sdk.util.http.core.HttpHelper;
import com.sdk.util.http.core.HttpTask;
import com.sdk.util.http.core.callback.IStringCallback;
import com.sdk.util.http.core.callback.OnHttpCallback;
import com.sdk.util.string.UtilString;

import java.io.InputStream;


/**
 * author xander on  2017/5/31.
 * function 处理字符串
 */

public class UtilHttpString extends HttpHelper<UtilHttpString> {

    @SuppressWarnings("unchecked")
    public UtilHttpString setCharset(String  charset){
        ConfigHttp.mCharset  = charset;
        return   this;
    }

    @SuppressWarnings("unchecked")
    public void initHttpStringCallback(final IStringCallback stringCallback){
        new HttpTask<String>(mParams,new OnHttpCallback<String>() {
            @Override
            public String onThread(InputStream inputStream) {
                return UtilString.is2String(inputStream, ConfigHttp.mCharset);
            }
            @Override
            public void onSuccess(String s) {
                stringCallback.onStringSuccess(s);
            }
            @Override
            public void onFailure(Exception e) {
                stringCallback.onFailure(e);
            }
        }).execute(mUrl);
    }
}
