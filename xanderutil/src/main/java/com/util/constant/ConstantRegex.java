package com.util.constant;

import com.util.InitUtil;

/**
 * author xander on  2017/5/25.
 */

public class ConstantRegex {
    public static final String regexStr;

    static {
        regexStr = InitUtil.getContext().getString(com.util.R.string.script_web_view_no_ad);
    }
}
