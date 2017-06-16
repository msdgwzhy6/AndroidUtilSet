package com.smart.androidutils.activity.app.bean;

import android.graphics.drawable.Drawable;


/**
 * @author xander on  16/3/3.
 * @function
 */
public class MyAppInfoBean implements java.io.Serializable {
    private Drawable AppIcon;//App图标
    private String appName;//App名字

    public MyAppInfoBean(Drawable image, String appName) {
        this.AppIcon = image;
        this.appName = appName;
    }
    public MyAppInfoBean() {

    }

    public Drawable getAppIcon() {
        return AppIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.AppIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
