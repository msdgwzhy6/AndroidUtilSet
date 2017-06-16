package com.sdk.util.common;

/**
 * author xander on  2017/6/14.
 * function
 */

public final class ErrorCode {

   /* 10001 暂无开屏广告资源

    10002 请求开屏广告数据失败

    10003 请求广告数据超时

    10004 广告数据未存至本地，跳过

    10005 请求网络图片失败 //

    10006 预加载广告数据失败 //


    10010 CPM统计请求失败 //

    10100 其他错误*/
    public static int CODE_NO_RESOURCE = 10000; //暂无开屏广告资源
    public static int CODE_REQUEST_DATA = 10001; //暂无开屏广告资源
    public static int CODE_REQUEST_IMAGE = 10002;//请求网络图片失败
    public static int CODE_PRELOAD = 10003;//请求广告数据超时
    public static int CODE_REQUEST_CPM = 10004;//CPM统计请求失败
    public static int CODE_REQUEST_NET = 10005;//网络请求失败
}
