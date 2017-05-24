package com.util.phone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.util.InitUtil;

/**
 * @author xander on  2017/5/24.
 */

public class UtilNet {
    private static ConnectivityManager mConnectivityManager;
    private static NetworkInfo mActiveNetworkInfo;//当前正在活动的网络

    static {
        Context context = InitUtil.getContext();
        if (context != null) {
            mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager == null) {
                throw new NullPointerException("ConnectivityManager  is null！");
            }
        }else {
            throw new NullPointerException("Please initialise  InitUtil.init(Context mContext) in your Application or Activity before using Context！After you can get global variable by InitUtil.getContext()");
        }
    }


    /*
    * 通过获取该框架所支持的所有网络类型,来判断当前是否有网络连接
    * */
    public static NetworkInfo[]  supportAllNetwork() {
        NetworkInfo[] networkInfo = mConnectivityManager.getAllNetworkInfo();
        if (networkInfo != null) {
            return networkInfo;
            /*//遍历网络状态，看是否有网络正在连接
            for (NetworkInfo networkInfo : mNetworkInfo){
                if (networkInfo.isConnected()) {
                    return true;
                }
            }*/
        }
        return null;
    }
    /*
    * 判断当前是否有网络连接
    * */
    public static boolean isCurrentConnected() {
        mActiveNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mActiveNetworkInfo != null) {
            return mActiveNetworkInfo.isConnected();
        }
        return false;
    }
    /*
    * 判断是否已经连接或正在连接
    * */
    public static boolean isCurrentConnecting(){
        mActiveNetworkInfo = mConnectivityManager.getActiveNetworkInfo();//在使用之前，要先判断是否有网络连接
        if (mActiveNetworkInfo!=null) {
            return mActiveNetworkInfo.isConnectedOrConnecting();
        }else {
            return false;
        }
    }
    /*
    * 判断是否支持 wifi 连接
    * */
    public static boolean isWifiAvailable() {
        mActiveNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mActiveNetworkInfo != null) {
            return mActiveNetworkInfo.isAvailable();
        }
        return false;
    }
    /*
    * 判断手机卡是否支持网络连接
    * */
    public static boolean isMobileNetAvailable() {
        mActiveNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mActiveNetworkInfo != null) {
            return mActiveNetworkInfo.isAvailable();
        }
        return false;
    }
    /*
    * 判断wifi是否连接
    * */
    public static boolean isWifiConnected() {
        mActiveNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mActiveNetworkInfo != null&&mActiveNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /*
    * 判断当前的网络连接状态(连接、断开...)
    * */
    public static NetworkInfo.DetailedState getConnectState(){
        mActiveNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mActiveNetworkInfo != null) {
            return mActiveNetworkInfo.getDetailedState();
        }
        return NetworkInfo.DetailedState.DISCONNECTED;
    }


    /**
     * 获取当前网络连接类型 ConnectivityManager.TYPE_WIFI
     * @return {@code -1} if network disconnect
     */
    public static int getActiveConnectType(){
        mActiveNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mActiveNetworkInfo!=null) {
            return mActiveNetworkInfo.getType();
        }else {
            return -1;
        }
    }
    /**
     *  获取网络类型名称(一般取值“WIFI”或“MOBILE”)
     */
    public static String getActiveConnectTypeName(){
        mActiveNetworkInfo = mConnectivityManager.getActiveNetworkInfo();//在使用之前，要先判断是否有网络连接
        if (mActiveNetworkInfo!=null) {
            return mActiveNetworkInfo.getTypeName();
        }else {
            return null;
        }
    }


    /*
    * 判断移动网络是否连接
    * */
    public static boolean isMobileConnected() {
        NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mMobileNetworkInfo != null) {
            return mMobileNetworkInfo.isConnected();
        }
        throw new NullPointerException("NetworkInfo is null");
    }


}
