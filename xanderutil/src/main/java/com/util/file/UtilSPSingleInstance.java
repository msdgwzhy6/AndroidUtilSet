package com.util.file;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.util.bitmap.UtilsImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.util.Base64.URL_SAFE;

/***************************************************************************
 * author : Dragon TOUR @ xbb 596928539@qq.com on technology 2016/12/20.
 * Note: save the basic data types and objects, pictures, etc;
 * And any non basic types to implement the serializable interface including internal class
 ***************************************************************************/

public final class UtilSPSingleInstance {
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

   private static volatile UtilSPSingleInstance instance;


   private UtilSPSingleInstance(Context context) {
      mContext = context;
   }

   public static UtilSPSingleInstance getInstance(Context context) {
      // When an object is instantiated, or not, it is judged (without using synchronous blocks of code, when the instance is not equal to null),
      // the object is returned directly and the operation efficiency is improve
      if (instance == null) {
         //Synchronous block of code (when the object is uninitialized, using synchronous blocks of code to ensure that
         // the object is not duplicated when it is first created) is accessed by multithreaded access
         synchronized (UtilSPSingleInstance.class) {
            //Uninitialized, the initial instance variable
            if (instance == null) {
               instance = new UtilSPSingleInstance(context);
            }
         }
      }
      return instance;
   }

   /**
    * fileName
    * 
    */
   public UtilSPSingleInstance initSPFileName(String fileName) {
      mFileName = fileName;
      sharedPreferences = mContext.getSharedPreferences(mFileName, MODE_PRIVATE);
      editor = sharedPreferences.edit();
      return instance;
   }
   public UtilSPSingleInstance initSPFileName() {
      sharedPreferences = mContext.getSharedPreferences(mFileName, MODE_PRIVATE);
      editor = sharedPreferences.edit();
      return instance;
   }

   /**
    * imgUrlKey
    * imgUrlValue
    * 
    */
   public UtilSPSingleInstance putString(Integer imgUrlKey, String imgUrlValue) {
      editor.putString(String.valueOf(imgUrlKey), imgUrlValue);
      return instance;
   }

   public UtilSPSingleInstance putString(String key, String value) {
      editor.putString(key, value);
      Log.i("xxx", "putString" +key);
      return instance;
   }

   /**
    * strList
    *        Encapsulate this URL in the form of a collection, and add the data back to the SP file
    * beginKey
    * 
    */
   public UtilSPSingleInstance putList(Integer beginKey, List<String> strList) {
      Integer count = beginKey + strList.size() - 1;
      for (Integer i = beginKey; i < count; i++) {
         putString(i, strList.get(i));
      }
      return instance;
   }


   /**
    * strList
    *  Encapsulates the string of the string data you want to add
    * 
    */
   public UtilSPSingleInstance putList(List<String> strList) {
      Integer count = strList.size();
      for (Integer i = 0; i < count; i++) {
         putString(i, strList.get(i));
      }
      return instance;
   }

   /**
    * Save multiple calls, save different records, number of pages, total number of pages
    * countKey
    * countValue
    */
   public UtilSPSingleInstance putInt(String countKey, Integer countValue) {
      if (countValue == null) {
         throw new NullPointerException("要保存的对象为空！");
      }
      editor.putInt(countKey, countValue);
      return instance;
   }


   /**
    * key index
    * object javaBean
    * 
    */
   public <T extends Serializable> UtilSPSingleInstance putBean(String key, T object) {
      if (object == null) {
         throw new NullPointerException("要保存的对象为空！");
      }
      // Creating a byte output stream
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = null;
      try {
         // Creates an object output stream and encapsulates the byte stream
         oos = new ObjectOutputStream(baos);
         // Writes an object to a byte stream
         oos.writeObject(object);
         // Character channeling encoding byte streams into Base64
         String obj_Base64 = new String(Base64.encode(baos.toByteArray(),URL_SAFE));
         Log.i("xxx", "putBean" +obj_Base64);
         putString(key, obj_Base64);
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         try {
            baos.close();
            if (oos != null) {
               oos.close();
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

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
   public UtilSPSingleInstance submit() {
      editor.commit();
      return instance;
   }

   /**
    * Clear all data under SP, refresh data, save data, you need to empty data first
    * 
    */
   public UtilSPSingleInstance clearAll() {
      File file = new File(mContext.getFilesDir(), mFileName + ".xml");
      if (file.exists()) {
         editor.clear();
      }
      return instance;
   }

   /*
   * Manual release after use
   * */
   public void release() {
      instance = null;
   }
   /**
    * Save the picture in XML
    * key
    * bitmap
    * 
    */
   public UtilSPSingleInstance putBitmap(String key, Bitmap bitmap){
      if (bitmap == null) {
         throw new NullPointerException("要保存的图片为空！");
      }
      Log.i("xxx", "getBitmap" +mFileName);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
//      bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//      addFrame(toRoundCorner(bitmap,20),10, Color.GREEN).compress(Bitmap.CompressFormat.PNG, 100, baos);
      UtilsImage.addReflection(bitmap,50).compress(Bitmap.CompressFormat.PNG, 100, baos);
      String imageBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
      putString(key,imageBase64 );
      return this;
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
