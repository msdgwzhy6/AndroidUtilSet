package com.util.http.callback;

public interface IStringCallback extends IFailureCallback {
    void onStringResult(final String result);
}
