package com.util.phone;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import com.util.core.InitSDK;
import com.util.http.UtilHttpString;
import com.util.http.core.callback.IStringCallback;
import com.util.permission.PermissionCallback;
import com.util.permission.UtilPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * author xander on  2017/6/5.
 * function
 */

public final class UtilDevice {
    public static void getLocation() {
        String serviceString = Context.LOCATION_SERVICE;// 获取的是位置服务
        final LocationManager locationManager = (LocationManager) InitSDK.getContext().getSystemService(serviceString);// 调用getSystemService()方法来获取LocationManager对象
        final String provider = judgeProvider(locationManager);;// 指定LocationManager的定位方法

        UtilPermission.getInstance().initPermissionTypes(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
                .setPermissionCallback(new PermissionCallback() {
                    @Override
                    public void onGranted() {
                        Log.i("xxx", "onGranted" );
                        double latitude=0.0;
                        double longitude =0.0;
                        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                getLocationCode(location);
                            }
                        }else{
                            LocationListener locationListener = new LocationListener() {

                                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                                @Override
                                public void onStatusChanged(String provider, int status, Bundle extras) {

                                }

                                // Provider被enable时触发此函数，比如GPS被打开
                                @Override
                                public void onProviderEnabled(String provider) {

                                }

                                // Provider被disable时触发此函数，比如GPS被关闭
                                @Override
                                public void onProviderDisabled(String provider) {

                                }

                                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                                @Override
                                public void onLocationChanged(Location location) {
                                    if (location != null) {
                                        Log.e("Map", "Location changed : Lat: "
                                                + location.getLatitude() + " Lng: "
                                                + location.getLongitude());
                                    }
                                }
                            };
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 0,locationListener);
                            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if(location != null){
                                latitude = location.getLatitude(); //经度
                                longitude = location.getLongitude(); //纬度
                                getLocationCode(location);
                            }
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });


    }
    /**
     * 得到当前经纬度并开启线程去反向地理编码
     */
    private static void getLocationCode(Location location) {
        String latitude = location.getLatitude()+"";
        String longitude = location.getLongitude()+"";
        final String[] str = {null};//服务器返回的数据
        String url = "http://api.map.baidu.com/geocoder/v2/";
//        new MyAsyncTask(url).execute();
        new UtilHttpString().get(url)
                .addParam("ak","pPGNKs75nVZPloDFuppTLFO3WXebPgXg")
                .addParam("callback","renderReverse")
                .addParam("location",latitude+","+longitude)
                .addParam("output","json")
                .addParam("pois","0")
                .initHttpStringCallback(new IStringCallback() {
                    @Override
                    public void onStringSuccess(String result) {
                        try {
                            str[0] = str[0].replace("renderReverse&&renderReverse","");
                            str[0] = str[0].replace("(","");
                            str[0] = str[0].replace(")","");
                            JSONObject jsonObject = new JSONObject(str[0]);
                            JSONObject address = jsonObject.getJSONObject("result");
                            String city = address.getString("formatted_address");
                            String district = address.getString("sematic_description");
                            Log.i("xxx", "onStringSuccess" +"当前位置："+city+district);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Exception e) {

                    }
                });
    }
    /**
     * 判断是否有可用的内容提供器
     * @return 不存在返回null
     */
    private static String judgeProvider(LocationManager locationManager) {
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if(providers.contains(LocationManager.GPS_PROVIDER)){
            //如果是GPS
            return  LocationManager.GPS_PROVIDER;
        }else if(providers.contains(LocationManager.NETWORK_PROVIDER)){
            //如果是Network
            return  LocationManager.NETWORK_PROVIDER;
        }else{
            return null;
        }
    }
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
