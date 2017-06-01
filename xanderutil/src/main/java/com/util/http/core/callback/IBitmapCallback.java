package com.util.http.core.callback;

import android.graphics.Bitmap;

/**
 * author xander on  2017/5/31.
 * function
 */

public interface IBitmapCallback extends IFailureCallback {
    void onBitmapSuccess(Bitmap bitmap);
}
