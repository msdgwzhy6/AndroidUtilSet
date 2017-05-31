package com.util.http.core.callback;

import java.io.InputStream;

public interface OnHttpCallback<T>{

    /*
    * 处理得到的输入流,运行在子线程
    * */

    T onThread(InputStream inputStream);

    /*
    * 返回到UI线程
    * */
    void onSuccess(T t);

    /*
    * 处理失败
    * */
    void onFailure(Exception e);
}
