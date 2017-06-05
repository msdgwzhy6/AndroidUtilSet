package com.smart.androidutils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.smart.androidutils.viewholder.BaseGridViewHolder;
import com.util.dialog.UtilDialogToast;
import com.util.viewholder.CommonAdapter;

import java.util.List;

import static com.util.view.UtilWidget.getView;

/**
 * author xander on  2017/5/26.
 * function 网格或者列表的具有这样的特征 只有一个按钮且按钮只有文字的时候
 */

public abstract class CommonViewHolderHelper implements CommonAdapter.IListHolderHelperCallback<BaseGridViewHolder,BaseBean> {
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

    /*
    * 子类必须实现的方法
    * 用于事件处理
    * */
    protected abstract void setOnItemViewClickedCallback(Context context, List<BaseBean> iBaseBeanList, BaseGridViewHolder viewHolder, int position);

    protected void viewHolderToast(String msg, int time) {
       /* new UtilDialogDouble(mActivity)
                .setToast(msg,time)
                .setToastDrawableId(R.drawable.dialog_bg)
                .setOutsideClickable(true)
                .show();*/
        new UtilDialogToast(mActivity)
                .setToast(msg,2000)
                .show();
    }

    protected void viewHolderToast(String msg) {
       /* new UtilDialogDouble(mActivity)
                .setToast(msg,2000)
                .setToastDrawableId(R.drawable.dialog_bg)
                .show();*/
        new UtilDialogToast(mActivity)
                .setToast(msg,2000)
                .show();
    }
}
