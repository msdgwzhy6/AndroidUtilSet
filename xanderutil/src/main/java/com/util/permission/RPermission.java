package com.util.permission;

import android.content.Context;

/**
 * author xander on  2017/5/23.
 */

public class RPermission {

    private static RPermission mInstance;
    private RPManager mRPManager;

    public static RPermission getInstance(Context context) {
        if (mInstance == null)
            synchronized (RPermission.class) {
                if (mInstance == null) {
                    mInstance = new RPermission(context);
                }
            }
        return mInstance;
    }

    private RPermission(Context context) {
        mRPManager = new RPManager(context.getApplicationContext());
    }

    /**
     * 开始请求
     *
     * param options
     * param RPListener
     */
    public void request(RPOptions options, RPListener RPListener) {
        if (options == null) new NullPointerException("RPOptions is null...");
        if (RPListener == null) new NullPointerException("RPListener is null...");
        mRPManager.request(options, RPListener);
    }

    RPManager getAcpManager() {
        return mRPManager;
    }
}
