package com.smart.androidutils;

import android.content.Intent;
import android.os.Handler;

public class SplashCompatActivity extends BaseCompatActivity {


    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mNoTitle = true;
        mFullScreen = true;
        mHideStatusBar = true;
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashCompatActivity.this,MainCompatActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        },3000);
    }
}
