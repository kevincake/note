package com.lingchat.lc.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 创建者:yin_zeping
 * 创建时间:2016/3/2 14:25
 * 功能说明:
 */
public class KeyboardUtils {
    public static void showSoftInput(Context context, View view) {
        if (context == null) {
            return;
        }
        InputMethodManager img = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean showInput = img.showSoftInput(view,
                InputMethodManager.SHOW_FORCED);
        if (!showInput) {
            img.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideSoftInput(Context context, View view) {
        if (context == null || view == null) {
            return;
        }
        InputMethodManager img = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        img.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void switchSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //得到InputMethodManager的实例
        if (imm.isActive()) {
            //如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

}
