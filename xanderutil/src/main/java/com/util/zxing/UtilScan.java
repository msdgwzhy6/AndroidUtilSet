package com.util.zxing;

import android.content.Intent;
import android.graphics.Bitmap;

import com.util.manager.ManagerActivity;
import com.util.zxing.activity.CaptureActivity;
import com.util.zxing.encoding.EncodingHandler;

/**
 * author xander on  2017/6/8.
 * function  二维码工具类
 */

public final class UtilScan {
    /*
    * 用于扫描二维码
    * */
    public static void scanQRCode(int requestCode){
        Intent intent = new Intent(ManagerActivity.getInstance().getCurrentActivity(),
                CaptureActivity.class);
        ManagerActivity.getInstance().getCurrentActivity()
                .startActivityForResult(intent, requestCode);
    }

    /*
    * 用于生成二维码（带log）
    * */
    public static Bitmap createQRCode(Bitmap logBmp, String content,int width,int hight){
        return EncodingHandler.createQRCode(logBmp,content,width,hight);
    }
}
