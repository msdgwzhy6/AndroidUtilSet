package com.smart.androidutils.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.smart.androidutils.R;
import com.smart.androidutils.activity.ad.NoAdActivity;
import com.smart.androidutils.activity.app.AppInfoActivity;
import com.smart.androidutils.activity.classactivity.ClassActivity;
import com.smart.androidutils.activity.device.DeviceActivity;
import com.smart.androidutils.activity.reflect.ReflectActivity;
import com.smart.androidutils.activity.sharepreference.SPActivity;
import com.smart.androidutils.activity.spider.view.SpiderActivity;
import com.smart.androidutils.bean.ItemBean;
import com.smart.dialog_library.DialogCustom;
import com.smart.holder_library.CommonAdapter;
import com.util.phone.UitlDevice;
import com.util.phone.UtilNet;

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
 */

public class GridViewHolderHelper implements CommonAdapter.IListHolderHelperCallback<GridViewHolder,ItemBean> {
    private Activity activity ;
    @Override
    public CommonAdapter.IBaseViewHolder initViewHolder(GridViewHolder viewHolder, View convertView) {
        viewHolder = new GridViewHolder();
        viewHolder.mNameText = getView(convertView, R.id.main_grid_text);
        return viewHolder;
    }

    @Override
    public void bindListDataToView(final Context context, final List<ItemBean> iBaseBeanList, final GridViewHolder viewHolder, final int position) {
        if (activity == null) {
            activity = (Activity) context;
        }
        viewHolder.mNameText.setText(iBaseBeanList.get(position).getName());
        viewHolder.mNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.act_sp))) {
                        context.startActivity(new Intent(context, SPActivity.class));
                }else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.act_spider))) {
                        context.startActivity(new Intent(context, SpiderActivity.class));
                }else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.act_class))) {
                        context.startActivity(new Intent(context, ClassActivity.class));
                }else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.act_reflect))) {
                        context.startActivity(new Intent(context, ReflectActivity.class));
                }else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.act_device))) {
                    context.startActivity(new Intent(context, DeviceActivity.class));
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.act_no_ad_off))) {
                    context.startActivity(new Intent(context, NoAdActivity.class));
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.act_file))) {
                    context.startActivity(new Intent(context, NoAdActivity.class));
                }

                //设备相关的事件处理
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.device_wifi_availalbe))) {
                    viewHolderToast(String.valueOf(UtilNet.isWifiAvailable()));
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.device_wifi_connect))) {
                    viewHolderToast(String.valueOf(UtilNet.isWifiConnected()));
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.device_available_memory))) {
                    viewHolderToast(getAvailMemory());
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.device_total_memory))) {
                    viewHolderToast(getTotalMemory());
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.device_sd_total_size))) {
                    viewHolderToast(getSDTotalSize());
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.device_sd_available_size))) {
                    viewHolderToast(getSDAvailableSize());
                }
                //手机卡相关的事件处理
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.telephony_state))) {
                    viewHolderToast(UitlDevice.getPhoneStatus(),10000);
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.telephony_sim_support_net))) {
                    viewHolderToast(String.valueOf(UtilNet.isMobileNetAvailable()));
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.telephony_sim_net_type))) {
                    viewHolderToast(getMobileNetType());
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.telephony_sim_net_connect))) {
                    viewHolderToast(String.valueOf(isMobileConnected()));
                }
                else if (viewHolder.mNameText.getText().toString().equals(context.getResources().getString(R.string.app_list_info))) {
                    context.startActivity(new Intent(context, AppInfoActivity.class));
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
