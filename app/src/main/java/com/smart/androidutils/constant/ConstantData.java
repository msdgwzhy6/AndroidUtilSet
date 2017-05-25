package com.smart.androidutils.constant;

import com.smart.androidutils.R;
import com.util.InitUtil;

/**
 * @author xander on  2017/5/25.
 * @function
 */

public class ConstantData {
    public static final String [] ITEMS_MAIN = new String[]{
            InitUtil.getContext().getString(R.string.act_sp),
            InitUtil.getContext().getString(R.string.act_spider),
            InitUtil.getContext().getString(R.string.act_class),
            InitUtil.getContext().getString(R.string.act_reflect),
            InitUtil.getContext().getString(R.string.act_device),
            InitUtil.getContext().getString(R.string.act_no_ad_off),
            InitUtil.getContext().getString(R.string.act_file),
            InitUtil.getContext().getString(R.string.app_list_info)
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
