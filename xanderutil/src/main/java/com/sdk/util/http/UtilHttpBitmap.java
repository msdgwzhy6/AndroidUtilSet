package com.sdk.util.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sdk.util.http.core.HttpTask;
import com.sdk.util.http.core.callback.BitmapCallback;
import com.sdk.util.http.core.callback.OnHttpCallback;

import java.io.InputStream;

/**
 * author xander on  2017/5/31.
 * function 处理图片
 */

public class UtilHttpBitmap extends HttpTask<UtilHttpBitmap,Bitmap> {
    @SuppressWarnings("unchecked")
    public UtilHttpBitmap setBitmapCallback(final BitmapCallback bitmapCallback){
        setOnHttpCallback(new OnHttpCallback<Bitmap>() {
            @Override
            public Bitmap onChildThread(InputStream inputStream) {
                if (!interceptFlag) {
                    return BitmapFactory.decodeStream(inputStream);
                }else {
                    return null;
                }
            }

            @Override
            public void onUISuccess(Bitmap bitmap) {
                if (interceptFlag) {
                    return;
                }
                if (bitmap != null) {
                    bitmapCallback.onBitmapSuccess(bitmap);
                }else {
                    bitmapCallback.onBitmapFailure(new NullPointerException("bitmap 为空"), "");
                }
            }

            @Override
            public void onFailure(Exception e, String errorCode) {
                /*
                * 如果获取失败
                * */
                bitmapCallback.onBitmapFailure(e, errorCode);
            }
        }).execute(mUrl);
        return this;
    }
}
