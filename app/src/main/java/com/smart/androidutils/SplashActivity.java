package com.smart.androidutils;

import android.content.Intent;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import static com.sdk.util.view.UtilWidget.getView;

public class SplashActivity extends BaseActivity {
    private RelativeLayout mRelativeLayout;
    @Override
    protected void initSystemUI() {
        super.initSystemUI();
        mNoTitle = true;
        mFullScreen = true;
        mHideStatusBar = true;
    }
    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }
    @Override
    protected void initView() {
        mRelativeLayout = getView(this,R.id.ad_pos);
    }
    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        },2000);
    }
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
        return true;
    } else if(keyCode == KeyEvent.KEYCODE_MENU) {
        //监控/拦截菜单键
    } else if(keyCode == KeyEvent.KEYCODE_HOME) {
        //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
    }
    return super.onKeyDown(keyCode, event);
}
}
