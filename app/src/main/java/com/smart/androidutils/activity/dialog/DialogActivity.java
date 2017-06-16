package com.smart.androidutils.activity.dialog;

import com.smart.androidutils.MainActivity;
import com.smart.androidutils.R;

import static com.smart.androidutils.activity.dialog.ConDialog.ITEMS_DIALOG;

public class DialogActivity extends MainActivity {
    @Override
    protected void setActivityTitle() {
        super.setActivityTitle();
        mAvtivitytTitle = getString(R.string.dialog);
    }

    @Override
    protected void initData() {
        super.initData();
        mUseSelfViewHolderHelper = new DialogViewHolerHelperI();
    }

    @Override
    protected void setItems() {
        super.setItems();
        mItemsData =ITEMS_DIALOG;
    }
}
