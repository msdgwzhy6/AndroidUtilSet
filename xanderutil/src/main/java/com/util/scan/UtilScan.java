package com.util.scan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.zxing.WriterException;
import com.util.logger.JJLogger;
import com.util.manager.ManagerActivity;
import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;


/**
 * author xander on  2017/6/8.
 * function  二维码工具类
 */

public final class UtilScan {

    /*
* 用于生成二维码（带log）
* */
    public static Bitmap createQRCode(Bitmap logBmp, String content,int width,int hight){
        try {
            return EncodingHandler.createQRCode(logBmp,content,width,hight);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    * 用于扫描二维码
    * */
    public static void scanQRCode(int requestCode){
        Intent intent = new Intent(ManagerActivity.getInstance().getCurrentActivity(),
                CaptureActivity.class);
        ManagerActivity.getInstance().getCurrentActivity()
                .startActivityForResult(intent, requestCode);
        JJLogger.i("scanQRCode  = "+requestCode);
    }

    /*
    * 二维码的扫描结果
    * */

    public static String handleQRCode(Intent data ){
        if (data != null) {
            Bundle bundle = data.getExtras();
            return bundle.getString("result");
        }else {
            return "获取信息失败";
        }

    }

}
