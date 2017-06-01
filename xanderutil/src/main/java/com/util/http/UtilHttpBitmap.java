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
                bitmapCallback.onBitmapSuccess(bitmap);
            }

            @Override
            public void onFailure(Exception e) {
            bitmapCallback.onFailure(e);
            }
        }).execute(mUrl);
    }
}
