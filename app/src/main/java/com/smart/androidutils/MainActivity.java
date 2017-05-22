package com.smart.androidutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.smart.androidutils.bean.ItemBean;
import com.smart.androidutils.viewholder.MainGridViewHolderHelper;
import com.smart.holder_library.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.smart.androidutils.UtilWidget.getView;

public class MainActivity extends AppCompatActivity {

    private GridView mGridView;
    private List<ItemBean> mItemBeanList;
    private  String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setTitle("操作面板");
        mGridView.setAdapter(new CommonAdapter(this,mItemBeanList,R.layout.main_grid_view_item,new MainGridViewHolderHelper()));
    }



    private void initView() {
        mGridView = getView(this,R.id.main_grif_view);
    }
    private void initData() {
        items = new String[]{
                getResources().getString(R.string.act_sp),
                getResources().getString(R.string.act_spider),
                getResources().getString(R.string.act_class),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1),
                getResources().getString(R.string.act_sp1)

        };
        ItemBean itemBean;
        mItemBeanList = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            itemBean = new ItemBean();
            itemBean.setName(items[i]);
            mItemBeanList.add(itemBean);
        }
    }
}
