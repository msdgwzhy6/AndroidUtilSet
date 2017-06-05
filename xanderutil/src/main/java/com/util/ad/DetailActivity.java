package com.util.ad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.util.R;

import static com.util.view.UtilWidget.setWebView;

public class DetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        WebView webView = (WebView) findViewById(R.id.id_splash_web_view);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle= intent.getExtras();
            if (bundle != null) {
                String url = bundle.getString("url");
//                new UtilNoAdWeb(mWebView,"gb2312", url);
                webView.loadUrl(url);
                webView.getSettings().setJavaScriptEnabled(true);
                setWebView(webView);
            }
        }
    }
/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
//            SplashADView.getInstance().exit();
            return true;
        } else if(keyCode == KeyEvent.KEYCODE_MENU) {
            //监控/拦截菜单键
        } else if(keyCode == KeyEvent.KEYCODE_HOME) {
            //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
