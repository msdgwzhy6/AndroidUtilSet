package com.smart.androidutils.activity.http;

import android.util.Log;

import com.smart.androidutils.BaseCompatActivity;
import com.smart.androidutils.R;
import com.util.http.UtilHttpString;
import com.util.http.core.callback.IStringCallback;

public class HttpActivity extends BaseCompatActivity {


    @Override
    protected int initLayout() {
        return R.layout.activity_http;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
           new UtilHttpString()
                .get("http://www.imooc.com/api/teacher?type=4&num=30")
                .addParam("type","4")
                .addParam("num","30")
                .setCharset("GBK")
        .initHttpStringCallback(new IStringCallback() {
            @Override
            public void onStringSuccess(String result) {
                Log.i("xxx", "onStringSuccess" +result);
            }

            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "onFailure: "+e.getMessage());
            }
        });
    }
}
