package com.smart.androidutils.activity.http;

import android.util.Log;

import com.smart.androidutils.BaseCompatActivity;
import com.smart.androidutils.R;
import com.util.http.asynctask.string.HttpStringHelper;
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
           new HttpStringHelper()
                .get("http://www.imooc.com/api/teacher?type=4&num=30")
                .addParam("type","4")
                .addParam("num","30")
                .setCharset("GBK")
        .initHttpStringCallback(new IStringCallback() {
            @Override
            public void onStringResult(String result) {
                Log.i("xxx", "onStringResult" +result);
            }

            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "onFailure: "+e.getMessage());
            }
        });
    }
}
