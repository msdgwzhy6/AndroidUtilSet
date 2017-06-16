package com.sdk.util.http.core;

import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;

import com.sdk.util.http.core.callback.OnHttpCallback;
import com.sdk.util.logger.JJLogger;
import com.sdk.util.phone.UtilNet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.sdk.util.common.ErrorCode.CODE_REQUEST_NET;
import static com.sdk.util.http.core.ConfigHttp.HTTP_TYPE_GET;
import static com.sdk.util.http.core.ConfigHttp.HTTP_TYPE_POST;
import static com.sdk.util.http.core.ConfigHttp.httpType;
import static com.sdk.util.http.core.ConfigHttp.mHttpTimeout;

/**
 * author xander on  2017/5/31.
 * function  处理具体的业务逻辑 ，获取字符串
 */
@SuppressWarnings("unchecked")
public class HttpTask<TYPE,RETURN> extends AsyncTask<String, Void, RETURN> {
    private OnHttpCallback mHttpCallback;
    private Map<String ,String > mParams;//接受请求参数
    private Map<String ,String > mHeads;//接受请求参数
    private  String mCharset = "utf-8";
    private Handler mHandler = new Handler();
    protected String mUrl;
    private TYPE instance;
    protected boolean interceptFlag = false;//取消标志,默认不取消

    public HttpTask() {
        instance = (TYPE) this;
    }
    public TYPE get(String url){
        mUrl = url;
        httpType = ConfigHttp.HTTP_TYPE_GET;
        return instance;
    }
    public TYPE post(String url){
        mUrl = url;
        httpType = ConfigHttp.HTTP_TYPE_POST;
        return instance;
    }
    public TYPE addParams(Map<String ,String > params){
        mParams = params;
        return instance;
    }
    public TYPE addParams(String key, String value){
        if (TextUtils.isEmpty(key)) {
            return instance;
        }
        if (mParams == null) {
            mParams = new HashMap<>();
        }
        mParams.put(key,value);

        return instance;
    }

    public TYPE addHeads(String key, String value){
        if (TextUtils.isEmpty(key)) {
            return instance;
        }
        if (mHeads == null) {
            mHeads = new HashMap<>();
        }
        mHeads.put(key,value);

        return instance;
    }

    public TYPE addHeads(Map<String ,String > heads){
        mHeads = heads;
        return instance;
    }

    public TYPE setTimeout(int timeout){
        mHttpTimeout = timeout;
        return instance;
    }

    public HttpTask<TYPE,RETURN> setParams(Map<String ,String > params){
        mParams = params;
        return this;
    }
    public HttpTask<TYPE,RETURN> setOnHttpCallback(OnHttpCallback httpCallback){
        mHttpCallback = httpCallback;
        return this;
    }
    public HttpTask<TYPE,RETURN> setHeads(Map<String ,String > heads){
        mHeads = heads;
        return this;
    }
    public HttpTask<TYPE,RETURN> setCharset(String charset){
        mCharset = charset;
        return this;
    }
    public void cancle(){
        interceptFlag = true;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected RETURN doInBackground(String... urlStr) {

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
            if (httpType==HTTP_TYPE_GET) {
                urlStr[0] = urlStr[0] + "?" + sbuf.toString();
            }
            JJLogger.i(urlStr[0]);
        }
        HttpURLConnection httpUrlCon = null;

        try {
            URL httpUrl = new URL(urlStr[0]);
            JJLogger.i(urlStr[0]);
            httpUrlCon = (HttpURLConnection) httpUrl.openConnection();
            httpUrlCon.setConnectTimeout(mHttpTimeout);// 建立连接超时时间
            httpUrlCon.setReadTimeout(mHttpTimeout);//数据传输超时时间，很重要，必须设置。

            //设置请求头
            if (mHeads != null) {
                for (Map.Entry<String,String> entry : mHeads.entrySet()) {
                    httpUrlCon.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            switch (httpType) {
                case ConfigHttp.HTTP_TYPE_GET:
                    httpUrlCon.setRequestMethod("GET");// 设置请求类型为
                    break;
                case HTTP_TYPE_POST:
                    // 1、重新对请求报文进行  编码
                    httpUrlCon.setRequestMethod("POST");// 设置请求类型为
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
                    if (postData != null) {
                        httpUrlCon.setRequestProperty("Content-length", String.valueOf(postData.length));
                    }
                    DataOutputStream out = null;
                    if (!interceptFlag) {
                        out = new DataOutputStream(httpUrlCon.getOutputStream()); // 获取输出流
                    }else {
                        return null;
                    }
                    if (postData != null) {
                        out.write(postData);// 将要传递的参数写入数据输出流
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
            if (!interceptFlag) {//用户没有取消
                httpUrlCon.connect();
            }else {
                return null;
            }
            //check the result of connection
            if (httpUrlCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = null;
                if (!interceptFlag){
                    inputStream  = httpUrlCon.getInputStream();

                }else {
                    interceptFlag = false;
                    JJLogger.i("HttpTask"+"取消连接");
                }
                return (RETURN) mHttpCallback.onChildThread(inputStream);
            }else {
                mHttpCallback.onFailure(httpUrlCon.getResponseCode());
            }
        } catch (final IOException e) {
//            e.printStackTrace();
            //如果需要处理超时，可以在这里写
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mHttpCallback.onFailure(CODE_REQUEST_NET);
                }
            });

        } finally {
            if (httpUrlCon != null) {
                httpUrlCon.disconnect(); // 断开连接
                JJLogger.i("断开连接");
               interceptFlag = false;
            }
        }
        return null;
    }

    @Override
     protected void onPostExecute(RETURN  s) {
        super.onPostExecute(s);
        if (s == null) {
            JJLogger.i("没有得到数据");
        }
        mHttpCallback.onUISuccess(s);
    }
}