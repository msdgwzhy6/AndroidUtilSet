package com.smart.androidutils.activity.device;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.smart.androidutils.R;
import com.smart.androidutils.BaseBean;
import com.smart.androidutils.viewholder.BaseGridViewHolderHelper;
import com.util.viewholder.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.smart.androidutils.constant.ConMain.ITEMS_DEVICE;
import static com.util.view.UtilWidget.getView;

public class DeviceActivity extends AppCompatActivity {

    private GridView mGridView;
    private List<BaseBean> mBaseBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.act_device));
        mGridView = getView(this,R.id.main_grif_view);
        initData();
        mGridView.setAdapter(new CommonAdapter<BaseBean>(this, mBaseBeanList,
                R.layout.main_grid_view_item,new BaseGridViewHolderHelper()));
    }


    private void initData() {

        BaseBean baseBean;
        mBaseBeanList = new ArrayList<>();
        for (int i = 0; i < ITEMS_DEVICE.length; i++) {
            baseBean = new BaseBean();
            baseBean.setName(ITEMS_DEVICE[i]);
            mBaseBeanList.add(baseBean);
        }
    }
}
