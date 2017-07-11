package com.smart.androidutils.activity.http;

import android.widget.ListView;

import com.sdk.util.http.UtilHttpString;
import com.sdk.util.http.core.callback.OnStringCallback;
import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;

import static com.sdk.util.view.UtilWidget.getView;

public class HttpActivity extends BaseActivity  implements OnStringCallback {

    private ListView mListView;
    private String url = "http://www.tngou.net/api/top/list";
    private UtilHttpString mUtilHttpString;
    @Override
    protected int initLayout() {
        return R.layout.activity_http;
    }

    @Override
    protected void initView() {
        mListView = getView(this,R.id.id_http_text);
    }

    @Override
    protected void initData() {
        mUtilHttpString = new UtilHttpString().get(url).setStringCallback(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUtilHttpString.cancel();//取消网络请求
    }

    @Override
    public void onStringSuccess(String result) {

    }

    @Override
    public void onRequestFailure(Exception e, String errorCode) {

    }
}
