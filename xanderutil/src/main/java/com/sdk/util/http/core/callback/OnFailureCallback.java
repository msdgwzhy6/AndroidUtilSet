package com.sdk.util.http.core.callback;

/**
 * author xander on  2017/5/31.
 * function
 */

public interface OnFailureCallback {
    void onRequestFailure(Exception e, String errorCode);
}
