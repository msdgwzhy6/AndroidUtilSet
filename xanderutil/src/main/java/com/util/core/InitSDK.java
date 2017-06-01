package com.util.core;

import android.content.Context;

/**
 * @author xander on  2017/5/23.

 */

public final class InitSDK {
    protected static Context context;
    /**
     * 初始化工具类
     *
     *  context 上下文
     */
    public static void init(Context context) {
        InitSDK.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     *  ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}
