package com.smart.androidutils;

import com.smart.androidutils.viewholder.BaseGridViewHolderHelper;
import com.util.viewholder.CommonAdapter;

import java.util.ArrayList;

import static com.smart.androidutils.constant.ConstantData.ITEMS_MAIN;
import static com.util.view.UtilWidget.getView;

public class MainActivity extends BaseActivity {

    protected String [] mItems;


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setActivityTitle() {
        mAvtivitytTitle = "操作面板";
    }

    @Override
    protected void initView() {
        mGridView = getView(this,R.id.main_grif_view);
    }

    @Override
    protected void initData() {
        mBaseBeanList = new ArrayList<>();
        setItems();
        BaseBean baseBean;
        for (int i = 0; i < mItems.length; i++) {
            baseBean = new BaseBean();
            baseBean.setName(mItems[i]);
            mBaseBeanList.add(baseBean);
        }
        mGridView.setAdapter(new CommonAdapter<BaseBean>(this, mBaseBeanList,
                R.layout.main_grid_view_item,new BaseGridViewHolderHelper()));
    }



    protected void setItems() {
        mItems = ITEMS_MAIN;
    }

}
