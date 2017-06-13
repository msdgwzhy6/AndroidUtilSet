package com.util.http.core;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import static com.util.http.core.ConfigHttp.httpType;
import static com.util.http.core.ConfigHttp.mHttpTimeout;


/**
 * author xander on  2017/5/31.
 * function 暂未添加https支持，未添加post方式
 */

public class HttpHelper <T extends HttpHelper>{
    protected String mUrl;

    private T instance;
    protected Map<String ,String > mParams;

    @SuppressWarnings("unchecked")
    public HttpHelper() {
        instance = (T) this;
    }

    public T get(String url){
        mUrl = url;
        httpType = ConfigHttp.HTTP_TYPE_GET;
        return instance;
    }
    public T post(String url){
        mUrl = url;
        httpType = ConfigHttp.HTTP_TYPE_POST;
        return instance;
    }
    public T addParam(String key,String value){
        if (TextUtils.isEmpty(key)) {
            return instance;
        }
        if (mParams == null) {
            mParams = new HashMap<>();
        }
        mParams.put(key,value);

        return instance;
    }
    public T addParam(Map<String ,String > params){
        mParams = params;
        return instance;
    }

    public T setTimeout(int timeout){
        mHttpTimeout = timeout;
        return instance;
    }

}
