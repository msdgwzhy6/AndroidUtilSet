package com.smart.androidutils.activity.app.bean;

import android.graphics.drawable.Drawable;

import com.util.viewholder.CommonAdapter;


/**
 * @author xander on  16/3/3.
 * @function
 */
public class MyAppInfoBean implements CommonAdapter.IBaseBean{
    private Drawable image;
    private String appName;

    public MyAppInfoBean(Drawable image, String appName) {
        this.image = image;
        this.appName = appName;
    }
    public MyAppInfoBean() {

    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
