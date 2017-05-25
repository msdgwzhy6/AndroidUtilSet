package com.smart.androidutils.activity.spider.viewholder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.smart.androidutils.R;
import com.smart.androidutils.activity.spider.bean.SpiderBean;
import com.smart.dialog_library.DialogCustom;
import com.smart.holder_library.CommonAdapter;

import java.util.List;

import static com.util.view.UtilWidget.getView;

/**
 * @author xander on  2017/5/24.
 * @function
 */

public class SpiderViewHolderHelper implements CommonAdapter.IListHolderHelperCallback<SpiderViewHolder,SpiderBean> {
    private Activity mActivity;
    @Override
    public CommonAdapter.IBaseViewHolder initViewHolder(SpiderViewHolder viewHolder, View convertView) {
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
        new DialogCustom(mActivity)
                .setToast(msg,time)
                .setToastDrawableId(R.drawable.dialog_bg)
                .setOutsideClickable(true)
                .show();
    }

    private void viewHolderToast(String msg) {
        new DialogCustom(mActivity)
                .setToast(msg,5000)
                .setToastDrawableId(R.drawable.dialog_bg)
                .show();
    }
}
