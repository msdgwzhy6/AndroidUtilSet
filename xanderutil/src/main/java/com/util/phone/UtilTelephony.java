package com.util.phone;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.util.InitUtil;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.telephony.TelephonyManager.CALL_STATE_IDLE;
import static android.telephony.TelephonyManager.CALL_STATE_OFFHOOK;
import static android.telephony.TelephonyManager.CALL_STATE_RINGING;
import static android.telephony.TelephonyManager.PHONE_TYPE_CDMA;
import static android.telephony.TelephonyManager.PHONE_TYPE_GSM;
import static android.telephony.TelephonyManager.PHONE_TYPE_NONE;
import static com.util.phone.UtilNet.getActiveConnectType;
import static com.util.phone.UtilNet.getConnectState;
import static com.util.phone.UtilNet.isCurrentConnected;

/**
 * @author xander on  2017/5/23.
 */

public class UtilTelephony {
    private static TelephonyManager tm = (TelephonyManager) InitUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);;
    /**
     * 判断设备是否是手机
     *
     * @return {@code true}: 是{@code false}: 否
     */
    private static boolean isPhone() {
        return tm != null && tm.getPhoneType() != PHONE_TYPE_NONE;
    }

    /**
     * 获取IMEI码
     * 需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}
     *
     * @return IMEI码
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI() {
        return tm != null ? tm.getDeviceId() : null;
    }

    /**
     * 获取IMSI码
     * 需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}
     *
     * @return IMSI码
     */
    @SuppressLint("HardwareIds")
    public static String getIMSI() {
        TelephonyManager tm = (TelephonyManager) InitUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getSubscriberId() : null;
    }
    /**
     * 获取移动终端类型
     *
     * @return 手机制式
     * <ul>
     * <li>{@link TelephonyManager#PHONE_TYPE_NONE } : 0 手机制式未知</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_GSM  } : 1 手机制式为GSM，移动和联通</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_CDMA } : 2 手机制式为CDMA，电信</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_SIP  } : 3</li>
     * </ul>
     */
    public static String getPhoneType() {
        int type = tm.getPhoneType();
        switch (type){
            case  PHONE_TYPE_NONE:
                return "无信号";
            case  PHONE_TYPE_CDMA:
                return "电信";
            case PHONE_TYPE_GSM:
                return "移动或联通";
            default:
                return "未知";
        }
    }

    /**
     * 判断sim卡是否准备好
     *
     * @return {@code true}: 是{@code false}: 否
     */
    public static boolean isSimCardReady() {
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * 获取Sim卡运营商名称
     * 中国移动、如中国联通、中国电信
     *
     * @return sim卡运营商名称
     */
    public static String getSimOperatorName() {
        return tm != null ? tm.getSimOperatorName() : null;
    }

    /**
     * 获取Sim卡运营商名称
     * 中国移动、如中国联通、中国电信
     *
     * @return 移动网络运营商名称
     */
    public static String getSimOperatorByMnc() {
        String operator = tm != null ? tm.getSimOperator() : null;
        if (operator == null) return null;
        switch (operator) {
            case "46000":
            case "46002":
            case "46007":
                return "中国移动";
            case "46001":
                return "中国联通";
            case "46003":
                return "中国电信";
            default:
                return operator;
        }
    }

    /**
     * 获取手机状态信息
     * 需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}
     *
     * @return DeviceId(IMEI) = 99000311726612
     * DeviceSoftwareVersion = 00
     * Line1Number =
     * NetworkCountryIso = cn
     * NetworkOperator = 46003
     * NetworkOperatorName = 中国电信
     * NetworkType = 6
     * honeType = 2
     * SimCountryIso = cn
     * SimOperator = 46003
     * SimOperatorName = 中国电信
     * SimSerialNumber = 89860315045710604022
     * SimState = 5
     * SubscriberId(IMSI) = 460030419724900
     * VoiceMailNumber = *86
     */
    @SuppressLint("HardwareIds")
    public static String getPhoneStatus() {
        if (!isPhone()) {
            return "该设备不是手机";
        }
        String result = "\n" + "设备Id(IMEI) : " + tm.getDeviceId() + "\n" +
                "设备的软件版本号 : " + getDeviceSoftwareVersion() + "\n" +
                "获取ISO标准的国家码，即国际长途区号 : " + tm.getNetworkCountryIso() + "\n" +
                "手机状态： " + getCallState() + "\n" +
                "手机类型：" + getPhoneType() + "\n" +
                "手机号 : " + tm.getLine1Number() + "\n" +
                "SIM卡的序列号：" + tm.getSimSerialNumber() + "\n" +
                "SIM卡的状态： " + tm.getSimState() + "\n" +
                "获取Sim卡运营商名称： " + getSimOperatorByMnc() + "\n" +
                "是否漫游：" + tm.isNetworkRoaming() + "\n" +
                "电话方位： " + tm.getCellLocation() + "\n" +
                "网络类型： " + getNetworkType() + "\n" +
                "网络状态： " + getConnectState() + "\n" +
                "设备的软件版本号 = " + tm.getDeviceSoftwareVersion() + "\n";
        Log.i("xxx", "getPhoneStatus" +result);
        return result;
    }
    /*
        * 当前使用的网络类型：
        * 例如： NETWORK_TYPE_UNKNOWN  网络类型未知  0
          NETWORK_TYPE_GPRS     GPRS网络  1
          NETWORK_TYPE_EDGE     EDGE网络  2
          NETWORK_TYPE_UMTS     UMTS网络  3
          NETWORK_TYPE_HSDPA    HSDPA网络  8
          NETWORK_TYPE_HSUPA    HSUPA网络  9
          NETWORK_TYPE_HSPA     HSPA网络  10
          NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4
          NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5
          NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6
          NETWORK_TYPE_1xRTT    1xRTT网络  7
        */
    private static String getNetworkType() {
        if (!isCurrentConnected()) {
            return "当前无网络连接";
        }
        int type = getActiveConnectType();
        switch (type){
            case ConnectivityManager.TYPE_WIFI:
                return "当前为 WIFI 连接";
            case ConnectivityManager.TYPE_MOBILE:
                return getMobileNetType();
            default:
                return "";
        }
    }

    /** 获取手机网络类型
     * @return
     */
    public static String getMobileNetType() {
        int type = tm.getNetworkType();
        switch (type){
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
            case TelephonyManager.NETWORK_TYPE_IWLAN:
                return "4G";
            default:
                return "网络类型未知,请检查您的手机是否插入SIM";
        }
    }

    /**
     * 待完善
     * @return
     */
    private static String getDeviceSoftwareVersion() {
        return tm.getDeviceSoftwareVersion();
    }


    //是否wifi连接
    private static boolean isWifiConnected() {
        Context context = InitUtil.getContext();
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /*
       * 电话状态：
       * 1.tm.CALL_STATE_IDLE=0    无活动
       * 2.tm.CALL_STATE_RINGING=1  响铃
       * 3.tm.CALL_STATE_OFFHOOK=2  摘机
       */
    private static String getCallState() {
        int state = tm.getCallState();
        switch (state){
            case CALL_STATE_IDLE:
                return "无活动";
            case CALL_STATE_RINGING:
                return "响铃";
            case CALL_STATE_OFFHOOK:
                return "摘机";
            default:
                return "";
        }
    }


    /**
     * 获取手机联系人
     * 需添加权限 {@code <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>}
     * 需添加权限 {@code <uses-permission android:name="android.permission.READ_CONTACTS"/>}
     *
     * @return 联系人链表
     */
    public static List<HashMap<String, String>> getAllContactInfo() {
        SystemClock.sleep(3000);
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        // 1.获取内容解析者
        ContentResolver resolver = InitUtil.getContext().getContentResolver();
        // 2.获取内容提供者的地址:com.android.contacts
        // raw_contacts表的地址 :raw_contacts
        // view_data表的地址 : data
        // 3.生成查询地址
        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri date_uri = Uri.parse("content://com.android.contacts/data");
        // 4.查询操作,先查询raw_contacts,查询contact_id
        // projection : 查询的字段
        Cursor cursor = resolver.query(raw_uri, new String[]{"contact_id"}, null, null, null);
        try {
            // 5.解析cursor
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 6.获取查询的数据
                    String contact_id = cursor.getString(0);
                    // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
                    // : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
                    // 判断contact_id是否为空
                    if (!TextUtils.isEmpty(contact_id)) {//null   ""
                        // 7.根据contact_id查询view_data表中的数据
                        // selection : 查询条件
                        // selectionArgs :查询条件的参数
                        // sortOrder : 排序
                        // 空指针: 1.null.方法 2.参数为null
                        Cursor c = resolver.query(date_uri, new String[]{"data1",
                                        "mimetype"}, "raw_contact_id=?",
                                new String[]{contact_id}, null);
                        HashMap<String, String> map = new HashMap<>();
                        // 8.解析c
                        if (c != null) {
                            while (c.moveToNext()) {
                                // 9.获取数据
                                String data1 = c.getString(0);
                                String mimetype = c.getString(1);
                                // 10.根据类型去判断获取的data1数据并保存
                                if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                                    // 电话
                                    map.put("phone", data1);
                                } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                                    // 姓名
                                    map.put("name", data1);
                                }
                            }
                        }
                        // 11.添加到集合中数据
                        list.add(map);
                        // 12.关闭cursor
                        if (c != null) {
                            c.close();
                        }
                    }
                }
            }
        } finally {
            // 12.关闭cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    /**
     * 获取手机短信并保存到xml中
     * 需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}
     * 需添加权限 {@code <uses-permission android:name="android.permission.READ_SMS"/>}
     */
    public static void getAllSMS() {
        // 1.获取短信
        // 1.1获取内容解析者
        ContentResolver resolver = InitUtil.getContext().getContentResolver();
        // 1.2获取内容提供者地址   sms,sms表的地址:null  不写
        // 1.3获取查询路径
        Uri uri = Uri.parse("content://sms");
        // 1.4.查询操作
        // projection : 查询的字段
        // selection : 查询的条件
        // selectionArgs : 查询条件的参数
        // sortOrder : 排序
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        // 设置最大进度
        int count = cursor.getCount();//获取短信的个数
        // 2.备份短信
        // 2.1获取xml序列器
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            // 2.2设置xml文件保存的路径
            // os : 保存的位置
            // encoding : 编码格式
            xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")), "utf-8");
            // 2.3设置头信息
            // standalone : 是否独立保存
            xmlSerializer.startDocument("utf-8", true);
            // 2.4设置根标签
            xmlSerializer.startTag(null, "smss");
            // 1.5.解析cursor
            while (cursor.moveToNext()) {
                SystemClock.sleep(1000);
                // 2.5设置短信的标签
                xmlSerializer.startTag(null, "sms");
                // 2.6设置文本内容的标签
                xmlSerializer.startTag(null, "address");
                String address = cursor.getString(0);
                // 2.7设置文本内容
                xmlSerializer.text(address);
                xmlSerializer.endTag(null, "address");
                xmlSerializer.startTag(null, "date");
                String date = cursor.getString(1);
                xmlSerializer.text(date);
                xmlSerializer.endTag(null, "date");
                xmlSerializer.startTag(null, "type");
                String type = cursor.getString(2);
                xmlSerializer.text(type);
                xmlSerializer.endTag(null, "type");
                xmlSerializer.startTag(null, "body");
                String body = cursor.getString(3);
                xmlSerializer.text(body);
                xmlSerializer.endTag(null, "body");
                xmlSerializer.endTag(null, "sms");
                System.out.println("address:" + address + "   date:" + date + "  type:" + type + "  body:" + body);
            }
            xmlSerializer.endTag(null, "smss");
            xmlSerializer.endDocument();
            // 2.8将数据刷新到文件中
            xmlSerializer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
