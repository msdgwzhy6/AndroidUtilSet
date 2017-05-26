package com.smart.androidutils;

import android.widget.GridView;

import com.smart.androidutils.viewholder.BaseGridViewHolderHelper;
import com.util.viewholder.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.smart.androidutils.constant.ConMain.ITEMS_MAIN;
import static com.util.view.UtilWidget.getView;

public class MainCompatActivity extends BaseCompatActivity {

    protected String [] mItemsData;
    protected boolean mSetAdapter;
    protected List<BaseBean> mBaseBeanList;

    protected GridView mGridView;

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
        for (int i = 0; i < mItemsData.length; i++) {
            baseBean = new BaseBean();
            baseBean.setName(mItemsData[i]);
            mBaseBeanList.add(baseBean);
        }

    }

    @Override
    protected void bindDataToView() {
        if (mSetAdapter) {
            setAdapter();
        }else {
            mGridView.setAdapter(new CommonAdapter<BaseBean>(this, mBaseBeanList,
                    R.layout.main_grid_view_item,new BaseGridViewHolderHelper()));
        }
    }

    protected void setAdapter(){
    }


    protected void setItems() {
        mItemsData = ITEMS_MAIN;
    }

}
