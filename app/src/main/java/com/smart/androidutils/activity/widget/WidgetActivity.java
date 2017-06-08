package com.smart.androidutils.activity.widget;

import com.smart.androidutils.MainActivity;
import com.smart.androidutils.constant.ConMainItem;

public class WidgetActivity extends MainActivity {


    @Override
    protected void setActivityTitle() {
        mAvtivitytTitle="View 控制面板";
    }

    @Override
    protected void setItems() {
        super.setItems();
        mItemsData = ConMainItem.ITEMS_VIEW;
    }
}
