package com.util.view;

import android.util.Log;
import android.webkit.WebView;

import com.util.http.UtilHttpString;
import com.util.http.core.callback.IStringCallback;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.util.constant.ConstantRegex.regexStr;

/**
 * author xander on  2017/5/25.
 * function
 */

public final class UtilNoAdWeb {
    private WebView mWebView;
    private String baseUrl;
    private String mCharset;

    /**
     *webView
     *charset 网页的编码格式
     */
    public UtilNoAdWeb(WebView webView, String charset, String url) {
        mWebView = webView;
        mCharset = charset;
        baseUrl = url;
        new UtilHttpString().get(url)
                .setCharset(charset)
                .initHttpStringCallback(new IStringCallback() {
                    @Override
                    public void onStringSuccess(String result) {
                        try {
                            URL url = new URL(baseUrl);
                            Pattern p = Pattern.compile(regexStr);
                            Matcher m = p.matcher(result);
                            while (m.find()) {
                                if (!(m.group().contains(url.getHost()))) {
                                    result = result.replace(m.group(), "");
                                }
                            }
                            baseUrl = String.format("%s://%s", url.getProtocol(), url.getHost());
                            Log.i("baseUrl", "onPostExecute: " + url.getHost());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        mWebView.loadDataWithBaseURL(baseUrl, result, "text/html", mCharset, "file:///android_asset/error_page.html");
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
    }

    /*@Override
    protected String doInBackground(String... strings) {
        baseUrl = strings[0];
        return readSourceFromUrl(strings[0],mCharset);
    }

    @Override
    protected void onPostExecute(String html) {
        super.onPostExecute(html);

    }*/
}
