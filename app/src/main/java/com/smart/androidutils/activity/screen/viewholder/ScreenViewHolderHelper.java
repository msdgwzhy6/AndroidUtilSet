package com.smart.androidutils.activity.screen.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.smart.androidutils.BaseBean;
import com.smart.androidutils.CommonViewHolderHelper;
import com.smart.androidutils.R;
import com.smart.androidutils.activity.spider.view.SpiderCompatActivity;
import com.smart.androidutils.viewholder.BaseGridViewHolder;
import com.util.screen.UtilScreen;

import java.util.List;

import static com.smart.androidutils.util.Util.putBitmapDefault;

/**
 * author xander on  2017/5/26.
 * function
 */

public class ScreenViewHolderHelper extends CommonViewHolderHelper {

    @Override
    protected void setOnItemViewClickedCallback(Context context, List<BaseBean> iBaseBeanList, BaseGridViewHolder viewHolder, int position) {
        super.setOnItemViewClickedCallback(context, iBaseBeanList, viewHolder, position);
        mItemName = iBaseBeanList.get(position).getName();
        if (mItemName.equals(context.getResources().getString(R.string.screen_capture_without_status_bar))) {
            Bitmap bitmap = UtilScreen.captureWithoutStatusBar(mActivity);
            Log.i("xxx", "setOnItemViewClickedCallback");
            if (bitmap == null) {
                viewHolderToast("没有图片");
            }
            putBitmapDefault("cut",bitmap);
        }
        else if (mItemName.equals(context.getResources().getString(R.string.act_spider))) {
            context.startActivity(new Intent(context, SpiderCompatActivity.class));
        }
    }
}
