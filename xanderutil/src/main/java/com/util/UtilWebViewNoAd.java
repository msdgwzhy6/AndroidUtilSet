package com.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.util.file.UtilFile.writeStr2Log;

/****************************************************************************
 * Created by xu on 2017/1/6.
 * Function:
 ***************************************************************************/

public class UtilWebViewNoAd {

    private static final String regexStr = "<script\\b[^>]*?src=\"([^\"]*?)\"[^>]*></script>";

    /************************************************************
     * @param url
     * @param webView
     * @param context
     * @param paramB
     * @Author; 龙之游 @ xu 596928539@qq.com
     * 时间:2016/12/19 22:20
     * 注释:  * WebView详情页  剔除 js注入的广告
     ************************************************************/
    public static void htmlDetails(@NotNull final String url, @NotNull final WebView webView, @NotNull final Activity context, final boolean deleteAD) {
//
        setWebView(webView,url,context);
        if (!deleteAD) {
            webView.loadUrl(url);
            return;
        }
        /************************************************************
         *@Author; 龙之游 @ xu 596928539@qq.com
         * 时间:2016/12/19 20:10
         * type  2不设置setWebViewClient   1设置正常加载网页   0设置处理地图导航的页面
         * 注释: 处理url 剔除 恶意 脚本
         ************************************************************/
        new AsyncTaskHtml(webView).execute(url);

    }

    /****************************************************************************
     * Created by xu on 2016/12/19.
     * Function:静态内部类 AsyncTaskHtml  配合  htmlDetails 去除恶意脚本
     ***************************************************************************/

    private static class AsyncTaskHtml extends AsyncTask<String, Void, String> {
        private WebView mWebView;
        private String baseUrl;

        AsyncTaskHtml(WebView webView) {
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
                Log.i("baseUrl", "onPostExecute: " + baseUrl);
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
    private static void setWebView(final WebView webView, final String url, final Activity paramContext) {
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
                if (Build.VERSION.SDK_INT >21) {
                    new AsyncTaskHtml(webView).execute(request.getUrl().toString());//去广告核心步骤
                }else {
                    new AsyncTaskHtml(webView).execute(url);//去广告核心步骤
                }
                return super.shouldInterceptRequest(view, request);
            }


        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                paramContext.startActivity(intent);
            }
        });
    }
    private static String readSourceFromUrl(String urlParam, String encoding){
        InputStreamReader isr = null;
        StringBuilder html = new StringBuilder();
        InputStream is = null;
        String charset ="utf-8";
        if (!TextUtils.isEmpty(encoding)) {
            charset = encoding;
        }
        try{
            URL url = new URL(urlParam); //根据Strng表现形式创建URL对象

            URLConnection urlConnection = url.openConnection();//返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接

            Log.i("xxx", "readSourceFromUrl: "+urlConnection.getContentEncoding());
            //urlConnection.setConnectTimeout(4000); //设置链接超时

            is = urlConnection.getInputStream();//返回从打开的连接中读取到的输入流对象

            isr = new InputStreamReader(is, charset);

            BufferedReader br = new BufferedReader(isr);
            String line;
            while((line = br.readLine()) != null)
            {
                html.append(line).append("\r\n");
            }

        } catch(IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        writeStr2Log(html.toString());
        return html.toString();
    }

}
