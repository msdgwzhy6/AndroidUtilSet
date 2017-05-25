package com.smart.androidutils.activity.canvas.viewholderhelper;

import android.content.Context;
import android.view.View;

import com.smart.androidutils.BaseBean;
import com.smart.androidutils.R;
import com.smart.androidutils.activity.canvas.view.XanderView;
import com.smart.androidutils.viewholder.BaseGridViewHolder;
import com.smart.holder_library.CommonAdapter;

import java.util.List;

import static com.smart.androidutils.constant.ConstantData.TAG_CIRCLE;
import static com.smart.androidutils.constant.ConstantData.TAG_LINE;
import static com.util.view.UtilWidget.getView;

/**
 * @author xander on  2017/5/25.
 * @function
 */

public class CanvasViewHolderHeper implements CommonAdapter.IListHolderHelperCallback<BaseGridViewHolder,BaseBean> {
    private XanderView xanderView;

    public CanvasViewHolderHeper(XanderView xanderView) {
        this.xanderView = xanderView;
    }

    @Override
    public CommonAdapter.IBaseViewHolder initViewHolder(BaseGridViewHolder viewHolder, View convertView) {
        viewHolder = new BaseGridViewHolder();
        viewHolder.mNameText = getView(convertView, R.id.main_grid_text);
        return viewHolder;
    }

    @Override
    public void bindListDataToView(final Context context, List<BaseBean> iBaseBeanList, final BaseGridViewHolder viewHolder, int position) {
        viewHolder.mNameText.setText(iBaseBeanList.get(position).getName());
        viewHolder.mNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.canvas_circle))) {
                    xanderView.setTag(TAG_CIRCLE);
                }else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.canvas_line))) {
                    xanderView.setTag(TAG_LINE);
                }
                xanderView.invalidate();
            }
        });
    }
}
