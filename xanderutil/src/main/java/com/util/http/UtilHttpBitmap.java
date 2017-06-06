package com.util.http;

import android.graphics.Bitmap;

import com.util.http.core.HttpHelper;
import com.util.http.core.HttpTask;
import com.util.http.core.callback.IBitmapCallback;
import com.util.http.core.callback.OnHttpCallback;

import java.io.InputStream;

import static com.util.bitmap.UtilBitmap.getBitmap;

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
                return getBitmap(inputStream);
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