package com.sdk.util.http;

import android.text.TextUtils;

import com.sdk.util.common.ErrorCode;
import com.sdk.util.http.core.HttpTask;
import com.sdk.util.http.core.callback.OnHttpCallback;
import com.sdk.util.http.core.callback.StringCallback;
import com.sdk.util.logger.JJLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * author xander on  2017/5/31.
 * function 处理字符串
 */

public class UtilHttpString extends HttpTask<UtilHttpString,String> {
    private  String mCharset = "utf-8";
    @SuppressWarnings("unchecked")
    public UtilHttpString setCharset(String  charset){
        mCharset  = charset;
        return   this;
    }

    public UtilHttpString setStringCallback(final StringCallback stringCallback){
        setOnHttpCallback(new OnHttpCallback<String>() {
            @Override
            public String onChildThread(InputStream inputStream) {
                InputStreamReader isr;
                if (inputStream == null) {
                    return null;
                }
                try {
                    isr = new InputStreamReader(inputStream, mCharset);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        if(interceptFlag ){
                            //用户取消
                            interceptFlag = true;
                            JJLogger.i("-------用户取消请求");
                            //跳出任务
                            return "用户取消请求";
                        }
                        stringBuilder.append(line).append("\n");
                    }
                    return stringBuilder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    return e.getMessage();
                }

            }
            @Override
            public void onUISuccess(String s) {
                if (!TextUtils.isEmpty(s)) {
                    stringCallback.onStringSuccess(s);
                }else {
                    JJLogger.i("没有得到数据");
                    stringCallback.onRequestFailure(ErrorCode.CODE_REQUEST_DATA);
                }
            }
            @Override
            public void onFailure(int errorCode) {
                stringCallback.onRequestFailure(errorCode);
            }
        }).execute(mUrl);
        return this;
    }

}
