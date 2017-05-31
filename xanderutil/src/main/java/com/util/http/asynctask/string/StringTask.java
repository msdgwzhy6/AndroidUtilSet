package com.util.http.asynctask.string;

import com.util.http.asynctask.HttpTask;
import com.util.http.asynctask.OnHttpCallback;
import com.util.http.callback.IStringCallback;

/**
 * author xander on  2017/5/31.
 * function
 */

public class StringTask extends HttpTask{
    private IStringCallback mStringCallback;
    private String mCharset;

    public StringTask(IStringCallback stringCallback, String charset, Integer type,OnHttpCallback httpCallback) {
        super(type,httpCallback);
        mCharset = charset;
        mStringCallback = stringCallback;
    }
/*
    @Override
    public void onSuccess(InputStream inputStream) {


    }

    @Override
    public void onFailure(Exception e) {

    }*/
}
