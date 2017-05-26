package com.smart.androidutils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.smart.androidutils.viewholder.BaseGridViewHolder;
import com.util.dialog.DialogCustom;
import com.util.viewholder.CommonAdapter;

import java.util.List;

import static com.util.view.UtilWidget.getView;

/**
 * author xander on  2017/5/26.
 * function
 */

public class CommonViewHolderHelper implements CommonAdapter.IListHolderHelperCallback<BaseGridViewHolder,BaseBean> {
    protected Activity mActivity;
    protected String mItemName;
    @Override
    public CommonAdapter.IBaseViewHolder initViewHolder(BaseGridViewHolder viewHolder, View convertView) {
        viewHolder = new BaseGridViewHolder();
        viewHolder.mNameText = getView(convertView, R.id.main_grid_text);
        return viewHolder;
    }

    @Override
    public void bindListDataToView(final Context context, final List<BaseBean> iBaseBeanList, final BaseGridViewHolder viewHolder, final int position) {
        if (mActivity == null) {
            mActivity = (Activity) context;
        }
        mItemName = iBaseBeanList.get(position).getName();
        viewHolder.mNameText.setText(mItemName);
        viewHolder.mNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemViewClickedCallback(context, iBaseBeanList,viewHolder,position);
            }
        });
    }

    protected void setOnItemViewClickedCallback(Context context, List<BaseBean> iBaseBeanList, BaseGridViewHolder viewHolder, int position) {

    }

    protected void viewHolderToast(String msg, int time) {
        new DialogCustom(mActivity)
                .setToast(msg,time)
                .setToastDrawableId(R.drawable.dialog_bg)
                .setOutsideClickable(true)
                .show();
    }

    protected void viewHolderToast(String msg) {
        new DialogCustom(mActivity)
                .setToast(msg,2000)
                .setToastDrawableId(R.drawable.dialog_bg)
                .show();
    }
}
