package com.util.file;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static android.content.ContentValues.TAG;
import static com.util.UtilEncript.getMD5;

/***************************************************************************
 * @author : Dragon TOUR @ xbb 596928539@qq.com on technology 2016/12/20.
 * Function:  A class of tools for file manipulation of data
 ***************************************************************************/

public final class  UtilFile {
    protected static final String TAG = "ccc";
    /**
     * Get files based on file paths
     * @param filePath file path
     * @return File
     */
    public static File getFileByPath(String filePath) {
        return TextUtils.isEmpty(filePath) ? null : new File(filePath);
    }
    /**
     * 判断问价是否存在
     * @param filePath 文件路径
     * @return {@code true}: 存在  {@code false}: 不存在
     */
    public static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判断问价是否存在
     * @param file 文件
     * @return {@code true}: 存在  {@code false}: 不存在
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 删除文件
     * Deletes a file by specifying the path
     * @param srcFilePath
     * @return {@code true}: Delete successfully<br>{@code false}: Delete failed
     */
    public static boolean deleteFile(String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }
    /**
     * 删除文件
     * Deletes a file by specifying the filename
     * @param file
     * @return {@code true}: Delete successfully<br>{@code false}: Delete failed
     */
    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * Writes an input stream to a file
     * @param filePath
     * @param is
     * @param append   Append at end of file
     * @return {@code true}: success<br>{@code false}: failure
     */
    public static boolean writeFileFromIS(String filePath, InputStream is, boolean append) {
        return writeFileFromIS(getFileByPath(filePath), is, append);
    }

    /**
     * Writes an input stream to a file
     * @param file
     * @param is
     * @param append Append at end of file
     * @return {@code true}: success<br>{@code false}: failure
     */
    public static boolean writeFileFromIS(File file, InputStream is, boolean append) {
        if (file == null || is == null) return false;
        if (!createOrExistsFile(file)) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[1024];
            int len;
            while ((len = is.read(data, 0, 1024)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsCloseIO.closeIO(is, os);
        }
    }

    /**
     * Writes String to File
     * @param filePath file path
     * @param content  file content
     * @param append  Append at end of file
     * @return {@code true}: success<br>{@code false}: faiure
     */
    public static boolean writeFileFromString(String filePath, String content, boolean append) {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    /**
     * Writes String to File
     * @param file file name
     * @param content  file content
     * @param append  Append at end of file
     * @return {@code true}: success<br>{@code false}: failure
     */
    public static boolean writeFileFromString(File file, String content, boolean append) {
        if (file == null || content == null) return false;
        if (!createOrExistsFile(file)) return false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsCloseIO.closeIO(bw);
        }
    }

    /**
     * Judge if the directory exists and
     * if not exist then to determine whether the creation succeeded
     * @param dirPath
     * @return {@code true}: Existence or creation success <br>{@code false}: Failure to exist or create
     */
    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * Judge if the directory exists and
     * if not exist then to determine whether the creation succeeded
     * @param file
     * @return {@code true}: Existence or creation success <br>{@code false}: Failure to exist or create
     */
    public static boolean createOrExistsDir(File file) {
        // If it exists and it is a directory, returns true; it is not , returns false; if it does not exist, returns the success of creation
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * Judge if the file exists and
     * if not exist then to determine whether the creation succeeded
     * @param filePath  file path
     *  @return {@code true}: Existence or creation success <br>{@code false}: Failure to exist or create
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * Judge if the file exists;
     * if not exist then to create ,and judge whether the creation succeeded
     * @param file  file
     * @return {@code true}: Existence or creation success <br>{@code false}: Failure to exist or create
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) return false;
        // if exist ,this is ,it is file,return true;else
        if (file.exists()) return file.isFile();
        //
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Judge if the file exists;
     * if it dose,delete it ;
     * then create file ;
     * @param filePath  file path
     * @return {@code true}: create success<br>{@code false}: create failure
     */
    public static boolean createFileByDeleteOldFile(String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    /**
     * Judge if the file exists;
     * if it dose,delete it ;
     * then create file ;
     * @param file  file
     * @return {@code true}: create success<br>{@code false}: create failure
     */
    public static boolean createFileByDeleteOldFile(File file) {
        if (file == null) return false;
        // File exists and deletion failed. Return false
        if (file.exists() && file.isFile() && !file.delete()) return false;
        // Failed to create directory back false
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Just get a picture from the Internet
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
     * @说明： Save picture to local
     */
    public static String rootDir = Environment.getExternalStorageDirectory() .getAbsolutePath()+ File.separator;
    /**
     * Get a picture from the network and save it to the SD card
     * @param imgUrl URL of the picture
     * @param fileName File name stored
     * @return Picture file form
     */
    public static String getNetBitmap2Save(String imgUrl,  String fileName){
        Bitmap bitmap =  getNetBitmap(imgUrl);
        if (TextUtils.isEmpty(fileName)) {
            fileName = "img_cache";
        }
        String pathName = rootDir  + fileName + File.separator;

        /*
        * create directory
        * */
        File imgDir = new File(pathName);
        if (!imgDir.exists()) {
            imgDir.mkdirs();
        }

        /*
        * create a file
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
     * Get a bitmap by specifying the local path of the image
     * @param bitmapPath  Absolute path of local file
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
     * Establish the HTTP request and get the bitmap object
     * @param urlString  URL of the picture
     * @return  Parsed bitmap object
     */
    public static boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
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
            Log.i(TAG, "writeStr2Log: "+file.getAbsolutePath().toString());
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
    public static String readSourceFromUrl(String urlParam,String encoding){
        InputStreamReader isr = null;
        StringBuilder html = new StringBuilder();
        InputStream is = null;
        String charset ="utf-8";
        if (!TextUtils.isEmpty(encoding)) {
            charset = encoding;
        }
        try{
            URL url = new URL(urlParam); //根据Strng表现形式创建URL对象

            URLConnection urlConnection = url.openConnection();//返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接

            Log.i(TAG, "readSourceFromUrl: "+urlConnection.getContentEncoding());
            //urlConnection.setConnectTimeout(4000); //设置链接超时

            is = urlConnection.getInputStream();//返回从打开的连接中读取到的输入流对象

            isr = new InputStreamReader(is, charset);

            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while((line = br.readLine()) != null)
            {
                html.append(line).append("\r\n");
            }

        } catch(IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        writeStr2Log(html.toString());
        return html.toString();
    }

}