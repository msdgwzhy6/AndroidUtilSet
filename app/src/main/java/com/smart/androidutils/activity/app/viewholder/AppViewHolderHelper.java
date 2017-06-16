package com.smart.androidutils.activity.app.viewholder;

import android.content.Context;
import android.view.View;

import com.sdk.util.viewholder.callback.IBaseItemViewHolder;
import com.sdk.util.viewholder.callback.IListDataViewHolderHelperI;
import com.smart.androidutils.R;
import com.smart.androidutils.activity.app.bean.MyAppInfoBean;

import java.util.List;

import static com.sdk.util.view.UtilWidget.getView;

/**
 * @author xander on  2017/5/25.
 * @function
 */

public class AppViewHolderHelper implements IListDataViewHolderHelperI<AppViewHolder,MyAppInfoBean> {


    @Override
    public IBaseItemViewHolder initItemViewHolder(AppViewHolder viewHolder, View convertView) {
        viewHolder = new AppViewHolder();
        viewHolder.mTextView = getView(convertView, R.id.tv_app_name);
        viewHolder.mImageView = getView(convertView, R.id.iv_app_icon);
        return viewHolder;
    }

    @Override
    public void bindListDataToView(Context context, List<MyAppInfoBean> iBaseBeanList, AppViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(iBaseBeanList.get(position).getAppName());
        viewHolder.mImageView.setImageDrawable(iBaseBeanList.get(position).getAppIcon());
    }
}
