package com.smart.androidutils;

import android.widget.GridView;

import com.sdk.util.viewholder.CommonAdapter;
import com.smart.androidutils.viewholder.IBaseGridViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

import static com.sdk.util.view.UtilWidget.getView;
import static com.smart.androidutils.constant.ConMainItem.ITEMS_MAIN;


/*
* 网格或者列表的具有这样的特征 只有一个按钮且按钮只有文字的时候
* */
public class MainActivity extends BaseActivity {

    protected String [] mItemsData;
    protected List<BaseBean> mBaseBeanList;

    /*
    * 如果子类需要使用自己的 ViewHolderHelper
    * 只需在初始化数据的时候 新建一个自己的实例即可
    * */
    protected ComHolderHelperI mUseSelfViewHolderHelper;
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
        mUseSelfViewHolderHelper = new IBaseGridViewHolderHelper();
        setItems();
        BaseBean baseBean;
        for (int i = 0; i < mItemsData.length; i++) {
            baseBean = new BaseBean();
            baseBean.setName(mItemsData[i]);
            mBaseBeanList.add(baseBean);
        }
        if (mBaseBeanList == null) {
            throw new NullPointerException("mBaseBeanList should not to be null");
        }
    }

    @Override
    protected void bindDataToView() {
        mGridView.setAdapter(new CommonAdapter<>(this, mBaseBeanList,R.layout.main_grid_view_item, mUseSelfViewHolderHelper));
    }

    protected void setItems() {
        mItemsData = ITEMS_MAIN;
    }

}
