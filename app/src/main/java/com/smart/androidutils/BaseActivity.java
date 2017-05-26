package com.smart.androidutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.List;

/*
* activity的基类
* */
public abstract class BaseActivity extends AppCompatActivity {
    protected String mAvtivitytTitle;
    protected final String TAG = this.getClass().getSimpleName();
    protected List<BaseBean> mBaseBeanList;

    protected GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        setActivityTitle();
        setTitle(mAvtivitytTitle);
        initView();
        initData();
        setListener();
    }

    protected abstract int initLayout() ;
    protected abstract void initView();
    protected abstract void initData();

    protected void setListener(){}

    /**
     * 设置activity的标题
     */
    protected void setActivityTitle() {
//        mAvtivitytTitle = "操作面板";
    }

}
