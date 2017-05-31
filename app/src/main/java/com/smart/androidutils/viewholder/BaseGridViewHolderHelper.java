package com.smart.androidutils.viewholder;

import android.content.Context;
import android.content.Intent;

import com.smart.androidutils.BaseBean;
import com.smart.androidutils.CommonViewHolderHelper;
import com.smart.androidutils.R;
import com.smart.androidutils.activity.ad.NoAdCompatActivity;
import com.smart.androidutils.activity.app.AppInfoCompatActivity;
import com.smart.androidutils.activity.canvas.CanvasCompatActivity;
import com.smart.androidutils.activity.classactivity.ClassActivity;
import com.smart.androidutils.activity.device.DeviceActivity;
import com.smart.androidutils.activity.dialog.DialogActivity;
import com.smart.androidutils.activity.file.FileCompatActivity;
import com.smart.androidutils.activity.permission.PActivity;
import com.smart.androidutils.activity.reflect.ReflectActivity;
import com.smart.androidutils.activity.screen.ScreenActivity;
import com.smart.androidutils.activity.sharepreference.SPCompatActivity;
import com.smart.androidutils.activity.spider.view.SpiderCompatActivity;
import com.smart.androidutils.activity.widget.MoreTextCompatActivity;
import com.smart.androidutils.activity.widget.WidgetCompatActivity;
import com.util.phone.UitlDevice;
import com.util.phone.UtilNet;

import java.util.List;

import static com.util.phone.UitlDevice.getAvailMemory;
import static com.util.phone.UitlDevice.getMobileNetType;
import static com.util.phone.UitlDevice.getSDAvailableSize;
import static com.util.phone.UitlDevice.getSDTotalSize;
import static com.util.phone.UitlDevice.getTotalMemory;
import static com.util.phone.UtilNet.isMobileConnected;

/**
 * Created by smart on 2017/5/17.
 * 1、在文本末尾，实现点击“展开”---展开所有文本，并把“展开”改为“收起”；点击“收起”，则收起文本；
 * 2、“展开”和“收起”紧跟文本末尾。并且不换行。
 */

public class BaseGridViewHolderHelper extends CommonViewHolderHelper{
    @Override
    protected void setOnItemViewClickedCallback(Context context, List<BaseBean> iBaseBeanList, BaseGridViewHolder viewHolder, int position) {
        mItemName = iBaseBeanList.get(position).getName();
        if (mItemName.equals(context.getResources().getString(R.string.control_sp))) {
            context.startActivity(new Intent(context, SPCompatActivity.class));
        }
        else if (mItemName.equals(context.getResources().getString(R.string.control_spider))) {
            context.startActivity(new Intent(context, SpiderCompatActivity.class));
        }
        else if (mItemName.equals(context.getResources().getString(R.string.control_class))) {
            context.startActivity(new Intent(context, ClassActivity.class));
        }
        else if (mItemName.equals(context.getResources().getString(R.string.control_reflect))) {
            context.startActivity(new Intent(context, ReflectActivity.class));
        }
        else if (mItemName.equals(context.getResources().getString(R.string.phone))) {
            context.startActivity(new Intent(context, DeviceActivity.class));
        }
        else if (mItemName.equals(context.getResources().getString(R.string.control_no_ad_off))) {
            context.startActivity(new Intent(context, NoAdCompatActivity.class));
        }
        else if (mItemName.equals(context.getResources().getString(R.string.screen))) {
            context.startActivity(new Intent(context, ScreenActivity.class));
        }
        else if (mItemName.equals(context.getResources().getString(R.string.control_file))) {
            context.startActivity(new Intent(context, FileCompatActivity.class));
        }
        else if (mItemName.equals(context.getResources().getString(R.string.canvas))) {
            context.startActivity(new Intent(context, CanvasCompatActivity.class));
        }

        //设备相关的事件处理
        else if (mItemName.equals(context.getResources().getString(R.string.phone_wifi_availalbe))) {
            viewHolderToast(String.valueOf(UtilNet.isWifiAvailable()));
        }
        else if (mItemName.equals(context.getResources().getString(R.string.phone_wifi_connect))) {
            viewHolderToast(String.valueOf(UtilNet.isWifiConnected()));
        }
        else if (mItemName.equals(context.getResources().getString(R.string.phone_available_memory))) {
            viewHolderToast(getAvailMemory());
        }
        else if (mItemName.equals(context.getResources().getString(R.string.phone_total_memory))) {
            viewHolderToast(getTotalMemory());
        }
        else if (mItemName.equals(context.getResources().getString(R.string.phone_sd_total_size))) {
            viewHolderToast(getSDTotalSize());
        }
        else if (mItemName.equals(context.getResources().getString(R.string.phone_sd_available_size))) {
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
            context.startActivity(new Intent(context, AppInfoCompatActivity.class));
        }
                /*
                * ViewActivity的相关操作
                * */
        else if (mItemName.equals(context.getResources().getString(R.string.view))) {
            context.startActivity(new Intent(context, WidgetCompatActivity.class));
        }
        else if (mItemName.equals(context.getResources().getString(R.string.view_more_text))) {
            context.startActivity(new Intent(context, MoreTextCompatActivity.class));
        }
        /*
        * 权限
        * */
        else if (mItemName.equals(context.getResources().getString(R.string.permission))) {
            context.startActivity(new Intent(context, PActivity.class));
        }

       /*
       *  对话框
       *  */
        else if (mItemName.equals(context.getResources().getString(R.string.dialog))) {
            context.startActivity(new Intent(context, DialogActivity.class));
        }


    }
}
