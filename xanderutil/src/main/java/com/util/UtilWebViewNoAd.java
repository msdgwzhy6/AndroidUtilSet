package com.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.util.file.UtilFile.readSourceFromUrl;

/****************************************************************************
 * Created by xu on 2017/1/6.
 * Function:
 ***************************************************************************/

public class UtilWebViewNoAd {

    private static Activity activity;
    private static final String regexStr = "<script\\b[^>]*?src=\"([^\"]*?)\"[^>]*></script>";

    /************************************************************
     * @param url
     * @param webView
     * @param context
     * @Author; 龙之游 @ xu 596928539@qq.com
     * 时间:2016/12/19 22:20
     * 注释:  * WebView详情页  剔除 js注入的广告
     ************************************************************/
    public static void htmlDetails(@NotNull String url, @NotNull WebView webView, @NotNull final Activity context) {
        webView.loadUrl(url);
        setWebView(webView);
        activity = context;

        /************************************************************
         *@Author; 龙之游 @ xu 596928539@qq.com
         * 时间:2016/12/19 20:10
         * type  2不设置setWebViewClient   1设置正常加载网页   0设置处理地图导航的页面
         * 注释: 处理url 剔除 恶意 脚本
         ************************************************************/
        new AsyncTaskHtml(webView).execute(url);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            }
        });

    }

    /****************************************************************************
     * Created by xu on 2016/12/19.
     * Function:静态内部类 AsyncTaskHtml  配合  htmlDetails 去除恶意脚本
     ***************************************************************************/

    static class AsyncTaskHtml extends AsyncTask<String, Void, String> {
        private WebView mWebView;
        private String baseUrl;

        public AsyncTaskHtml(WebView webView) {
            mWebView = webView;
        }

        @Override
        protected String doInBackground(String... strings) {
            baseUrl = strings[0];
            return readSourceFromUrl(strings[0],"gb2312");

        }

        @Override
        protected void onPostExecute(String html) {
            super.onPostExecute(html);
            /************************************************************
             *@Author; 龙之游 @ xu 596928539@qq.com
             * 时间:2016/12/19 20:07
             * 注释:  正则处理到的html源码  字符串
             ************************************************************/
//            readSourceFromUrl(html);
            Pattern p = Pattern.compile(regexStr);
            Matcher m = p.matcher(html);
            while (m.find()) {
                if (!(m.group().contains("www.geyanw.com"))) {
                    html = html.replace(m.group(), "");
                }
            }

            try {
                URL url = new URL(baseUrl);
                baseUrl = String.format("%s://%s", url.getProtocol(), url.getHost());
//                Log.i("strUrl", "onPostExecute: " + strUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            mWebView.loadDataWithBaseURL(baseUrl, html,
                    "text/html", "utf-8", "file:///android_asset/error_page.html");
        }
    }

    /************************************************************
     * 创建者;龙之游 @ xu 596928539@qq.com
     * 修改时间:2017/1/6 11:49
     * 注释:webview设置
     ************************************************************/
    public static void setWebView(WebView webView) {
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
        webSettings.setSavePassword(false);
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
    }
}
