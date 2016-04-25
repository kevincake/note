package com.dajia.mobile.android.framework.component.onActivityForResult;

import android.app.Activity;
import android.content.Intent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OnActivityForResultUtils
 * 支持Android的OnActivityForResult方法
 * @author hewanxian
 * @date 16/4/5
 * Copyright © 2016年 branch.6.3.0.portal.3.2.AppBox.TabStyle. All rights reserved.
 */
public class OnActivityForResultUtils {
    public static Map<Integer, List<OnActivityForResultCallback>> resultMap = new HashMap<Integer, List<OnActivityForResultCallback>>();

    public static void startActivityForResult(Activity activity, Integer requestCode, Intent intent, OnActivityForResultCallback callback) {
        List<OnActivityForResultCallback> callbackList = resultMap.get(requestCode);
        if (callbackList == null) {
            callbackList = new ArrayList<OnActivityForResultCallback>();
            resultMap.put(requestCode, callbackList);
        }
        resultMap.get(requestCode).add(0, callback);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<OnActivityForResultCallback> callbackList = resultMap.get(requestCode);
        if (null != callbackList) {
            for (OnActivityForResultCallback callback : callbackList) {
                if (Activity.RESULT_CANCELED == resultCode) {
                    callback.cancel(data);
                } else {
                    callback.success(resultCode, data);
                }
                callback.result(resultCode, data);
            }
        }
    }
}
