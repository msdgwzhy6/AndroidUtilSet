package com.smart.androidutils.util;

import android.graphics.Bitmap;

import com.util.core.InitUtil;
import com.util.file.UtilSPOnlyPead;
import com.util.file.UtilSPSingleInstance;

/**
 * author xander on  2017/5/26.
 * function 为此封装SP工具类
 */

public class Util {
  public static Bitmap getBitmapDefault(String key){
      Bitmap bitmap = UtilSPOnlyPead.getInstance(InitUtil.getContext())
              .initSPFileName()
              .getBitmap(key);
      if (bitmap == null) {
          throw new NullPointerException("bitmap is null");
      }
      return bitmap;
  }
  public static void putBitmapDefault(String key,Bitmap bitmap){
      if (bitmap == null) {
          throw new NullPointerException("bitmap is null");
      }
      UtilSPSingleInstance.getInstance(InitUtil.getContext())
              .initSPFileName()
              .putBitmap(key,bitmap)
              .submit();
  }
}
