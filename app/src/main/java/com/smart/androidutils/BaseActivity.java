package com.smart.androidutils;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/*
* activity的基类
* */
public abstract class BaseActivity extends AppCompatActivity {
    protected String mAvtivitytTitle;
    protected final String TAG = this.getClass().getSimpleName();

    protected boolean mIsAllowScreenRoate;//是否可以横屏
    protected boolean mNoTitle;//是否隐藏标题栏
    protected boolean mHideStatusBar;
    protected boolean mFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSystemUI();
        if (mFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(initLayout());

        setActivityTitle();
        setTitle(mAvtivitytTitle);
        if (mIsAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//只能是竖屏
        }
        initView();
        initData();
        handleIntent();
        bindDataToView();

        setListener();
    }

    protected void handleIntent() {

    }

    protected void initSystemUI() {

    }

    /**
     * 设置activity的标题
     */
    protected void setActivityTitle() {    }
    /*
    * 初始化布局
    * */
    protected abstract int initLayout() ;
    /*
    * 初始化view
    * */
    protected void initView(){};
    /*
    * 初始化数据
    * */
    protected void initData(){};


    /**
     * 没有写成抽象 考虑到 MVP 等开发模式
     */
    protected void bindDataToView(){};

    protected void setListener(){}

}
