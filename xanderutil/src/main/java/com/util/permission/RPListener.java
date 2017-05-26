package com.util.permission;

import java.util.List;

/**
 * author xander on  2017/5/23.
 */

public interface RPListener {
    /**
     *同意
     */
    void onGranted();

    /**
     * 拒绝
     */
    void onDenied(List<String> permissions);
}
