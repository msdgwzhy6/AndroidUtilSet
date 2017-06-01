package com.smart.androidutils.activity.http;

import android.util.Log;
import android.widget.TextView;

import com.smart.androidutils.BaseCompatActivity;
import com.smart.androidutils.R;
import com.util.http.UtilHttpString;
import com.util.http.core.callback.IStringCallback;

import static com.util.view.UtilWidget.getView;

public class HttpActivity extends BaseCompatActivity implements IStringCallback{

    private TextView mTextView;

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
                   .get("http://www.imooc.com/api/teacher?type=4&num=30")
                    .addParam("type","4")
                    .addParam("num","30")
                    .setCharset("GBK")
                    .initHttpStringCallback(this);
    }

    @Override
    public void onStringSuccess(String result) {
        Log.i("xxx", "onStringSuccess" +result);
        mTextView.setText(result);
    }

    @Override
    public void onFailure(Exception e) {
        Log.i(TAG, "onFailure: "+e.getMessage());
    }


}
