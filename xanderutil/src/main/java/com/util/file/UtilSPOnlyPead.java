package com.util.file;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import static android.content.Context.MODE_PRIVATE;
import static android.util.Base64.URL_SAFE;

/***************************************************************************
 * author : Dragon TOUR @ xbb 596928539@qq.com on technology 2016/12/20.
 * 这个类设计为只读SP，为了避免你在一个类里存在读取的时候，读取不同文件下的数据造成空指针的问题
 * 举个栗子：
 *         1 ......  地方先用了保存数据，文件名为 cut
 *          Bitmap bitmap = UtilSPOnlyPead.getInstance(InitUtil.getContext())
                                        .initSPFileName("cut")//................................... 1
                                        .getBitmap(UtilEncript.getMD5("cut"));
            assert bitmap != null;
          2 ...... 读取默认文件下的数据 如果用单利，会造成空指针
          canvas.drawBitmap(getBitmapDefault("cut"), 200, 200, null);//...............................2
 ***************************************************************************/

public final class UtilSPOnlyPead {
   private Context mContext;
   private String mFileName = "default_sp_xml";

   private SharedPreferences.Editor editor;
   private SharedPreferences sharedPreferences;

   /*
   * The definition of a private variable (not static initialization, do not use the final keyword,
   * using volatile ensures that the multi thread access instance variable visibility, to avoid
   * the instance initialization when the other variables did not end when the attribute assignment,
   * by another thread calls)
   */

   private static volatile UtilSPOnlyPead instance;


   private UtilSPOnlyPead(Context context) {
      mContext = context;
   }

   public static UtilSPOnlyPead getInstance(Context context) {

      return instance = new UtilSPOnlyPead(context);
   }

   /**
    * fileName
    * 
    */
   public UtilSPOnlyPead initSPFileName(String fileName) {
      mFileName = fileName;
      sharedPreferences = mContext.getSharedPreferences(mFileName, MODE_PRIVATE);
      editor = sharedPreferences.edit();
      return instance;
   }
   public UtilSPOnlyPead initSPFileName() {
      sharedPreferences = mContext.getSharedPreferences(mFileName, MODE_PRIVATE);
      editor = sharedPreferences.edit();
      return instance;
   }


   /**
    * key get individual data through key
    *   value
    */
   public String getString(Integer key) {
      return sharedPreferences.getString(String.valueOf(key), "");
   }

   public String getString(String key) {
      return sharedPreferences.getString(key, null);
   }

   public Integer getInt(Integer key) {
     String _key = String.valueOf(key);
      if (TextUtils.isEmpty(_key)) {
         throw new NullPointerException("没有找到与"+key+"匹配的键");
      }
      return sharedPreferences.getInt(_key, 0);
   }

   public Integer getInt(String key) {
      if (TextUtils.isEmpty(key)) {
         throw new NullPointerException("没有找到与"+key+"匹配的键");
      }
      return sharedPreferences.getInt(key, 0);
   }
   /**
    *
    * key
    *  The key that stores the object, and all non base data types must implement the serialization interface
    * 
    */
   public <T extends Serializable> T getBean(String key) {
      if (TextUtils.isEmpty(key)) {
         throw new NullPointerException("没有找到与"+key+"匹配的键");
      }
      String objectBase64 = getString(key);
      Log.i("xxx", "getBean: " + objectBase64);
      //Read byte
      byte[] base64 = Base64.decode(objectBase64.getBytes(),URL_SAFE);

      //Encapsulated into byte stream
      ByteArrayInputStream bais = new ByteArrayInputStream(base64);
      ObjectInputStream bis = null;
      try {
         //Re encapsulation
         bis = new ObjectInputStream(bais);
         try {
            return (T) bis.readObject();
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         try {
            bais.close();
            if (bis != null) {
               bis.close();
            }
         } catch (IOException e) {
            e.printStackTrace();
//            Log.i("xxx", "getBean" );
         }
      }
      return null;
   }

   /**
    * Take pictures from sp
    * key
    * 
    */
   public  Bitmap getBitmap(String key){
      String temp = getString(key);
      if (TextUtils.isEmpty(temp)) {
         Log.i("xxx", "getBitmap" +mFileName);
         throw new NullPointerException("没有找到与"+key+"匹配的键");
      }
      ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inPreferredConfig = Bitmap.Config.ARGB_8888;
      return BitmapFactory.decodeStream(bais, null, options);
   }
}
