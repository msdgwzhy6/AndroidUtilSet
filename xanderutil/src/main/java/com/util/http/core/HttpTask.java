package com.util.http.core;

import android.os.AsyncTask;
import android.os.Handler;

import com.util.http.core.callback.OnHttpCallback;
import com.util.phone.UtilNet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.util.http.core.ConHttp.HTTP_TYPE_GET;
import static com.util.http.core.HttpHelper.HTTP_TYPE;
import static com.util.http.core.HttpHelper.mHttpTimeout;

/**
 * author xander on  2017/5/31.
 * function  处理具体的业务逻辑 ，获取字符串
 */

public class HttpTask<T> extends AsyncTask<String, Void, T> {
    private OnHttpCallback mHttpCallback;
    public HttpTask(OnHttpCallback httpCallback) {
        mHttpCallback = httpCallback;
    }
    private Handler mHandler = new Handler();

    @Override
    @SuppressWarnings("unchecked")
    protected T doInBackground(String... params) {
        if (isCancelled()) {
            return null;
        }
        if (params == null || params.length == 0) {
            return null;
        }
        HttpURLConnection httpUrlCon = null;
        try {
            URL httpUrl = new URL(params[0]);

            httpUrlCon = (HttpURLConnection) httpUrl.openConnection();
            httpUrlCon.setConnectTimeout(mHttpTimeout);// 建立连接超时时间
            httpUrlCon.setReadTimeout(mHttpTimeout);//数据传输超时时间，很重要，必须设置。
            httpUrlCon.setDoInput(true); // 向连接中写入数据
            httpUrlCon.setDoOutput(true); // 从连接中读取数据
            httpUrlCon.setUseCaches(false); // 禁止缓存
            httpUrlCon.setInstanceFollowRedirects(true);
            switch (HTTP_TYPE) {
                case HTTP_TYPE_GET:
                    httpUrlCon.setRequestMethod("GET");// 设置请求类型为
                    break;
               /* case HTTP_TYPE_POST:
                    httpUrlCon.setRequestMethod("POST");// 设置请求类型为
                    DataOutputStream out = new DataOutputStream(httpUrlCon.getOutputStream()); // 获取输出流
                    out.write(mParams.getBytes("utf-8"));// 将要传递的数据写入数据输出流,不要使用out.writeBytes(param); 否则中文时会出错
                    out.flush(); // 输出缓存
                    out.close(); // 关闭数据输出流
                    break;*/
                default:
                    break;

            }
            if (!UtilNet.isActiveConnected(true)) {
                return null;
            }
            httpUrlCon.connect();
            //check the result of connection
            if (httpUrlCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream  = httpUrlCon.getInputStream();
                return (T) mHttpCallback.onThread(inputStream);
            }

        } catch (final IOException e) {
            e.printStackTrace();
            //如果需要处理超时，可以在这里写
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mHttpCallback.onFailure(e);
                }
            });

        } finally {
            if (httpUrlCon != null) {
                httpUrlCon.disconnect(); // 断开连接
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onPostExecute(T  s) {
        super.onPostExecute(s);
        if (s != null) {
            mHttpCallback.onSuccess(s);
        }
    }
}
