package com.smart.androidutils.constant;

import com.smart.androidutils.R;
import com.util.InitUtil;

/**
 * Created by smart on 2017/5/22.
 */

public class Constant {
    public static String urlImg="http://img.my.csdn.net/uploads/201407/26/1406383243_5120.jpg";
    public static final String [] ITEMS_MAIN = new String[]{
            InitUtil.getContext().getString(R.string.act_sp),
            InitUtil.getContext().getString(R.string.act_spider),
            InitUtil.getContext().getString(R.string.act_class),
            InitUtil.getContext().getString(R.string.act_reflect),
            InitUtil.getContext().getString(R.string.act_device),
            InitUtil.getContext().getString(R.string.act_no_ad),
            InitUtil.getContext().getString(R.string.act_sp1),
            InitUtil.getContext().getString(R.string.act_sp1)
    };
    public static final String[] ITEMS_DEVICE = new String[]{
            InitUtil.getContext().getString(R.string.telephony_state),
            InitUtil.getContext().getString(R.string.telephony_sim_support_net),
            InitUtil.getContext().getString(R.string.telephony_sim_net_type),
            InitUtil.getContext().getString(R.string.telephony_sim_net_connect),
            InitUtil.getContext().getString(R.string.device_wifi_availalbe),
            InitUtil.getContext().getString(R.string.device_wifi_connect),
            InitUtil.getContext().getString(R.string.device_available_memory),
            InitUtil.getContext().getString(R.string.device_total_memory),
            InitUtil.getContext().getString(R.string.device_sd_total_size),
            InitUtil.getContext().getString(R.string.device_sd_available_size),
            InitUtil.getContext().getString(R.string.act_sp1)
    };
}