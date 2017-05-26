package com.smart.androidutils;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/*
* activity的基类
* */
public abstract class BaseCompatActivity extends AppCompatActivity {
    protected String mAvtivitytTitle;
    protected final String TAG = this.getClass().getSimpleName();

    protected boolean mIsAllowScreenRoate;//是否可以横屏
    protected boolean mNoTitle;//是否隐藏标题栏
    protected boolean mHideStatusBar;
    protected boolean mFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(initLayout());

        setActivityTitle();
        setTitle(mAvtivitytTitle);
        if (mIsAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//只能是竖屏
        }

        initView();
        initData();

        bindDataToView();

        setListener();
    }

    /**
     * 设置activity的标题
     */
    protected void setActivityTitle() {    }
    protected abstract int initLayout() ;
    protected abstract void initView();
    protected abstract void initData();


    /**
     * 没有写成抽象 考虑到 MVP 等开发模式
     */
    protected void bindDataToView(){};

    protected void setListener(){}

}