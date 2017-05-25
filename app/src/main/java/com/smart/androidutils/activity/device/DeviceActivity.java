package com.smart.androidutils.activity.device;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.smart.androidutils.R;
import com.smart.androidutils.bean.ItemBean;
import com.smart.androidutils.viewholder.GridViewHolderHelper;
import com.smart.holder_library.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.smart.androidutils.constant.ConstantUrl.ITEMS_DEVICE;
import static com.util.view.UtilWidget.getView;

public class DeviceActivity extends AppCompatActivity {

    private GridView mGridView;
    private List<ItemBean> mItemBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getResources().getString(R.string.act_device));
        mGridView = getView(this,R.id.main_grif_view);
        initData();
        mGridView.setAdapter(new CommonAdapter(this,mItemBeanList,R.layout.main_grid_view_item,new GridViewHolderHelper()));
    }


    private void initData() {

        ItemBean itemBean;
        mItemBeanList = new ArrayList<>();
        for (int i = 0; i < ITEMS_DEVICE.length; i++) {
            itemBean = new ItemBean();
            itemBean.setName(ITEMS_DEVICE[i]);
            mItemBeanList.add(itemBean);
        }
    }
}
