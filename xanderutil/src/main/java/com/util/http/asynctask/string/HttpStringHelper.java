package com.util.http.asynctask.string;

import com.util.http.core.HttpHelper;
import com.util.http.core.HttpTask;
import com.util.http.core.callback.OnHttpCallback;
import com.util.http.core.callback.IStringCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * author xander on  2017/5/31.
 * function 处理字符串
 */

public class HttpStringHelper extends HttpHelper<HttpStringHelper> {

    @SuppressWarnings("unchecked")
    public void initHttpStringCallback(final IStringCallback stringCallback){
        new HttpTask<String>(new OnHttpCallback<String>() {
            @Override
            public String onThread(InputStream inputStream) {
                InputStreamReader isr;
                try {
                    isr = new InputStreamReader(inputStream,mCharset);

                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();
                    while((line = br.readLine()) != null)
                    {
                        stringBuilder.append(line).append("\n");
                    }
                    return stringBuilder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    stringCallback.onFailure(e);
                }

                return null;
            }

            @Override
            public void onSuccess(String s) {
                stringCallback.onStringResult(s);
            }

            @Override
            public void onFailure(Exception e) {
                stringCallback.onFailure(e);
            }
        }).execute(mUrl);
    }
}
