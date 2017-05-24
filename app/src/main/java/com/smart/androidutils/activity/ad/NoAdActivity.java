package com.smart.androidutils.activity.ad;

import android.webkit.WebView;

import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;
import com.util.UtilWebViewNoAd;

import static com.smart.androidutils.constant.Constant.urlMR;
import static com.util.view.UtilWidget.getView;


public class NoAdActivity extends BaseActivity {

    WebView mIdWebView;



    @Override
    protected int initLayout() {
        setTitle(getResources().getString(R.string.act_no_ad));
        return R.layout.activity_no_ad;

    }

    @Override
    protected void initView() {
        mIdWebView = getView(this,R.id.id_no_ad_web_view);
    }

    @Override
    protected void initData() {
        UtilWebViewNoAd.htmlDetails(urlMR,mIdWebView,this);
    }
}
