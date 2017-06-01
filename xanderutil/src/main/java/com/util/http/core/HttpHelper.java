package com.util.http.core;

import android.text.TextUtils;

import static com.util.http.core.ConHttp.HTTP_TYPE_GET;

/**
 * author xander on  2017/5/31.
 * function 负责初始化和分发请求数据
 */

public class HttpHelper <T extends HttpHelper>{
    protected String mUrl;
    static int HTTP_TYPE;
    static int mHttpTimeout = 2000;
    private T instance;
    @SuppressWarnings("unchecked")
    public HttpHelper() {
        instance = (T) this;
    }

    public T get(String url){
        mUrl = url;
        HTTP_TYPE = HTTP_TYPE_GET;
        return instance;
    }
    public T addParam(String key,String value){
        if (TextUtils.isEmpty(key)) {
            return instance;
        }
        switch (HTTP_TYPE){
            case HTTP_TYPE_GET:
                if (!mUrl.contains("?")) {
                    mUrl = mUrl + "?" + key +"="+value;
                } else  {
                    mUrl = mUrl + "&"+ key +"="+value;
                }
                break;
        }
        return instance;
    }

    public T setTimeout(int timeout){
        mHttpTimeout = timeout;
        return instance;
    }

}
