package com.sdk.util.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * author xander on  2017/6/6.
 * function 压缩有关
 * Bitmap压缩都是围绕这个来做文章：Bitmap所占用的内存 = 图片长度 x 图片宽度 x 一个像素点占用的字节数。
 * 3个参数，任意减少一个的值，就达到了压缩的效果。
 * 这样算来一共有 3*2*1 = 6 种 压缩算法
 */

public final class UtilBitmapCompress {

    /**
     * 判断bitmap对象是否为空
     *
     *  src 源图片
     *  {@code true}: 是<br>{@code false}: 否
     */
    private static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    //****************************************************** 缩放图片 ****************************
    /**
     * 按缩放压缩（按用户指定的宽高缩放；且不回收）
     *  src       源图片
     *  newWidth  新宽度
     *  newHeight 新高度
     *  缩放压缩后的图片
     */
    public static Bitmap compressByScale(Bitmap src, int newWidth, int newHeight) {
        return scale(src, newWidth, newHeight, false);
    }
    /**
     * 按缩放压缩（按用户指定的比例缩放；且不回收）
     *  src         源图片
     *  scaleWidth  缩放宽度倍数
     *  scaleHeight 缩放高度倍数
     *  缩放压缩后的图片
     */
    public static Bitmap compressByScale(Bitmap src, float scaleWidth, float scaleHeight) {
        return scale(src, scaleWidth, scaleHeight, false);
    }
    /**
     * 按缩放压缩（用户指定的宽高缩放；用户指定是否回收）
     *  src       源图片
     *  newWidth  新宽度
     *  newHeight 新高度
     *  recycle   是否回收
     *  缩放压缩后的图片
     */
    public static Bitmap compressByScale(Bitmap src, int newWidth, int newHeight, boolean recycle) {
        return scale(src, newWidth, newHeight, recycle);
    }
    /**
     * 按缩放压缩(用户指定的比例缩放；用户指定是否回收)
     *  src         源图片
     *  scaleWidth  缩放宽度倍数
     *  scaleHeight 缩放高度倍数
     *  recycle     是否回收
     *  缩放压缩后的图片
     */
    public static Bitmap compressByScale(Bitmap src, float scaleWidth, float scaleHeight, boolean recycle) {
        return scale(src, scaleWidth, scaleHeight, recycle);
    }


    /**
     *  src       源图片
     *  newWidth  新宽度
     *  newHeight 新高度
     *  recycle   是否回收
     *  缩放后的图片
     *  这里是将图片压缩成用户所期望的长度和宽度，但是这里要说，如果用户期望的长度和宽度和原图长度宽度相差太多的话，图片会很不清晰。
     */
    private static Bitmap scale(Bitmap src, int newWidth, int newHeight, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }


    /**
     *  src         源图片
     *  scaleWidth  缩放宽度倍数 0.5
     *  scaleHeight 缩放高度倍数 0.5
     *  recycle     是否回收 0000
     *  缩放后的图片 size = scaleWidth*scaleHeight*原始高度
     *  when 长度和宽度分别缩小了一半，图片大小缩小了四分之一。
     */
    private static Bitmap scale(Bitmap src, float scaleWidth, float scaleHeight, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Matrix matrix = new Matrix();
        matrix.setScale(scaleWidth, scaleHeight);
        Bitmap ret = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }


    //**************************************************** * 按质量压缩 *************************************************
    /*
    * 图片的大小是没有变的，因为质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度等;
    * 来达到压缩图片的目的，这也是为什么该方法叫质量压缩方法。
    * 那么，图片的长，宽，像素都不变，那么bitmap所占内存大小是不会变的;
    * 但是bytes.length是随着quality变小而变小的。
    * 这样适合去传递二进制的图片数据，比如微信分享图片，要传入二进制数据过去，限制32kb之内'
    * 这里要说，如果是bit.compress(CompressFormat.PNG, qualityLong, baos);
    * 这样的png格式，quality就没有作用了，bytes.length不会变化，因为png图片是无损的，不能进行压缩;
    */

    /**
     *  src     源图片
     *  qualityLong 质量
     *  质量压缩后的图片
     */
    public static Bitmap compressByIntQuality(Bitmap src, int quality) {
        return qualityInt(src, quality, false);
    }
    /**
     *  src         源图片
     *  maxByteSize 允许最大值字节数
     *  质量压缩压缩过的图片
     */
    public static Bitmap compressByQualityLong(Bitmap src, long maxByteSize) {
        return qualityLong(src, maxByteSize, false);
    }
    /**
     *  src     源图片
     *  qualityLong 质量
     *  recycle 是否回收
     *  质量压缩后的图片
     */
    private static Bitmap qualityInt(Bitmap src,  int quality, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) src.recycle();
        Log.i("xxx", "压缩后图片的大小" + (src.getByteCount() / 1024 / 1024)
                + "M宽度为" + src.getWidth() + "高度为" + src.getHeight()
                + "bytes.length=  " + (bytes.length / 1024) + "KB"
                + "quality=" + quality);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     *  src         源图片
     *  maxByteSize 允许最大值字节数
     *  recycle     是否回收
     *  质量压缩压缩过的图片
     */
    private static Bitmap qualityLong(Bitmap src, long maxByteSize, boolean recycle) {
        if (isEmptyBitmap(src) || maxByteSize <= 0) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        while (baos.toByteArray().length > maxByteSize && quality > 0) {
            baos.reset();
            src.compress(Bitmap.CompressFormat.JPEG, quality -= 5, baos);
        }
        if (quality < 0) return null;
        byte[] bytes = baos.toByteArray();
        Log.i("xxx", "压缩后图片的大小" + (src.getByteCount() / 1024 / 1024)
                + "M宽度为" + src.getWidth() + "高度为" + src.getHeight()
                + "bytes.length=  " + (bytes.length / 1024) + "KB"
                + "quality=" + quality);
        if (recycle && !src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    //********************************************************** 按采样大小压缩 **********************************

    /*
    * 设置inSampleSize的值(int类型)后，假如设为2，则宽和高都为原来的1/2，宽高都减少了，自然内存也降低了;
    * 代码没用过options.inJustDecodeBounds = true, 因为是固定来取样的数据;
    * 为什么这个压缩方法叫采样率压缩，是因为配合inJustDecodeBounds;
    * 先获取图片的宽、高【这个过程就是取样】，然后通过获取的宽高，动态的设置inSampleSize的值;
    * 当inJustDecodeBounds设置为true的时候，BitmapFactory通过decodeResource或者decodeFile解码图片时，将会返回空(null)的Bitmap对象;
    * 这样可以避免Bitmap的内存分配，但是它可以返回Bitmap的宽度、高度以及MimeType;
    * */
    /**
     *  src        源图片
     *  sampleSize 采样率大小
     *  按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(Bitmap src, int sampleSize) {
        return compressBySampleSize(src, sampleSize, false);
    }

    /**
     * 按采样大小压缩
     *
     *  src        源图片
     *  sampleSize 采样率大小
     *  recycle    是否回收
     *  按采样率压缩后的图片
     */
    private static Bitmap compressBySampleSize(Bitmap src, int sampleSize, boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) src.recycle();

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    //***************************************************  RGB_565法 ********************

    /*
    * */

    public static Bitmap compressByRGB(Bitmap src){
             return rgb(src,false);
    }
    public static Bitmap compressByRGB(Bitmap src,boolean recycle){
             return rgb(src,recycle);
    }
    private static Bitmap rgb(Bitmap src,boolean recycle){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }
    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
