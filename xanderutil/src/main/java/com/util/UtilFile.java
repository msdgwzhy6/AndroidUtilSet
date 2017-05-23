package com.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.util.UtilEncript.getMD5;


/****************************************************************************
 * Created by xu on 2017/5/23.
 * Function: 对数据进行文件操作的工具类
 ***************************************************************************/

public final class  UtilFile {


    /**
     * 仅仅是从网络获取一张图片
     * @param imgUrl
     * @return
     */
    public static Bitmap getNetBitmap(String imgUrl){
        URL url ;
        Bitmap bitmap = null;
        try {
            url = new URL(imgUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            bitmap =  BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    /**
     * @说明： 保存图片到本地
     */
    public static String rootDir = Environment.getExternalStorageDirectory() .getAbsolutePath()+ File.separator;
    /**
     * 从网络得到一张图片并保存到SD卡
     * @param imgUrl 图片的url
     * @param fileName 文件名
     * @return 图片的文件形式
     */
    public static String getNetBitmap2Save(String imgUrl,  String fileName){
        Bitmap bitmap =  getNetBitmap(imgUrl);
        if (TextUtils.isEmpty(fileName)) {
            fileName = "img_cache";
        }
        String pathName = rootDir  + fileName + File.separator;

        /*
        * 创建目录
        * */
        File imgDir = new File(pathName);
        if (!imgDir.exists()) {
            imgDir.mkdirs();
        }

        /*
        * 创建文件
        * */
        String imgName = getMD5(imgUrl)+".png";
        File imageFile = new File(imgDir,imgName);//文件

        FileOutputStream out = null;
        if (imageFile.exists()) {
            imageFile.delete();
        }
        try{
            imageFile.createNewFile();
            out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageFile.getAbsolutePath().toString();
    }

    /**
     * 通过制定图片的本地路径得到一张bitmap
     * @param bitmapPath  本地文件的绝对路径
     * @return
     */
    public static Bitmap getLocalBitmap(String bitmapPath){
        FileInputStream streamFile;
        Bitmap bitmap = null;
        try {
            streamFile = new FileInputStream(bitmapPath);
            bitmap =  BitmapFactory.decodeStream(streamFile);
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 建立HTTP请求，并获取Bitmap对象。
     * @param urlString  图片的URL地址
     * @return 解析后的Bitmap对象
     */
    public static boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /************************************************************
     *@Author; 龙之游 @ xu 596928539@qq.com
     * 时间:2016/12/20 13:25
     * 注释:  写文件
     ************************************************************/
    public static File writeLog(String str){


        File file = null;
        Log.i("file", "__________________________________________________________________写入成功: "+str);
        try
        {
            String path=Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                    + "crash_lzhy_moneyhll.txt";
            file=new File(path);
            if(!file.exists()){
                file.createNewFile();
            }

            FileOutputStream out=new FileOutputStream(file,false); //如果追加方式用true,此处覆盖
            StringBuffer sb=new StringBuffer();
            byte[] bytes = str.getBytes();
            out.write(bytes);
            //out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
            if (file.exists()) {
                Log.i("file", "writeLog: "+file.length());
                Log.i("file", "writeLog: "+file);
                Log.i("file", "__________________________________________________________________写入成功: "+file);
                return file;
            }else {
                Log.i("file", "__________________________________________________________________写入失败: "+file);
            }
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
        return null;
    }

    /**
     * 将字符串写入文件
     * @param content
     */
    public static void writeStr2Log( String content){
        try {

//            content = "This is the content to write into file";

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                    + "crash_lzhy_moneyhll.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
