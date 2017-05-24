package com.smart.androidutils.activity.ad;

import android.webkit.WebView;

import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;
import com.util.UtilWebViewNoAd;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoAdActivity extends BaseActivity {

    @BindView(R.id.id_web_view)
    WebView mIdWebView;

    private String url = "http://www.geyanw.com/html/renshenggeyan/2012/0503/295.html";

    @Override
    protected int initLayout() {
        setTitle(getResources().getString(R.string.act_no_ad));
        ButterKnife.bind(this);
        return R.layout.activity_no_ad;

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        UtilWebViewNoAd.htmlDetails(url,mIdWebView,this);
    }
}
