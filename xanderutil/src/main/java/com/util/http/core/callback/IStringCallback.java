package com.util.http.core.callback;

public interface IStringCallback extends IFailureCallback {
    void onStringSuccess(final String result);
}
