package com.smart.androidutils.activity.ad;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;
import com.util.UtilWebViewNoAd;

import static com.smart.androidutils.constant.Constant.urlMR;
import static com.util.view.UtilWidget.getView;


public class NoAdActivity extends BaseActivity {

    private WebView mIdWebView;
    private Button mButtonOff;
    private Button mButtonOn;

    @Override
    protected int initLayout() {
        setTitle(getResources().getString(R.string.act_no_ad));
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
        UtilWebViewNoAd.htmlDetails(urlMR,mIdWebView,this,true);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mButtonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                UtilWebViewNoAd.htmlDetails(urlMR,mIdWebView,NoAdActivity.this,false);
            }
        });
        mButtonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                UtilWebViewNoAd.htmlDetails(urlMR,mIdWebView,NoAdActivity.this,true);
            }
        });
    }
}
