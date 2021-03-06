package com.sdk.util.common;

import android.content.Intent;
import android.net.Uri;

import com.sdk.util.manager.ManagerActivity;

/**
 * author xander on  2017/6/1.
 * function
 */

public class UtilCommon {

    /*
    * 使用系统浏览器
    * */
    public static void useDefaultBrowser(String url){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ManagerActivity.getInstance().getCurrentActivity().startActivity(intent);
    }
/*    *//*
    * 在新的activity中用webview浏览网页
    * *//*
    public static void useWebView(String url){
        Intent intent = new Intent(ManagerActivity.getInstance().getCurrentActivity(),DetailActivity.class);
//                intent.setAction("com.sdk.ad.how.old.are.you");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        intent.putExtras(bundle);

        ManagerActivity.getInstance().getCurrentActivity().startActivity(intent);
    }*/
}
