package com.fecmobile.jiubeirobot.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 共享参数缓存工具
 */
public class SharedPreferencesUtils {
    private SharedPreferences mPreferences;
    private static SharedPreferencesUtils sharedPreferencesUtils;

    private SharedPreferencesUtils(Context context) {
        if (mPreferences == null) {
            mPreferences = context.getSharedPreferences("dong", 0);
        }
    }

    /**
     * 获取单实例对象
     */
    public static SharedPreferencesUtils getInstance(Context context) {
        if (sharedPreferencesUtils == null) {
            sharedPreferencesUtils = new SharedPreferencesUtils(context);
        }
        return sharedPreferencesUtils;
    }

    /**
     * 存储String
     */
    public static void putString(SharedPreferencesUtils mPreferences, String key,
                                 String v) {
        mPreferences.mPreferences.edit().putString(key, v).commit();
    }

    /**
     * 取String
     */
    public static String getString(Context context, String key) {
        return getInstance(context).mPreferences.getString(key, "");
    }

    /**
     * 存储String
     */
    public static void putString(Context context, String key, String v) {
        getInstance(context).mPreferences.edit().putString(key, v).commit();
    }

    /**
     * 取String
     */
    public static String getString(SharedPreferencesUtils mPreferences, String key) {
        return mPreferences.mPreferences.getString(key, "");
    }

    /**
     * 取int
     */
    public static int getInt(SharedPreferencesUtils mPreferences, String key) {
        return mPreferences.mPreferences.getInt(key, -1);
    }

    /**
     * 取int
     */
    public static int getInt(Context context, String key) {
        return getInstance(context).mPreferences.getInt(key, -1);
    }

    /**
     * 存储String
     */
    public static void putInt(SharedPreferencesUtils mPreferences, String key,
                              int v) {
        mPreferences.mPreferences.edit().putInt(key, v).commit();
    }

    /**
     * 存储String
     */
    public static void putInt(Context context, String key, int v) {
        SharedPreferencesUtils.getInstance(context).mPreferences.edit().putInt(key, v).commit();

    }

    /**
     * 取Long
     */
    public static long getLong(Context context, String key) {
        return getInstance(context).mPreferences.getLong(key, -1);
    }

    /**
     * 存储String
     */
    public static void putLong(Context context, String key,
                               long v) {
        getInstance(context).mPreferences.edit().putLong(key, v).commit();
    }

    /**
     * 存储String
     */
    public static void putLong(Context context, String key, String v) {
        long l = 0;
        try {
            l = Long.parseLong(v);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        getInstance(context).mPreferences.edit().putLong(key, l).commit();
    }

    /**
     * 取Long
     */
    public static long getLong(SharedPreferencesUtils mPreferences, String key) {
        return mPreferences.mPreferences.getLong(key, -1);
    }

    /**
     * 存储String
     */
    public static void putLong(SharedPreferencesUtils mPreferences, String key,
                               long v) {
        mPreferences.mPreferences.edit().putLong(key, v).commit();
    }

}
