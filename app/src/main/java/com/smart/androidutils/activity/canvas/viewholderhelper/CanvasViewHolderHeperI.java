package com.smart.androidutils.activity.canvas.viewholderhelper;

import android.content.Context;
import android.view.View;

import com.sdk.util.viewholder.callback.IBaseItemViewHolder;
import com.sdk.util.viewholder.callback.IListDataViewHolderHelperI;
import com.smart.androidutils.BaseBean;
import com.smart.androidutils.R;
import com.smart.androidutils.activity.canvas.view.XanderView;
import com.smart.androidutils.viewholder.IBaseGridViewHolder;

import java.util.List;

import static com.sdk.util.view.UtilWidget.getView;
import static com.sdk.util.view.UtilWidget.setViewAlphaAnimation;
import static com.smart.androidutils.activity.canvas.constant.ConsData.TAG_BITMAP;
import static com.smart.androidutils.activity.canvas.constant.ConsData.TAG_CIRCLE;
import static com.smart.androidutils.activity.canvas.constant.ConsData.TAG_LINE;
import static com.smart.androidutils.activity.canvas.constant.ConsData.TAG_RECT;
import static com.smart.androidutils.activity.canvas.constant.ConsData.TAG_ROUND_RECT;
import static com.smart.androidutils.activity.canvas.constant.ConsData.TAG_TEXT;

/**
 * @author xander on  2017/5/25.
 * @function
 */

public class CanvasViewHolderHeperI implements IListDataViewHolderHelperI<IBaseGridViewHolder,BaseBean> {
    private XanderView xanderView;

    public CanvasViewHolderHeperI(final XanderView xanderView) {
        this.xanderView = xanderView;
        this.xanderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewAlphaAnimation(xanderView);
            }
        });
    }

    @Override
    public IBaseItemViewHolder initItemViewHolder(IBaseGridViewHolder viewHolder, View convertView) {
        viewHolder = new IBaseGridViewHolder();
        viewHolder.mNameText = getView(convertView, R.id.main_grid_text);
        return viewHolder;
    }

    @Override
    public void bindListDataToView(final Context context, List<BaseBean> iBaseBeanList, final IBaseGridViewHolder viewHolder, int position) {
        viewHolder.mNameText.setText(iBaseBeanList.get(position).getName());
        viewHolder.mNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.canvas_circle))) {
                    xanderView.setTag(TAG_CIRCLE);//画圆
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.canvas_line))) {
                    xanderView.setTag(TAG_LINE);//划线
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.canvas_rect))) {
                    xanderView.setTag(TAG_RECT);//画矩形
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.canvas_round_rect))) {
                    xanderView.setTag(TAG_ROUND_RECT);//画圆角矩形
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.canvas_bitmap))) {
                    xanderView.setTag(TAG_BITMAP);//位图
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.canvas_text))) {
                    xanderView.setTag(TAG_TEXT);//文字
                }

                xanderView.invalidate();
            }
        });
    }
}
