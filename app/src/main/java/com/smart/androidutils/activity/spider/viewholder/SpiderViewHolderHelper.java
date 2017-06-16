package com.smart.androidutils.activity.spider.viewholder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.sdk.util.viewholder.callback.BaseItemViewHolder;
import com.sdk.util.viewholder.callback.IListDataViewHolderHelper;
import com.smart.androidutils.R;
import com.smart.androidutils.activity.spider.bean.SpiderBean;

import java.util.List;

import static com.sdk.util.view.UtilWidget.getView;

/**
 * @author xander on  2017/5/24.
 * @function
 */

public class SpiderViewHolderHelper implements IListDataViewHolderHelper<SpiderViewHolder,SpiderBean> {
    private Activity mActivity;


    @Override
    public BaseItemViewHolder initItemViewHolder(SpiderViewHolder viewHolder, View convertView) {
        viewHolder = new SpiderViewHolder();
        viewHolder.mTextView = getView(convertView, R.id.id_spider_text_item);
        return viewHolder;
    }

    @Override
    public void bindListDataToView(Context context, final List<SpiderBean> iBaseBeanList, SpiderViewHolder viewHolder, final int position) {
        if (mActivity == null) {
            mActivity = (Activity) context;
        }
        viewHolder.mTextView.setText(iBaseBeanList.get(position).getText());
        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolderToast(iBaseBeanList.get(position).getText());
            }
        });

    }
    private void viewHolderToast(String msg, int time) {
        /*new UtilDialogDouble(mActivity)
                .setToast(msg,time)
                .setToastDrawableId(R.drawable.dialog_bg)
                .setOutsideClickable(true)
                .show();*/
    }

    private void viewHolderToast(String msg) {
       /* new UtilDialogDouble(mActivity)
                .setToast(msg,5000)
                .setToastDrawableId(R.drawable.dialog_bg)
                .show();*/
    }
}
