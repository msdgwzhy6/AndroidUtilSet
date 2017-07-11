package com.sdk.util.http.core.callback;

public interface OnStringCallback extends OnFailureCallback {
    void onStringSuccess(final String result);
}
