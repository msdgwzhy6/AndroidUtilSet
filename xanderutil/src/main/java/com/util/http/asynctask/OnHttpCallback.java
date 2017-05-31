package com.util.http.asynctask;

import java.io.InputStream;

public interface OnHttpCallback{
        void onSuccess(InputStream inputStream);
        void onFailure(Exception e);
    }
