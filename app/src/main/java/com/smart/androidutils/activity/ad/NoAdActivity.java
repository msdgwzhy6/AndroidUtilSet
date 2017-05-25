package com.smart.androidutils.activity.ad;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;
import com.util.view.WebViewNoAd;

import static com.smart.androidutils.constant.Constant.urlMR;
import static com.smart.dialog_library.Utils.setViewAlphaAnimation;
import static com.util.view.UtilWidget.getView;


public class NoAdActivity extends BaseActivity {

    private WebView mIdWebView;
    private Button mButtonOff;
    private Button mButtonOn;

    @Override
    protected int initLayout() {
        setTitle(getResources().getString(R.string.act_no_ad_off));
        return R.layout.activity_no_ad;

    }

    @Override
    protected void initView() {
        mIdWebView = getView(this,R.id.id_no_ad_web_view);
        mButtonOff = getView(this,R.id.id_no_ad_off);
        mButtonOn = getView(this,R.id.id_no_ad_on);
    }

    @Override
    protected void initData() {
        mIdWebView.loadUrl(urlMR);
        setWebView(mIdWebView);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mButtonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                setViewAlphaAnimation(mButtonOff);
                setTitle(getResources().getString(R.string.act_no_ad_off));
                mIdWebView.loadUrl(urlMR);
            }
        });
        mButtonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                setViewAlphaAnimation(mButtonOn);
                setTitle(getResources().getString(R.string.act_no_ad_on));
                new WebViewNoAd(mIdWebView,"gb2312").execute(urlMR);
            }
        });
    }
    private void setWebView(final WebView webView) {
        webView.requestFocus();
        WebSettings webSettings = webView.getSettings();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setTextZoom(100);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        //设置 缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        //不在webiew中保存密码
        webSettings.setSaveFormData(false);
        //设置数据库缓存路径
        //开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }


            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//
                return super.shouldInterceptRequest(view, request);
            }


        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                NoAdActivity.this.startActivity(intent);
            }
        });
    }
}
