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
                    .initHttpStringCallback(this);
  /*      OkGo.get(url)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        // s 即为所需要的结果
                    }
                });*/
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
