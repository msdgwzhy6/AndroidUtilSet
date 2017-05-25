package com.util.file;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

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
 * @author : Dragon TOUR @ xbb 596928539@qq.com on technology 2016/12/20.
 * Note: save the basic data types and objects, pictures, etc;
 * And any non basic types to implement the serializable interface including internal class
 ***************************************************************************/

public final class UtilSP {
   private Context mContext;
   private String mFileName = "spxml";

   private SharedPreferences.Editor editor;
   private SharedPreferences sharedPreferences;

   /*
   * The definition of a private variable (not static initialization, do not use the final keyword,
   * using volatile ensures that the multi thread access instance variable visibility, to avoid
   * the instance initialization when the other variables did not end when the attribute assignment,
   * by another thread calls)
   */

   private static volatile UtilSP instance;


   private UtilSP(Context context) {
      mContext = context;
   }

   public static UtilSP getInstance(Context context) {
      // When an object is instantiated, or not, it is judged (without using synchronous blocks of code, when the instance is not equal to null),
      // the object is returned directly and the operation efficiency is improve
      if (instance == null) {
         //Synchronous block of code (when the object is uninitialized, using synchronous blocks of code to ensure that
         // the object is not duplicated when it is first created) is accessed by multithreaded access
         synchronized (UtilSP.class) {
            //Uninitialized, the initial instance variable
            if (instance == null) {
               instance = new UtilSP(context);
            }
         }
      }
      return instance;
   }

   /**
    * @param fileName
    * @return
    */
   public UtilSP initSP(String fileName) {
      mFileName = fileName;
      sharedPreferences = mContext.getSharedPreferences(fileName, MODE_PRIVATE);
      editor = sharedPreferences.edit();
      return instance;
   }

   /**
    * @param imgUrlKey
    * @param imgUrlValue
    * @return
    */
   public UtilSP putString(int imgUrlKey, String imgUrlValue) {
      editor.putString(String.valueOf(imgUrlKey), imgUrlValue);
      return instance;
   }

   public UtilSP putString(String imgUrlKey, String imgUrlValue) {
      editor.putString(imgUrlKey, imgUrlValue);
      return instance;
   }

   /**
    * @param strList
    *        Encapsulate this URL in the form of a collection, and add the data back to the SP file
    * @param beginKey
    * @return
    */
   public UtilSP putList( int beginKey,List<String> strList) {
      int count = beginKey + strList.size() - 1;
      for (int i = beginKey; i < count; i++) {
         putString(i, strList.get(i));
      }
      return instance;
   }


   /**
    * @param strList
    *          Encapsulates the string of the string data you want to add
    * @return
    */
   public UtilSP putList(List<String> strList) {
      int count = strList.size();
      for (int i = 0; i < count; i++) {
         putString(i, strList.get(i));
      }
      return instance;
   }

   /**
    * Save multiple calls, save different records, number of pages, total number of pages
    * @param countKey
    * @param countValue
    */
   public UtilSP putInt(String countKey, int countValue) {
      editor.putInt(countKey, countValue);
      return instance;
   }


   /**
    * @param objKeyName index
    * @param object javaBean
    * @param <T>
    * @return
    */
   public <T extends Serializable> UtilSP putBean(String objKeyName, T object) {
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
         putString(objKeyName, obj_Base64);
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
    * @param keyName get individual data through key
    * @return  value
    */
   public String getString(int keyName) {
      return sharedPreferences.getString(String.valueOf(keyName), "");
   }

   public String getString(String keyName) {
      return sharedPreferences.getString(keyName, null);
   }

   public int getInt(int keyName) {
      return sharedPreferences.getInt(String.valueOf(keyName), 0);
   }

   public int getInt(String keyName) {
      return sharedPreferences.getInt(keyName, 0);
   }
   /**
    *
    * @param objKey
    *  The key that stores the object, and all non base data types must implement the serialization interface
    * @param <T>
    * @return
    */
   public <T> T getBean(String objKey) {
      T value;
      String objectBase64 = getString(objKey);
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
            value = (T) bis.readObject();
            return value;
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
   public UtilSP submit() {
      editor.commit();
      return instance;
   }

   /**
    * Clear all data under SP, refresh data, save data, you need to empty data first
    * @return
    */
   public UtilSP clearAll() {
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
    * @param key
    * @param bitmap
    * @return
    */
   public UtilSP putBitmap(String key,Bitmap bitmap){
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//      addFrame(toRoundCorner(bitmap,20),10, Color.GREEN).compress(Bitmap.CompressFormat.PNG, 100, baos);
      String imageBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
      putString(key,imageBase64 );
      return this;
   }

   /**
    * Take pictures from sp
    * @param key
    * @return
    */
   public  Bitmap getBitmap(String key){
      String temp = getString(key);
      if (TextUtils.isEmpty(temp)) {
         return null;
      }
      ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inPreferredConfig = Bitmap.Config.ARGB_8888;
      return BitmapFactory.decodeStream(bais, null, options);
   }
}
