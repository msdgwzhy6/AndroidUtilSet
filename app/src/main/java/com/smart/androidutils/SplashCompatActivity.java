package com.smart.androidutils;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.util.ad.SplashADView;

import static com.util.view.UtilWidget.getView;

public class SplashCompatActivity extends BaseCompatActivity {
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
        SplashADView.getInstance().initADView().setADTime(100).setSplashViewCallback(new SplashADView.SplashViewCallback() {
            @Override
            public void onSplashViewFailure(Exception e) {
                //广告加载失败的 操作
                // startActivity(new Intent(SplashCompatActivity.this,MainCompatActivity.class));
            }

            @Override
            public void onLoadADView(View view) {
                ViewGroup viewGroup = getView(SplashCompatActivity.this, R.id.activity_splash);
                viewGroup.addView(view, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                Log.i("xxx", "onLoadADView" );
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashCompatActivity.this,MainCompatActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }

/*    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashCompatActivity.this,MainCompatActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        },10000);
    }*/
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
        startActivity(new Intent(SplashCompatActivity.this,MainCompatActivity.class));
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
