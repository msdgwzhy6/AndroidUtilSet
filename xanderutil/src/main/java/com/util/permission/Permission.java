package com.util.permission;

import android.app.Activity;

/**
 * author xander on  2017/5/23.
 * 做权限初始化操作，具体权限请求操作在 helper 里面
 */

public class Permission {

    private static Permission mInstance;
    private PermissionHelper mPermissionHelper;

    public static Permission getInstance(Activity activity) {
        if (mInstance == null)
            synchronized (Permission.class) {
                if (mInstance == null) {
                    mInstance = new Permission(activity);
                }
            }
        return mInstance;
    }

    private Permission(Activity activity) {
        mPermissionHelper = new PermissionHelper(activity);
    }

    /**
     * 开始请求
     * param types 权限类型
     * param PermissionCallback
     */
    public void initPermission(PermissionTypes types, PermissionCallback permissionCallback) {
        if (types == null) throw new NullPointerException("PermissionTypes is null...");
        if (permissionCallback == null) throw new NullPointerException("PermissionCallback is null...");
        mPermissionHelper.request(types, permissionCallback);
    }

    PermissionHelper getPermissionHelper() {
        return mPermissionHelper;
    }
}
