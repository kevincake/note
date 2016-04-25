package com.lingchat.lc.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.lingchat.lc.Constants;

/**
 * Author: wuyihua
 * Time:   2016/2/25.
 * func:  全局的工具类
 */
public class Util {
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String getAlbumStorageDir(String albumName) {
        return Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)+"/"+ albumName;
    }
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    public static String Uri2String(Uri uri){
        String stringUri;
        stringUri = uri.toString();
        return stringUri;
    }
    public static Uri String2Uri(String uriString){
        Uri uri;
        uri = Uri.parse(uriString);
        return uri;
    }


}
