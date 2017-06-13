package com.util.http.core;

import android.os.AsyncTask;
import android.os.Handler;

import com.util.http.core.callback.OnHttpCallback;
import com.util.logger.JJLogger;
import com.util.phone.UtilNet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static com.util.http.core.ConfigHttp.HTTP_TYPE_POST;
import static com.util.http.core.ConfigHttp.httpType;
import static com.util.http.core.ConfigHttp.mCharset;
import static com.util.http.core.ConfigHttp.mHttpTimeout;


/**
 * author xander on  2017/5/31.
 * function  处理具体的业务逻辑 ，获取字符串
 */

public class HttpTask<T> extends AsyncTask<String, Void, T> {
    private OnHttpCallback mHttpCallback;
    private Map<String ,String > mParams;//接受请求参数


    public HttpTask(Map<String,String> params, OnHttpCallback httpCallback) {
        mParams = params;
        mHttpCallback = httpCallback;
    }
    public HttpTask( OnHttpCallback httpCallback) {
        mHttpCallback = httpCallback;
    }
    private Handler mHandler = new Handler();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    @SuppressWarnings("unchecked")
    protected T doInBackground(String... urlStr) {

        StringBuilder sbuf = new StringBuilder();
          /*
        * 处理请求参数
        * */
        if (mParams != null) {
            for (Map.Entry<String,String> entry : mParams.entrySet()) {
                sbuf.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            mParams = null;
            if(sbuf.length()>0){
                sbuf.deleteCharAt(sbuf.length()-1);
            }
            JJLogger.i("请求参数 ："+sbuf.toString());
            urlStr[0] = urlStr[0] + "?" + sbuf.toString();
        }
        HttpURLConnection httpUrlCon = null;

        try {
            URL httpUrl = new URL(urlStr[0]);
            JJLogger.i(urlStr[0]);
            httpUrlCon = (HttpURLConnection) httpUrl.openConnection();
            httpUrlCon.setConnectTimeout(mHttpTimeout);// 建立连接超时时间
            httpUrlCon.setReadTimeout(mHttpTimeout);//数据传输超时时间，很重要，必须设置。
            switch (httpType) {
                case ConfigHttp.HTTP_TYPE_GET:
                    httpUrlCon.setRequestMethod("GET");// 设置请求类型为
                    break;
                case HTTP_TYPE_POST:
                    // 1、重新对请求报文进行  编码
                    byte[] postData = null;
                    try {
                        postData = sbuf.toString().getBytes(mCharset);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    httpUrlCon.setDoInput(true); // 向连接中写入数据
                    httpUrlCon.setDoOutput(true); // 从连接中读取数据
                    httpUrlCon.setUseCaches(false); // 禁止缓存
                    httpUrlCon.setInstanceFollowRedirects(true);
                    httpUrlCon.setRequestMethod("POST");// 设置请求类型为
                    //B、指定报文头【Content-type】、【Content-length】 与 【Keep-alive】
                    httpUrlCon.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
                    if (postData != null) {
                        httpUrlCon.setRequestProperty("Content-length", String.valueOf(postData.length));
                    }
                    httpUrlCon.setRequestProperty("Keep-alive", "false");
                    httpUrlCon.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

                    DataOutputStream out = new DataOutputStream(httpUrlCon.getOutputStream()); // 获取输出流
                    if (postData != null) {
                        out.write(postData);// 将要传递的数据写入数据输出流
                    }
                    out.flush(); // 输出缓存
                    out.close(); // 关闭数据输出流
                    break;
                default:
                    break;

            }
            if (!UtilNet.isActiveConnected(true)) {
                return null;
            }
            httpUrlCon.connect();
            //check the result of connection
            /*if (httpUrlCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream  = httpUrlCon.getInputStream();
                return (T) mHttpCallback.onThread(inputStream);
            }*/
            InputStream inputStream  = httpUrlCon.getInputStream();
            return (T) mHttpCallback.onThread(inputStream);

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
