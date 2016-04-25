package com.lingchat.lc.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.lingchat.lc.R;
import com.lingchat.lc.network.HttpContants;

import java.util.Map;

/**
 * 创建者:eava
 * 创建时间:2016/3/7 17:48
 * 功能说明:
 */
public class HttpUtils {
    //get参数的封装
    public static String getMethodParams(Map<String, String> params) {
        String paramsStr = "";
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                paramsStr = entry.getKey().toString() + "=" + entry.getValue().toString() + "&" + paramsStr;
            }
        }
        return paramsStr;
    }

    public static String getVersion(Context context){
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return pi.versionName+"."+ appInfo.metaData.getInt(HttpContants.CHANEL_ID);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
