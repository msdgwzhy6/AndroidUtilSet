package com.util.http.asynctask.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.util.http.core.HttpHelper;
import com.util.http.core.HttpTask;
import com.util.http.core.callback.OnHttpCallback;
import com.util.http.core.callback.IBitmapCallback;

import java.io.InputStream;

/**
 * author xander on  2017/5/31.
 * function 处理图片
 */

public class HttpBitmapHelper extends HttpHelper<HttpBitmapHelper> {
    @SuppressWarnings("unchecked")
    public void initHttpBitmapCallback(final IBitmapCallback bitmapCallback){
        new HttpTask<Bitmap>(new OnHttpCallback<Bitmap>() {
            @Override
            public Bitmap onDoing(InputStream inputStream) {
                return BitmapFactory.decodeStream(inputStream);
            }

            @Override
            public void onSuccess(Bitmap bitmap) {
                bitmapCallback.onSuccess(bitmap);
            }

            @Override
            public void onFailure(Exception e) {
            bitmapCallback.onFailure(e);
            }
        }).execute(mUrl);
    }
}
