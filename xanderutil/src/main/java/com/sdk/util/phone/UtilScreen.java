package com.sdk.util.phone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;

import com.sdk.core.InitSDK;

/**
 * author xander on  2017/5/26.
 * function 设置全屏等信息
 */

public final class UtilScreen {
    /**
     * 获取状态栏高度
     *
     * param context context
     * return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * activity activity
     * Bitmap
     */
    public static Bitmap captureWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int statusBarHeight = getStatusBarHeight(activity);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight);
        view.destroyDrawingCache();
        return ret;
    }
    public static int getScreenHight(){
        DisplayMetrics dm2 = InitSDK.getContext().getResources().getDisplayMetrics();

        return dm2.heightPixels;

    }
    public static int getScreenWidth(){
        DisplayMetrics dm2 = InitSDK.getContext().getResources().getDisplayMetrics();
        return dm2.widthPixels;
    }
    public static String  getScreenWH(){
        DisplayMetrics dm2 = InitSDK.getContext().getResources().getDisplayMetrics();

        return dm2.widthPixels + " X "+dm2.heightPixels;
    }
}
