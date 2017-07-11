package com.sdk.util.http;

import android.text.TextUtils;

import com.sdk.util.http.core.HttpTask;
import com.sdk.util.http.core.callback.OnHttpCallback;
import com.sdk.util.http.core.callback.OnStringCallback;
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

    public UtilHttpString setStringCallback(final OnStringCallback stringCallback){
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
                            //TODO need to test
                            JJLogger.i("interceptFlag","requestURL:" +mUrl +"canceled");
                            //跳出任务
                            return null;
                        }
                        stringBuilder.append(line).append("\n");
                    }
                    return stringBuilder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }
            @Override
            public void onUISuccess(String s) {
                if (!TextUtils.isEmpty(s)) {
                    stringCallback.onStringSuccess(s);
                }else {

                    stringCallback.onRequestFailure(new NullPointerException(""), "");
                    JJLogger.i("onUISuccess","UtilHttpString:onUISuccess :"+"没有得到数据 :" +"");
                }
            }
            @Override
            public void onFailure(Exception e, String errorCode) {
                stringCallback.onRequestFailure(e, errorCode);
                JJLogger.i("onFailure","UtilHttpString:onFailure :请求的url 为："+mUrl);
            }
        }).startConcurrence();
        return this;
    }
}
