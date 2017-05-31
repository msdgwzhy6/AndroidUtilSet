package com.util.http.asynctask.string;

import com.util.http.HttpHelper;
import com.util.http.asynctask.HttpTask;
import com.util.http.callback.IStringCallback;

/**
 * author xander on  2017/5/31.
 * function 类似于中介
 */

public class HttpStringHelper extends HttpHelper<HttpStringHelper> {

    public void initHttpStringCallback(IStringCallback stringCallback){
//        mStringCallback = stringCallback;
        new HttpTask(stringCallback,mCharset,HTTP_TYPE).execute(mUrl);
    }
}
