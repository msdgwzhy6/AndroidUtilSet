package com.smart.androidutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.smart.androidutils.bean.ItemBean;
import com.smart.androidutils.viewholder.GridViewHolderHelper;
import com.smart.holder_library.CommonAdapter;
import com.util.InitUtil;

import java.util.ArrayList;
import java.util.List;

import static com.smart.androidutils.constant.ConstantUrl.ITEMS_MAIN;
import static com.util.view.UtilWidget.getView;

public class MainActivity extends AppCompatActivity {

    private GridView mGridView;
    private List<ItemBean> mItemBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitUtil.init(this);
        initView();
        initData();
        setTitle("操作面板");
        mGridView.setAdapter(new CommonAdapter(this,mItemBeanList,R.layout.main_grid_view_item,new GridViewHolderHelper()));
    }



    private void initView() {
        mGridView = getView(this,R.id.main_grif_view);
    }
    private void initData() {

        ItemBean itemBean;
        mItemBeanList = new ArrayList<>();
        for (int i = 0; i < ITEMS_MAIN.length; i++) {
            itemBean = new ItemBean();
            itemBean.setName(ITEMS_MAIN[i]);
            mItemBeanList.add(itemBean);
        }
    }
}
