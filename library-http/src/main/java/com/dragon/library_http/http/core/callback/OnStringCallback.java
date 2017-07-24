package com.dragon.library_http.http.core.callback;

public interface OnStringCallback extends OnFailureCallback {
    void onStringSuccess(final String result);
}
