package com.smart.androidutils.activity.http;

import android.util.Log;

import com.smart.androidutils.BaseCompatActivity;
import com.smart.androidutils.R;
import com.util.http.asynctask.string.HttpStringHelper;
import com.util.http.callback.IStringCallback;

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
                .get("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm")
                .addParam("tel","18221869775")
                .setCharset("GBK")
        .initHttpStringCallback(new IStringCallback() {
            @Override
            public void onStringResult(String result) {
                Log.i("xxx", "onStringResult" +result);
            }

            @Override
            public void failure(Exception e) {

            }
        });
    }
}
