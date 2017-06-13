package com.sdk.util.constant;

import com.sdk.R;
import com.sdk.core.InitSDK;

/**
 * author xander on  2017/5/25.
 */

public final class ConstantRegex {
    public static final String regexStr;

    static {
        regexStr = InitSDK.getContext().getString(R.string.script_web_view_no_ad);
    }
}
