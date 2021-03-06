package com.smart.androidutils.activity.screen;

import com.smart.androidutils.MainActivity;
import com.smart.androidutils.activity.screen.viewholder.ScreenViewHolderHelperI;

import static com.smart.androidutils.activity.screen.constant.ConScreen.ITEMS_SCREEN;

public class ScreenActivity extends MainActivity {
    @Override
    protected void initData() {
        super.initData();
        mUseSelfViewHolderHelper  = new ScreenViewHolderHelperI();
    }

    @Override
    protected void setActivityTitle() {
        super.setActivityTitle();
        mAvtivitytTitle = "屏幕";
    }


    @Override
    protected void setItems() {
        super.setItems();
        mItemsData = ITEMS_SCREEN;
    }
}
