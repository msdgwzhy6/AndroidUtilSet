package com.sdk.util.logger;

import android.util.Log;

/**
 * ================================================
 * 描    述：日志的工具类
 * ================================================
 */
public class JJLogger {
    private static boolean isLogEnable = true;

    public static String tag = "jjsdklog";

    public static void debug(boolean isEnable) {
        isLogEnable = isEnable;
    }

    public static void d(String msg) {
        d(tag, msg);
    }

    public static void e(String msg) {
        e(tag, msg);
    }

    public static void i(String msg) {
        i(tag, msg);
    }

    public static void v(String msg) {
        v(tag, msg);
    }

    public static void w(String msg) {
        w(tag, msg);
    }


    public static void e(String tag, String msg) {
        if (isLogEnable) Log.e(tag, msg);
    }

    public static void e(Throwable t) {
        if (isLogEnable) t.printStackTrace();
    }

    public static void d(String tag, String msg) {
        if (isLogEnable) Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isLogEnable) Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isLogEnable) Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isLogEnable) Log.w(tag, msg);
    }

}
