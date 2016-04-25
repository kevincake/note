package com.lingchat.lc.util;

import com.lingchat.lc.bean.ClientUser;

import java.util.ArrayList;

/**
 * 创建者:eava
 * 创建时间:2016/3/24 10:07
 * 功能说明:
 */
public class ObjTempUtil {
    public static ClientUser mClientUser;

    public static long getUserId() {
        return userId;
    }

    public static void setUserId(long userId) {
        ObjTempUtil.userId = userId;
        mClientUser = null;
    }

    private static long userId;

    public static void setClientUser(ClientUser ClientUser){
        mClientUser = ClientUser;
        ObjTempUtil.userId = 0;
    }


    public static ClientUser getClientUser(){
        return mClientUser;

    }

}
