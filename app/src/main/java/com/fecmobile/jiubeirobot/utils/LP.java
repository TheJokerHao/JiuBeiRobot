package com.fecmobile.jiubeirobot.utils;

import android.util.Log;

import com.fecmobile.jiubeirobot.BuildConfig;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/5/31.
 * 针对Log日志使用print显示的打印工具类
 */

public class LP {
    private LP() {
         /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated！");
    }

    public static boolean isDebug = BuildConfig.DEBUG;//DEBUG模式才会打印


    private static final String TAG = "print";


    // 下面四个是默认tag的函数
    public static void i(Object msg) {
        if (isDebug)
            Log.i(TAG, msg + "");
    }

    public static void d(Object msg) {
        if (isDebug)
            Log.d(TAG, msg + "");
    }

    public static void e(Object msg) {
        if (isDebug)
            Log.e(TAG, msg + "");
    }

    public static void v(Object msg) {
        if (isDebug)
            Log.v(TAG, msg + "");
    }

    // 下面是传入自定义tag的函数
    public static void i(Object tag, Object msg) {
        if (isDebug)
            Log.i(tag + "", msg + "");
    }

    public static void d(Object tag, Object msg) {
        if (isDebug)
            Log.i(tag + "", msg + "");
    }

    public static void e(Object tag, Object msg) {
        if (isDebug)
            Log.i(tag + "", msg + "");
    }

    public static void v(Object tag, Object msg) {
        if (isDebug)
            Log.i(tag + "", msg + "");
    }

}
