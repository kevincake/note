/*
 * 深圳小旺网络科技有限公司 
 */
package com.lingchat.lc.util;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;

/**
 * 创建者: Zeping.Yin
 * 创建时间: 2016/3/7 12:21
 * 功能说明: 粘贴板 工具类
 */
public class ClipboardUtil {

	public static void setTextToClipboard(Context context, String content) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			android.content.ClipboardManager cm = (android.content.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			cm.setPrimaryClip(ClipData.newPlainText(null, content));
		} else {
			android.text.ClipboardManager cm = (android.text.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			cm.setText(content);
		}
	}

	public static String getTextFromClipboard(Context context, String content) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			android.content.ClipboardManager cm = (android.content.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			if (cm.hasPrimaryClip()) {
				if (cm.getPrimaryClip().getItemCount() > 0) {
					return cm.getPrimaryClip().getItemAt(0).getText().toString();
				}
			}
		} else {
			android.text.ClipboardManager cm = (android.text.ClipboardManager) context
					.getSystemService(Context.CLIPBOARD_SERVICE);
			if (cm.hasText()) {
				return cm.getText().toString();
			}
		}
		return null;
	}
}
