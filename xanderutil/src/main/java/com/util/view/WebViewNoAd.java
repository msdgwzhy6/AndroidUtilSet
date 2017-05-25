package com.util.view;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.util.file.UtilFile.readSourceFromUrl;

/**
 * @author xander on  2017/5/25.
 * @function
 */

public class WebViewNoAd extends AsyncTask<String, Void, String> {
    private static final String regexStr = "<script\\b[^>]*?src=\"([^\"]*?)\"[^>]*></script>";
    private WebView mWebView;
    private String baseUrl;
    private String mCharset;

    /**
     * @param webView
     * @param charset 网页的编码格式
     */
    public WebViewNoAd(WebView webView, String charset) {
        mWebView = webView;
        mCharset = charset;
    }

    @Override
    protected String doInBackground(String... strings) {
        baseUrl = strings[0];
        return readSourceFromUrl(strings[0],mCharset);
    }

    @Override
    protected void onPostExecute(String html) {
        super.onPostExecute(html);
        try {
            URL url = new URL(baseUrl);
            Pattern p = Pattern.compile(regexStr);
            Matcher m = p.matcher(html);
            while (m.find()) {
                if (!(m.group().contains(url.getHost()))) {
                    html = html.replace(m.group(), "");
                }
            }
            baseUrl = String.format("%s://%s", url.getProtocol(), url.getHost());
            Log.i("baseUrl", "onPostExecute: " + url.getHost());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mWebView.loadDataWithBaseURL(baseUrl, html, "text/html", mCharset, "file:///android_asset/error_page.html");
    }
}
