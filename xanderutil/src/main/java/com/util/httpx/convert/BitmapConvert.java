package com.util.httpx.convert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

/**
 * ================================================
 * 创建日期：16/9/11
 * 描    述：字符串的转换器
 * 修订历史：
 * ================================================
 */
public class BitmapConvert implements Converter<Bitmap> {

    public static BitmapConvert create() {
        return BitmapConvert.ConvertHolder.convert;
    }

    private static class ConvertHolder {
        private static BitmapConvert convert = new BitmapConvert();
    }

    @Override
    public Bitmap convertSuccess(Response value) throws Exception {
        return BitmapFactory.decodeStream(value.body().byteStream());
    }
}
