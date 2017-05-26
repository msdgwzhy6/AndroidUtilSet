package com.smart.androidutils.activity.widget;

import com.smart.androidutils.MainCompatActivity;
import com.smart.androidutils.constant.ConMain;

public class WidgetCompatActivity extends MainCompatActivity {


    @Override
    protected void setActivityTitle() {
        mAvtivitytTitle="View 控制面板";
    }

    @Override
    protected void setItems() {
        super.setItems();
        mItemsData = ConMain.ITEMS_VIEW;
    }
}
