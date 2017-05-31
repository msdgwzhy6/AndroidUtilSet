package com.util.http.core;

import android.util.Log;

import static com.util.http.ConHttp.HTTP_TYPE_GET;

/**
 * author xander on  2017/5/31.
 * function 负责初始化和分发请求数据
 */

public class HttpHelper <T extends HttpHelper>{
    protected String mUrl;
    public static int HTTP_TYPE;
    public static  String mCharset = "utf-8";
    public static int mHttpTimeout = 2000;
    public HttpHelper() {

    }
    public T get(String url){
        mUrl = url;
        HTTP_TYPE = HTTP_TYPE_GET;
        return (T) this;
    }
    public T addParam(String key,String value){
        switch (HTTP_TYPE){
            case HTTP_TYPE_GET:
                if (!mUrl.contains("?")) {
                    mUrl = mUrl + "?" + key +"="+value;
                } else  {
                    mUrl = mUrl + "&"+ key +"="+value;
                }
                Log.i("xxx", "addParam" +mUrl);
                break;
        }
        return (T) this;
    }
    public T setCharset(String  charset){
        mCharset  = charset;
        return (T) this;
    }
    public T setTimeout(int timeout){
        mHttpTimeout = timeout;
        return (T) this;
    }

}
