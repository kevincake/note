package com.lingchat.lc.util;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.lingchat.lc.LingChatApplication;
import com.lingchat.lc.R;
import com.lingchat.lc.parse.CharacterParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建者:yin_zeping
 * 创建时间:2016/3/1 14:22
 * 功能说明:
 */
public class StringUtils {
    //根据汉字获取拼音
    public static String getSellingLetter(String sourceStr) {
        CharacterParser parser = new CharacterParser();
        String pinyin = parser.getSelling(sourceStr);
        return pinyin;
    }

    ;

    //获取首字母
    public static String getFirstUpCaseLetter(String sourceStr) {
        if (sourceStr.isEmpty()){
            return "#";
        }
        String selling = getSellingLetter(sourceStr);
        String firstLetter = selling.substring(0, 1).toUpperCase();

        if (firstLetter.matches("[A-Z]")) {
            return firstLetter;
        } else {
            firstLetter = "#";
        }
        return firstLetter;
    }

    //用正则表达式判断字符串是否为数字（含负数）
    public static boolean isNumeric(String str) {
        String regEx = "^-?[0-9]+$";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        if (mat.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static Spanned getHtmlColorString(String completeStr, String filterStr){
        return Html.fromHtml(getColorString(completeStr,filterStr));
    }

    //搜索文字替换蓝色
    public static String getColorString(String completeStr, String filterStr) {
        if (completeStr==null||completeStr.isEmpty()){
            LogUtil.error("getColorString error","completeStr is null");
            return "";
        }
        if (filterStr == null || filterStr.isEmpty()){
            LogUtil.error("getColorString error","filterStr is null");
            return "";
        };
        String newTerm =getBlueString(filterStr);
        String newCompleteStr =completeStr.replaceAll(filterStr, newTerm);
        return  newCompleteStr;
    };
    //通用蓝色
    public static String getBlueString(String str){
        return  "<font color='#157efb'>" + str + "</font>";
    }

    //通用红色
    public static String getRedString(String str){
        return  "<font color='#ff0000'>" + str + "</font>";
    }

    public static boolean isPhone(String phone) {
        if(TextUtils.isEmpty(phone)) {
            return false;
        }
        boolean matches = phone.matches("(^(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7})$");
        return matches;
    }

    public static String getLingchatPhone(String phone) {
        if(TextUtils.isEmpty(phone)) {
            return "";
        }

        if(phone.length()==11){
            return phone.substring(0, 3)+"-"+phone.substring(3, 7)+"-"+phone.substring(7, phone.length());
        }
        return "";
    }

    public static boolean isContainCode(String number) {
        if (number==null){
            return false;
        }
        boolean b = number.startsWith("+86");
        return b;
    }
    public static String getPhoneFromCountyPhoneNum(String number){
        if (isContainCode(number)){
            StringBuffer buffer = new StringBuffer(number);
            String result = buffer.substring(3, number.length()).trim();
            return result;
        }
        return  "";
    }

    /**
    笨方法：String s = "你要去除的字符串";
            1.去除空格：s = s.replace('\\s','');
            2.去除回车：s = s.replace('\n','');
    这样也可以把空格和回车去掉，其他也可以照这样做。
    注：\n 回车(\u000a)
    \t 水平制表符(\u0009)
    \s 空格(\u0008)
    \r 换行(\u000d)*/
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
