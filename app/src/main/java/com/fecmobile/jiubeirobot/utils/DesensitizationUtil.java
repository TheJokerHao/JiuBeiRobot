package com.fecmobile.jiubeirobot.utils;

/**
 * 脱敏工具类
 * Created by gonghuiyuan on 2016/10/12.
 * contact:gonghuiyuan516@qq.com
 */
public class DesensitizationUtil {
    /**
     * 手机脱敏
     *
     * @param cs 要脱敏的字符串
     * @return 脱敏后字符串
     */
    public static String phoneDesensitization(String phone) {
        if (phone != null && phone.length() >= 11) {
            String s = phone.substring(0, 3);
            String e = phone.substring(7, phone.length());
            phone = s + "****" + e;
        }

        return phone;
    }
}


