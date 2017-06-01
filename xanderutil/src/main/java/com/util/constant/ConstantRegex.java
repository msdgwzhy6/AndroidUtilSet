package com.util.constant;

import com.util.core.InitSDK;

/**
 * author xander on  2017/5/25.
 */

public final class ConstantRegex {
    public static final String regexStr;

    static {
        regexStr = InitSDK.getContext().getString(com.util.R.string.script_web_view_no_ad);
    }
}
