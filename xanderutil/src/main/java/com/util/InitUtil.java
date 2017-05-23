package com.util;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * @author xander on  2017/5/23.
 * @function init Util
 */

public class InitUtil {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        InitUtil.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }
}
