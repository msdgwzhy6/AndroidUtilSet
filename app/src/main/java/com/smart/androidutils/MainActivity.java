package com.smart.androidutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.smart.androidutils.viewholder.BaseGridViewHolderHelper;
import com.smart.holder_library.CommonAdapter;
import com.util.InitUtil;

import java.util.ArrayList;
import java.util.List;

import static com.smart.androidutils.constant.ConstantData.ITEMS_MAIN;
import static com.util.view.UtilWidget.getView;

public class MainActivity extends AppCompatActivity {

    private GridView mGridView;
    private List<BaseBean> mBaseBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitUtil.init(this);
        initView();
        initData();
        setTitle("操作面板");
        mGridView.setAdapter(new CommonAdapter(this, mBaseBeanList,R.layout.main_grid_view_item,new BaseGridViewHolderHelper()));
    }



    private void initView() {
        mGridView = getView(this,R.id.main_grif_view);
    }
    private void initData() {

        BaseBean baseBean;
        mBaseBeanList = new ArrayList<>();
        for (int i = 0; i < ITEMS_MAIN.length; i++) {
            baseBean = new BaseBean();
            baseBean.setName(ITEMS_MAIN[i]);
            mBaseBeanList.add(baseBean);
        }
    }
}
