package com.util.core;

import android.app.Application;


/**
 * author xander on  2017/6/9.
 * function
 */

public class UtilApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InitSDK.init(this);
    }
}
