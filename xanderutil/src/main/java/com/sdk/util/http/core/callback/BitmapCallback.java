package com.sdk.util.http.core.callback;

import android.graphics.Bitmap;

/**
 * author xander on  2017/5/31.
 * function
 */

public interface BitmapCallback {
    void onBitmapSuccess(Bitmap bitmap);
    void onBitmapFailure(Exception e, String errorCode);
}
