package com.smart.androidutils;

import android.app.Application;

import com.util.core.InitSDK;

/**
 *author xander on  2017/5/26.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InitSDK.init(this);
    }
}
