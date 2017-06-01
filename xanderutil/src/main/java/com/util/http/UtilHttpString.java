package com.util.http;

import com.util.http.core.HttpHelper;
import com.util.http.core.HttpTask;
import com.util.http.core.callback.IStringCallback;
import com.util.http.core.callback.OnHttpCallback;

import java.io.InputStream;

import static com.util.string.UtilString.is2String;

/**
 * author xander on  2017/5/31.
 * function 处理字符串
 */

public class UtilHttpString extends HttpHelper<UtilHttpString> {
    private static  String mCharset = "utf-8";
    @SuppressWarnings("unchecked")
    public UtilHttpString setCharset(String  charset){
        mCharset  = charset;
        return   this;
    }

    @SuppressWarnings("unchecked")
    public void initHttpStringCallback(final IStringCallback stringCallback){
        new HttpTask<String>(new OnHttpCallback<String>() {
            @Override
            public String onThread(InputStream inputStream) {
                return is2String(inputStream,mCharset);
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
