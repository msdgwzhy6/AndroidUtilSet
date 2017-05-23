package com.util;

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
import java.io.StreamCorruptedException;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.util.Base64.URL_SAFE;

/***************************************************************************
 * 创建者: 龙之游 技术部 @ xbb 596928539@qq.com on 2016/12/20.
 * 注释: 保存基本数据类型和对象，图片等,
 * 任何一个非基本类型 都要去实现Serializable接口 包括内部类
 ***************************************************************************/

public final class UtilSP {
   private Context mContext;
   private String mFileName;

   private SharedPreferences.Editor editor;
   private SharedPreferences sharedPreferences;
   /************************************************************
    * 注释: 定义一个静态私有变量(不初始化，不使用final关键字，
    * 使用volatile保证了多线程访问时instance变量的可见性，避免了instance初始化时其他变量属性还没赋值完时，被另外线程调用)
    ************************************************************/
   private static volatile UtilSP instance;


   private UtilSP(Context context) {
      mContext = context;
   }

   public static UtilSP getInstance(Context context) {
      // 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
      if (instance == null) {
         //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
         synchronized (UtilSP.class) {
            //未初始化，则初始instance变量
            if (instance == null) {
               instance = new UtilSP(context);
            }
         }
      }
      return instance;
   }

   /**
    * @param fileName
    * @return 在设置文件名的时候，再次获取一个SP
    */
   public UtilSP setFileName(String fileName) {
      mFileName = fileName;
      sharedPreferences = mContext.getSharedPreferences(fileName, MODE_PRIVATE);
      editor = sharedPreferences.edit();
      return instance;
   }

   /**
    * 保存String
    *
    * @param imgUrlKey
    *        键
    * @param imgUrlValue
    *        值
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
    *        缓存的集合
    *        以集合的形式封装这个url ,将这些数据向后添加到SP文件中
    * @param beginKey
    * @return
    */
   public UtilSP putList(List<String> strList, int beginKey) {
      int count = beginKey + strList.size() - 1;
      for (int i = beginKey; i < count; i++) {
         putString(i, strList.get(i));
      }
      return instance;
   }


   /**
    * @param strList
    *        封装了你要添加的String数据的字符串
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
    * @param countKey
    * @param countValue
    *        保存 多次调用保存不同的记录数 页数  页码 总数
    */
   public UtilSP putInt(String countKey, int countValue) {
      editor.putInt(countKey, countValue);
      return instance;
   }

   /**
    * @param keyName
    *        通过key获取单个数据
    * @return 读取到的值value
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


   public <T extends Serializable> UtilSP putBean(String objKeyName, T object) {
      // 创建字节输出流
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = null;
      try {
         // 创建对象输出流，并封装字节流
         oos = new ObjectOutputStream(baos);
         // 将对象写入字节流
         oos.writeObject(object);
         // 将字节流编码成base64的字符窜
         String obj_Base64 = new String(Base64.encode(baos.toByteArray(),URL_SAFE));
         Log.i("xxx", "putBean" +obj_Base64);
         putString(objKeyName, obj_Base64);
         Log.i("xxx", "putBean" +"ok 存储成功");
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         try {
            if (baos != null) {
               baos.close();
            }
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
    * @param objKey
    *        存储该对象的键,所有非基本数据类型都必须实现序列化接口
    * @param <T>
    * @return
    */
   public <T> T getBean(String objKey) {
      T value;
      String objectBase64 = getString(objKey);
      Log.i("xxx", "getBean: " + objectBase64);
      //读取字节
      byte[] base64 = Base64.decode(objectBase64.getBytes(),URL_SAFE);

      //封装到字节流
      ByteArrayInputStream bais = new ByteArrayInputStream(base64);
      ObjectInputStream bis = null;
      try {
         //再次封装
         bis = new ObjectInputStream(bais);
         try {
            value = (T) bis.readObject();
            return value;
         } catch (ClassNotFoundException e) {

            e.printStackTrace();
         }
      } catch (StreamCorruptedException e) {

         e.printStackTrace();
      } catch (IOException e) {

         e.printStackTrace();
      } finally {
         try {
            if (bais != null) {
               bais.close();
            }
            if (bis != null) {
               bis.close();
            }
         } catch (IOException e) {
            e.printStackTrace();
            Log.i("xxx", "getBean" );
         }
      }
      return null;
   }

   /************************************************************
    * 注释:  清除sp下的所有数据  刷新数据后，保存数据需要先清空数据
    ************************************************************/
   public UtilSP clearAll() {
      File file = new File(mContext.getFilesDir(), mFileName + ".xml");
      if (file.exists()) {
         editor.clear();
      }
      return instance;
   }

   public UtilSP submit() {
      editor.commit();
      return instance;
   }

   /*
   * 使用后手动释放
   * */
   public void release() {
      instance = null;
   }


   /**将图片保存到xml中
    * @param key
    * @param bitmap
    * @return
    */
   public UtilSP putBitmap(String key,Bitmap bitmap){
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
      String imageBase64 = new String(Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT));
      putString(key,imageBase64 );
      return this;
   }

   /**
    * 从SP中取出图片
    * @param key
    * @return
    */
   public  Bitmap getBitmap(String key){
      String temp = getString(key);
      if (TextUtils.isEmpty(temp)) {
         return null;
      }
      ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
      return BitmapFactory.decodeStream(bais);
   }
}
