package com.util.phone;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import com.util.core.InitSDK;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * author xander on  2017/6/5.
 * function
 */

public final class UtilDevice {
    /**
     * 获取手机内存大小
     *
     */
    public static String getTotalMemory() {
        String str1 = "/proc/meminfo";// 读出的内核信息进行解释
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            initial_memory = Integer.valueOf(arrayOfString[1]) * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException ignored) {
        }
        return Formatter.formatFileSize(InitSDK.getContext(), initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }
    /**
     * 获取当前可用内存大小
     *
     *
     */
    public static String getAvailMemory() {
        ActivityManager am = (ActivityManager) InitSDK.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(InitSDK.getContext(), mi.availMem);
    }
    /**
     * 获得SD卡总大小
     *
     *
     */
    public static  String getSDTotalSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(InitSDK.getContext(), blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     *
     */
    public static  String getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(InitSDK.getContext(), blockSize * availableBlocks);
    }

}
