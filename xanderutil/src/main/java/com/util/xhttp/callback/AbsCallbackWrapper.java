package com.util.xhttp.callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * ================================================
 * 描    述：所有回调的包装类,空实现
 * 修订历史：
 * ================================================
 */
public class AbsCallbackWrapper<T> extends AbsCallback<T> {
    @Override
    public T convertSuccess(Response value) throws Exception {
        value.close();
        return (T) value;
    }

    @Override
    public void onSuccess(T t, Call call, Response response) {
    }
}
