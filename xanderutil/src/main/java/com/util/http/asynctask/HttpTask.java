package com.util.http.asynctask;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.util.http.ConHttp.HTTP_TYPE_GET;
import static com.util.http.HttpHelper.mHttpTimeout;

/**
 * author xander on  2017/5/31.
 * function  处理具体的业务逻辑 ，获取字符串
 */

public class HttpTask extends AsyncTask<String, Void, InputStream> {
    private Integer mType = HTTP_TYPE_GET;

    private OnHttpCallback mHttpCallback;
    public HttpTask(Integer type,OnHttpCallback httpCallback) {
        if (type != null) {
            mType = type;
        }
        mHttpCallback = httpCallback;
    }


    @Override
    protected InputStream doInBackground(String... params) {
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
            switch (mType) {
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
            httpUrlCon.connect();
            //check the result of connection
            if (httpUrlCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return httpUrlCon.getInputStream();
            }

        } catch (IOException e) {
            e.printStackTrace();
            //如果需要处理超时，可以在这里写
            mHttpCallback.onFailure(e);
        } finally {
            if (httpUrlCon != null) {
                httpUrlCon.disconnect(); // 断开连接
            }
        }
        return null;
    }

        @Override
    protected void onPostExecute(InputStream s) {
        super.onPostExecute(s);
        mHttpCallback.onSuccess(s);
    }
}
