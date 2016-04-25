package com.lingchat.lc.util;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.Xml;

import com.lingchat.lc.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建者:Zeping.Yin
 * 创建时间:2016/3/15 14:12
 * 功能说明:XML解析
 */
public class XmlParserUtils {

    public static Map<String, String> parseStringXmlToMapFromResource(Context context, int resourceId){
        Map<String, String> result= new HashMap<>();
        try {
            XmlResourceParser xmlResourceParser = context.getResources().getXml(resourceId);
            xmlResourceParser.next();
            int eventType = xmlResourceParser.getEventType();
            //循环，直到文档结尾
            String key = "";
            String value = "";
            while (eventType != XmlResourceParser.END_DOCUMENT){
                if (eventType == XmlResourceParser.START_DOCUMENT){
                    //文档开始
                }else if (eventType == XmlResourceParser.START_TAG){
                    //标签开始
                    if(!TextUtils.isEmpty(xmlResourceParser.getName())&&xmlResourceParser.getName().equals("string")){
                        key = xmlResourceParser.getAttributeValue(0);
                    }
                }else if (eventType == XmlResourceParser.TEXT){
                    //文本
                    value = xmlResourceParser.getText();
                }else if (eventType == XmlResourceParser.END_TAG){
                    //标签结束
                    if(!TextUtils.isEmpty(key)){
                        result.put(key, value);
                    }
                }
                eventType = xmlResourceParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
