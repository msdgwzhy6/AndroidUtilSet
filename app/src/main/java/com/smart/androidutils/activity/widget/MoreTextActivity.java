package com.smart.androidutils.activity.widget;

import android.widget.TextView;

import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.R;
import com.util.view.UtilMoreText;

import static com.smart.androidutils.constant.ConstantData.MORE_TEXT_MSG;
import static com.util.view.UtilWidget.getView;

/*
* 更多展示
* */
public class MoreTextActivity extends BaseActivity {
    TextView mMoreText;
    TextView mMoreText1;

    @Override
    protected int initLayout() {
        return R.layout.activity_more_text;
    }

    @Override
    protected void initView() {
        mMoreText = getView(this, R.id.id_view_more_text);
        mMoreText1 = getView(this, R.id.id_view_more_text1);
    }

    @Override
    protected void initData() {
        String msg = MORE_TEXT_MSG;

        String msg1 = msg + msg;
        String msg2 = msg + msg1;
        String msg3 = msg + msg2;

        new UtilMoreText(mMoreText, msg);
        new UtilMoreText(mMoreText1, msg1);
    }

    @Override
    protected void setActivityTitle() {
        mAvtivitytTitle = "MoreText演示";
    }
}
