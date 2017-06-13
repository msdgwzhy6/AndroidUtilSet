package com.sdk.util.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sdk.util.http.core.HttpTask;
import com.sdk.util.http.core.callback.IBitmapCallback;
import com.sdk.util.http.core.HttpHelper;
import com.sdk.util.http.core.callback.OnHttpCallback;

import java.io.InputStream;

/**
 * author xander on  2017/5/31.
 * function 处理图片
 */

public class UtilHttpBitmap extends HttpHelper<UtilHttpBitmap> {
    @SuppressWarnings("unchecked")
    public void initHttpBitmapCallback(final IBitmapCallback bitmapCallback){
        new HttpTask<Bitmap>(new OnHttpCallback<Bitmap>() {
            @Override
            public Bitmap onThread(InputStream inputStream) {
                return BitmapFactory.decodeStream(inputStream);
            }

            @Override
            public void onSuccess(Bitmap bitmap) {
                if (bitmap != null) {
                    bitmapCallback.onBitmapSuccess(bitmap);
                }

            }

            @Override
            public void onFailure(Exception e) {
                /*
                * 如果获取失败，就返回错误信息和一张默认的图片
                * 默认图片 可以有用户指定
                * */
                bitmapCallback.onBitmapFailure(e);
            }
        }).execute(mUrl);
    }
}
