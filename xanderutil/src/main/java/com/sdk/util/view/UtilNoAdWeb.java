package com.sdk.util.view;

import android.util.Log;
import android.webkit.WebView;

import com.sdk.util.http.UtilHttpString;
import com.sdk.util.http.core.callback.OnStringCallback;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sdk.util.constant.ConstantRegex.regexStr;

/**
 * author xander on  2017/5/25.
 * function
 */

public final class UtilNoAdWeb {
    private WebView mWebView;
    private URL mTempUrl;
    private String baseUrl;
    private String mCharset;

    /**
     *webView
     *charset 网页的编码格式
     */
    public UtilNoAdWeb(WebView webView, String charset, final String url) {
        mWebView = webView;
        mCharset = charset;
        try {
            mTempUrl = new URL(url);
            baseUrl = String.format("%s://%s", mTempUrl.getProtocol(), mTempUrl.getHost());
            Log.i("mTempUrl", "onPostExecute: " + mTempUrl.getHost());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new UtilHttpString().get(url)
                .setCharset(charset)
                .setStringCallback(new OnStringCallback() {
                    @Override
                    public void onRequestFailure(Exception e, String errorCode) {

                    }



                    @Override
                    public void onStringSuccess(String result) {
                        Pattern p = Pattern.compile(regexStr);
                        Matcher m = p.matcher(result);
                        while (m.find()) {
                            if (!(m.group().contains(mTempUrl.getHost()))) {
                                result = result.replace(m.group(), "");
                            }
                        }
                        mWebView.loadDataWithBaseURL(baseUrl, result, "text/html", mCharset, "file:///android_asset/error_page.html");
                    }

                });
    }

    /*@Override
    protected String doInBackground(String... strings) {
        mTempUrl = strings[0];
        return readSourceFromUrl(strings[0],mCharset);
    }

    @Override
    protected void onPostExecute(String html) {
        super.onPostExecute(html);

    }*/
}
