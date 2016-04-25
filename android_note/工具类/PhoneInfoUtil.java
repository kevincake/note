package com.lingchat.lc.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * Created by sunjialimn on 2016/3/31.
 */
public class PhoneInfoUtil {

    private static PhoneInfoUtil instance;

    private Context mContext;

    // / 没有连接

    public static final int NETWORN_NONE = 0;

    // / wifi连接

    public static final int NETWORN_WIFI = 5;

    // / 手机网络数据连接

    public static final int NETWORN_2G = 2;

    public static final int NETWORN_3G = 3;

    public static final int NETWORN_4G = 4;

    public static final int NETWORN_MOBILE = 8;

    public WifiManager wifiMgr;

    private PhoneInfoUtil(Context context){
        mContext = context;
    }

    public static PhoneInfoUtil getInstance(Context context){

        if(instance == null){
            synchronized (PhoneInfoUtil.class){
                if(instance == null){
                    instance = new PhoneInfoUtil(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获取mac地址
     * @return
     */
    public String getMacAddress(){


        //在wifi未开启状态下，仍然可以获取MAC地址，但是IP地址必须在已连接状态下否则为0
        String macAddress = null;

        WifiInfo info = getWifiInfo();

        if (null != info) {
            macAddress = info.getMacAddress();
            //ip = int2ip(info.getIpAddress());

        }
        return macAddress;
    }

    /**
     * 获取ip地址
     * @return
     */
    public String getIpAddress(){


        //在wifi未开启状态下，仍然可以获取MAC地址，但是IP地址必须在已连接状态下否则为0
        String ipAddress = null;

        WifiInfo info = getWifiInfo();

        if (null != info) {
            //macAddress = info.getMacAddress();
            ipAddress = int2ip(info.getIpAddress());

        }
        return ipAddress;
    }



    //获取wifi信息
    public WifiInfo getWifiInfo(){

        WifiInfo info = null;
        wifiMgr = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
        return info;
    }





    public static String int2ip(long ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }


    /**
     * 获取网络类型 2G,3G,4G,WIFI
     * @return 0为没有连接，1为2G，2为3G，3为4G，4为WIFI
     */
    public  int getNetworkState() {

        ConnectivityManager connManager = (ConnectivityManager) mContext

                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == connManager)

            return NETWORN_NONE;

        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();

        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {

            return NETWORN_NONE;

        }

        // Wifi

        NetworkInfo wifiInfo=connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(null!=wifiInfo){

            NetworkInfo.State state = wifiInfo.getState();

            if(null!=state)

                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {

                    return NETWORN_WIFI;

                }

        }

        // 网络

        NetworkInfo networkInfo=connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(null!=networkInfo){

            NetworkInfo.State state = networkInfo.getState();

            String strSubTypeName = networkInfo.getSubtypeName();

            if(null!=state)

                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {

                    switch (activeNetInfo.getSubtype()) {

                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g

                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g

                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g

                        case TelephonyManager.NETWORK_TYPE_1xRTT:

                        case TelephonyManager.NETWORK_TYPE_IDEN:

                            return NETWORN_2G;

                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g

                        case TelephonyManager.NETWORK_TYPE_UMTS:

                        case TelephonyManager.NETWORK_TYPE_EVDO_0:

                        case TelephonyManager.NETWORK_TYPE_HSDPA:

                        case TelephonyManager.NETWORK_TYPE_HSUPA:

                        case TelephonyManager.NETWORK_TYPE_HSPA:

                        case TelephonyManager.NETWORK_TYPE_EVDO_B:

                        case TelephonyManager.NETWORK_TYPE_EHRPD:

                        case TelephonyManager.NETWORK_TYPE_HSPAP:

                            return NETWORN_3G;

                        case TelephonyManager.NETWORK_TYPE_LTE:

                            return NETWORN_4G;

                        default://有机型返回16,17

                            //中国移动 联通 电信 三种3G制式

                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")){

                                return NETWORN_3G;

                            }else{

                                return NETWORN_MOBILE;

                            }

                    }

                }

        }

        return NETWORN_NONE;

    }

    /**
     * 获取手机型号
     * @return
     */
    public String getModel(){
        return Build.MODEL;
    }

    /**
     * 获取手机品牌
     * @return
     */
    public String getProduct(){
        return Build.PRODUCT;
    }

    /**
     * 获取Android版本信息
     * @return
     */
    public int getSdkInt(){
        return Build.VERSION.SDK_INT;
    }

    public String getVersonRelease(){
        return Build.VERSION.RELEASE;
    }









}
