package com.smart.androidutils.activity.screen;

import com.smart.androidutils.MainCompatActivity;
import com.smart.androidutils.activity.screen.viewholder.ScreenViewHolderHelper;

import static com.smart.androidutils.activity.screen.constant.ConScreen.ITEMS_SCREEN;

public class ScreenActivity extends MainCompatActivity{
    @Override
    protected void initData() {
        super.initData();
        mUseSelfViewHolderHelper  = new ScreenViewHolderHelper();
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
