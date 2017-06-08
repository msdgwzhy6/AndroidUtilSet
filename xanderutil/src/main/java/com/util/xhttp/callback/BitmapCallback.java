package com.util.xhttp.callback;

import android.graphics.Bitmap;

import com.util.xhttp.convert.BitmapConvert;

import okhttp3.Response;

/**
 * ================================================
 * 描    述：返回图片的Bitmap，这里没有进行图片的缩放，可能会发生 OOM
 * 修订历史：
 * ================================================
 */
public abstract class BitmapCallback extends AbsCallback<Bitmap> {

    @Override
    public Bitmap convertSuccess(Response response) throws Exception {
        Bitmap bitmap = BitmapConvert.create().convertSuccess(response);
        response.close();
        return bitmap;
    }
}
