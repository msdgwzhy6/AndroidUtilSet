package com.sdk.util.http.core;

/**
 * author xander on  2017/5/31.
 * function  网络请求所需的通用配置信息
 */

public class ConfigHttp {
    public final static int HTTP_TYPE_GET = 0x10;
    public final static int HTTP_TYPE_POST = 0x11;
    public final static int PROTOCOL_TYPE_HTTP = 0x20;
    public final static int PROTOCOL_TYPE_HTTPS = 0x21;

    public static int mHttpTimeout = 2000;

    public static int httpType = HTTP_TYPE_GET;
}
