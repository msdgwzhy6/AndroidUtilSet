package com.smart.androidutils.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.smart.androidutils.R;
import com.smart.androidutils.activity.ad.NoAdActivity;
import com.smart.androidutils.activity.app.AppInfoActivity;
import com.smart.androidutils.activity.canvas.CanvasActivity;
import com.smart.androidutils.activity.classactivity.ClassActivity;
import com.smart.androidutils.activity.device.DeviceActivity;
import com.smart.androidutils.activity.file.FileActivity;
import com.smart.androidutils.activity.reflect.ReflectActivity;
import com.smart.androidutils.activity.sharepreference.SPActivity;
import com.smart.androidutils.activity.spider.view.SpiderActivity;
import com.smart.androidutils.BaseBean;
import com.smart.androidutils.activity.widget.MoreTextActivity;
import com.smart.androidutils.activity.widget.WidgetActivity;
import com.util.dialog.DialogCustom;
import com.util.phone.UitlDevice;
import com.util.phone.UtilNet;
import com.util.viewholder.CommonAdapter;

import java.util.List;

import static com.util.phone.UitlDevice.getAvailMemory;
import static com.util.phone.UitlDevice.getMobileNetType;
import static com.util.phone.UitlDevice.getSDAvailableSize;
import static com.util.phone.UitlDevice.getSDTotalSize;
import static com.util.phone.UitlDevice.getTotalMemory;
import static com.util.phone.UtilNet.isMobileConnected;
import static com.util.view.UtilWidget.getView;

/**
 * Created by smart on 2017/5/17.
 * 1、在文本末尾，实现点击“展开”---展开所有文本，并把“展开”改为“收起”；点击“收起”，则收起文本；
 * 2、“展开”和“收起”紧跟文本末尾。并且不换行。
 */

public class BaseGridViewHolderHelper implements CommonAdapter.IListHolderHelperCallback<BaseGridViewHolder,BaseBean> {
    private Activity activity ;
    protected String mItemName;
    @Override
    public CommonAdapter.IBaseViewHolder initViewHolder(BaseGridViewHolder viewHolder,
                                                        View convertView) {
        viewHolder = new BaseGridViewHolder();
        viewHolder.mNameText = getView(convertView, R.id.main_grid_text);
        return viewHolder;
    }

    @Override
    public void bindListDataToView(final Context context, final List<BaseBean> iBaseBeanList,
                                   final BaseGridViewHolder viewHolder, final int position) {
        if (activity == null) {
            activity = (Activity) context;
        }
        mItemName = iBaseBeanList.get(position).getName();
        viewHolder.mNameText.setText(mItemName);
        viewHolder.mNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemName = iBaseBeanList.get(position).getName();
                if (mItemName.equals(context.getResources().getString(R.string.act_sp))) {
                        context.startActivity(new Intent(context, SPActivity.class));
                }else if (mItemName.equals(context.getResources().getString(R.string.act_spider))) {
                        context.startActivity(new Intent(context, SpiderActivity.class));
                }else if (mItemName.equals(context.getResources().getString(R.string.act_class))) {
                        context.startActivity(new Intent(context, ClassActivity.class));
                }else if (mItemName.equals(context.getResources().getString(R.string.act_reflect))) {
                        context.startActivity(new Intent(context, ReflectActivity.class));
                }else if (mItemName.equals(context.getResources().getString(R.string.act_device))) {
                    context.startActivity(new Intent(context, DeviceActivity.class));
                }
                else if (mItemName.equals(context.getResources().getString(R.string.act_no_ad_off))) {
                    context.startActivity(new Intent(context, NoAdActivity.class));
                }
                else if (mItemName.equals(context.getResources().getString(R.string.act_file))) {
                    context.startActivity(new Intent(context, FileActivity.class));
                }
                else if (mItemName.equals(context.getResources().getString(R.string.canvas))) {
                    context.startActivity(new Intent(context, CanvasActivity.class));
                }

                //设备相关的事件处理
                else if (mItemName.equals(context.getResources().getString(R.string.device_wifi_availalbe))) {
                    viewHolderToast(String.valueOf(UtilNet.isWifiAvailable()));
                }
                else if (mItemName.equals(context.getResources().getString(R.string.device_wifi_connect))) {
                    viewHolderToast(String.valueOf(UtilNet.isWifiConnected()));
                }
                else if (mItemName.equals(context.getResources().getString(R.string.device_available_memory))) {
                    viewHolderToast(getAvailMemory());
                }
                else if (mItemName.equals(context.getResources().getString(R.string.device_total_memory))) {
                    viewHolderToast(getTotalMemory());
                }
                else if (mItemName.equals(context.getResources().getString(R.string.device_sd_total_size))) {
                    viewHolderToast(getSDTotalSize());
                }
                else if (mItemName.equals(context.getResources().getString(R.string.device_sd_available_size))) {
                    viewHolderToast(getSDAvailableSize());
                }
                //手机卡相关的事件处理
                else if (mItemName.equals(context.getResources().getString(R.string.telephony_state))) {
                    viewHolderToast(UitlDevice.getPhoneStatus(),10000);
                }
                else if (mItemName.equals(context.getResources().getString(R.string.telephony_sim_support_net))) {
                    viewHolderToast(String.valueOf(UtilNet.isMobileNetAvailable()));
                }
                else if (mItemName.equals(context.getResources().getString(R.string.telephony_sim_net_type))) {
                    viewHolderToast(getMobileNetType());
                }
                else if (mItemName.equals(context.getResources().getString(R.string.telephony_sim_net_connect))) {
                    viewHolderToast(String.valueOf(isMobileConnected()));
                }
                else if (mItemName.equals(context.getResources().getString(R.string.app_list_info))) {
                    context.startActivity(new Intent(context, AppInfoActivity.class));
                }
                /*
                * ViewActivity的相关操作
                * */
                else if (mItemName.equals(context.getResources().getString(R.string.view))) {
                    context.startActivity(new Intent(context, WidgetActivity.class));
                }
                else if (mItemName.equals(context.getResources().getString(R.string.view_more_text))) {
                    context.startActivity(new Intent(context, MoreTextActivity.class));
                }
            }
        });
    }

    private void viewHolderToast(String msg, int time) {
        new DialogCustom(activity)
                .setToast(msg,time)
                .setToastDrawableId(R.drawable.dialog_bg)
                .setOutsideClickable(true)
                .show();
    }

    private void viewHolderToast(String msg) {
        new DialogCustom(activity)
                .setToast(msg,2000)
                .setToastDrawableId(R.drawable.dialog_bg)
                .show();
    }
}
