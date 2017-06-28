package com.smart.androidutils.activity.http;

import android.util.Log;
import android.widget.TextView;

import com.sdk.util.http.UtilHttpString;
import com.sdk.util.http.core.callback.StringCallback;
import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;

import static com.sdk.util.view.UtilWidget.getView;

public class HttpActivity extends BaseActivity implements StringCallback {

    private TextView mTextView;
    private String url = "http://www.tngou.net/api/top/list";
    private String url1 = "http://www.imooc.com/api/teacher?type=4&num=30";
    @Override
    protected int initLayout() {
        return R.layout.activity_http;
    }

    @Override
    protected void initView() {
        mTextView = getView(this,R.id.id_http_text);
    }

    @Override
    protected void initData() {
           new UtilHttpString()
                   .get(url)
                    .setStringCallback(this);
    }

    @Override
    public void onStringSuccess(String result) {
        Log.i("xxx", "onStringSuccess" +result);
        mTextView.setText(result);
    }



    @Override
    public void onRequestFailure(Exception e, String errorCode) {

    }
}
