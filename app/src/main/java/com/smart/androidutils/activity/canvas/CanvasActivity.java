package com.smart.androidutils.activity.canvas;

import android.widget.GridView;

import com.smart.androidutils.BaseActivity;
import com.smart.androidutils.BaseBean;
import com.smart.androidutils.R;
import com.smart.androidutils.activity.canvas.view.XanderView;
import com.smart.androidutils.activity.canvas.viewholderhelper.CanvasViewHolderHeper;
import com.sdk.util.viewholder.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.smart.androidutils.activity.canvas.constant.ConsData.ITEMS_CANVAS;
import static com.sdk.util.view.UtilWidget.getView;

public class CanvasActivity extends BaseActivity {

    private GridView mGridView;
    private List<BaseBean> mBaseBeanList;
    private XanderView mXanderView;

    @Override
    protected int initLayout() {
        return R.layout.activity_canvas;
    }

    @Override
    protected void initView() {
        mGridView = getView(this,R.id.id_canvas_grif_view);
        mXanderView = getView(this,R.id.id_canvas_xander);
    }

    @Override
    protected void initData() {
        setTitle(getString(R.string.canvas));
        BaseBean baseBean;
        mBaseBeanList = new ArrayList<>();
        for (int i = 0; i < ITEMS_CANVAS.length; i++) {
            baseBean = new BaseBean();
            baseBean.setName(ITEMS_CANVAS[i]);
            mBaseBeanList.add(baseBean);
        }

    }

    @Override
    protected void bindDataToView() {
        mGridView.setAdapter(new CommonAdapter<BaseBean>(this, mBaseBeanList,R.layout.main_grid_view_item,new CanvasViewHolderHeper(mXanderView)));
    }
}
