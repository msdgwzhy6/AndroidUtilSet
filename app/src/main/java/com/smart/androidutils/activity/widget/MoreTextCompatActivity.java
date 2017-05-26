package com.smart.androidutils.activity.widget;

import android.widget.TextView;

import com.smart.androidutils.BaseCompatActivity;
import com.smart.androidutils.R;
import com.util.view.UtilMoreText;

import static com.smart.androidutils.constant.ConMain.MORE_TEXT_MSG;
import static com.util.view.UtilWidget.getView;

/*
* 更多展示
* */
public class MoreTextCompatActivity extends BaseCompatActivity {
    TextView mMoreText;
    TextView mMoreText1;
    private String msg1;
    private String msg2;

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

        msg1 = msg ;
        msg2 = msg + msg1;
    }

    @Override
    protected void bindDataToView() {

        new UtilMoreText(mMoreText, msg1);
        new UtilMoreText(mMoreText1, msg2);
    }

    @Override
    protected void setActivityTitle() {
        mAvtivitytTitle = "MoreText演示";
    }
}
