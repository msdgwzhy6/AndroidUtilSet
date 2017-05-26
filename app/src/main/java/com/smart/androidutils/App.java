package com.smart.androidutils;

import android.app.Application;

import com.util.core.InitUtil;

/**
 *author xander on  2017/5/26.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InitUtil.init(this);
    }
}
