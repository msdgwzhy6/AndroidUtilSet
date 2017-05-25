package com.util.view;

import android.app.Activity;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by smart on 2017/4/28.
 */
/*
* @function:
* Encapsulate findViewById to avoid forced type conversions
* */
public class UtilWidget {
    public static <V extends View> V getView(Activity activity , int itemViewId){
        return (V) activity.findViewById(itemViewId);
    }
    public static <V extends View> V getView(View convertView, int itemViewId){
        return (V) convertView.findViewById(itemViewId);
    }
    /**
     * 点击动画效果
     *
     * @param view 的透明度变化
     */
    public static void setViewAlphaAnimation(View view) {
        AlphaAnimation alphaAni = new AlphaAnimation(0.05f, 1.0f);
        alphaAni.setDuration(100);                // 设置动画效果时间
        view.startAnimation(alphaAni);        // 添加光效动画到VIew
    }

}
