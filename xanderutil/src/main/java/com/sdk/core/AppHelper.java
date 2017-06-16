package com.sdk.core;

import android.app.Application;


/**
 * author xander on  2017/6/9.
 * function 使用前可以先继承这个类
 */

public class AppHelper extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InitSDK.init(this);
    }
}
