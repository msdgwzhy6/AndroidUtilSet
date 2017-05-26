package com.util.constant;

import com.util.core.InitUtil;

/**
 * author xander on  2017/5/25.
 */

public final class ConstantRegex {
    public static final String regexStr;

    static {
        regexStr = InitUtil.getContext().getString(com.util.R.string.script_web_view_no_ad);
    }
}
