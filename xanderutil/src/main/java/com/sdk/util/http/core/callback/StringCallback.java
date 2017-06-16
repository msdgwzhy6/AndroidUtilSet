package com.sdk.util.http.core.callback;

public interface StringCallback extends FailureCallback {
    void onStringSuccess(final String result);
}
