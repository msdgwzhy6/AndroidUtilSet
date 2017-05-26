package com.smart.androidutils.activity.screen;

import com.smart.androidutils.BaseBean;
import com.smart.androidutils.MainCompatActivity;
import com.smart.androidutils.R;
import com.smart.androidutils.activity.screen.viewholder.ScreenViewHolderHelper;
import com.util.viewholder.CommonAdapter;

import static com.smart.androidutils.activity.screen.constant.ConScreen.ITEMS_SCREEN;

public class ScreenActivity extends MainCompatActivity{
    @Override
    protected void initData() {
        super.initData();
        mSetAdapter = true;
    }

    @Override
    protected void setActivityTitle() {
        super.setActivityTitle();
        mAvtivitytTitle = "屏幕";
    }

    @Override
    protected void setAdapter() {
        super.setAdapter();
        mGridView.setAdapter(new CommonAdapter<BaseBean>(this, mBaseBeanList,
                R.layout.main_grid_view_item,new ScreenViewHolderHelper()));
    }

    @Override
    protected void setItems() {
        super.setItems();
        mItemsData = ITEMS_SCREEN;
    }
}
