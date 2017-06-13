package com.sdk.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.sdk.util.logger.JJLogger;
import com.sdk.util.manager.ManagerActivity;


/**
 * author xander on  2017/5/23.
 */

public final class InitSDK {
    protected static Context context;
    /**
     * 初始化工具类
     * 必须在application中初始化
     *  context 上下文
     */
    public static void init(Application context) {
        JJLogger.debug(true);
        InitSDK.context = context.getApplicationContext();
        activityLifecycleCallbacks(context);
    }
    /************************************************************
     *Author; 龙之游 @ xu 596928539@qq.com
     * 时间:2017/1/13 12:03
     * 注释: 用于获取 栈顶 activity  //// Activity的生命周期事件进行集中处理
     ***********************************************************
     * param context*/
    private static void activityLifecycleCallbacks(Application context) {
        context.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                //监听onActivityResumed()方法
                ManagerActivity.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
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
