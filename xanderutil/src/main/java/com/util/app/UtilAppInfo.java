package com.util.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
/**
 * @author xander on  2017/5/25.
 * @function
 */

public class UtilAppInfo{
    private Handler mHandler = new Handler();
    private AppBeanCallback<String, Drawable> mAppBeanCallback;
    private boolean mIncludeSysApp;
    private PackageManager mPackageManager;

    public UtilAppInfo(boolean includeSysApp, PackageManager packageManager, AppBeanCallback<String, Drawable> appBeanCallback) {
        mIncludeSysApp = includeSysApp;
        if (packageManager != null) {
            mPackageManager = packageManager;
        }else {
            throw new NullPointerException("PackageManager should not be null");
        }
        if (appBeanCallback != null) {
            mAppBeanCallback = appBeanCallback;
        }else {
            throw new NullPointerException("AppBeanCallback should not be null");
        }

        scanLocalInstallAppList();
    }

    private void scanLocalInstallAppList() {
        final List<String> strings = new ArrayList<>();
        final List<Drawable> drawableList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<PackageInfo> packageInfos = mPackageManager.getInstalledPackages(0);
                    for (int i = 0; i < packageInfos.size(); i++) {
                        PackageInfo packageInfo = packageInfos.get(i);
                        //过滤掉系统app
            /*if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                continue;
            }*/
                        strings.add(packageInfo.packageName);
                        drawableList.add(packageInfo.applicationInfo.loadIcon(mPackageManager));
                        if (packageInfo.applicationInfo.loadIcon(mPackageManager) == null) {
                        }
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAppBeanCallback.result(strings,drawableList);
                        }
                    });

                }catch (Exception e){
                    mAppBeanCallback.failure(e);
                }
            }
        }).start();

    }


}