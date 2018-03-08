package com.vegen.smartcampus.baseframework.utils;

import android.util.Log;

import com.vegen.smartcampus.baseframework.BuildConfig;

/**
 * @creation_time: 2017/3/27
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: Log统一管理类
 */

public class LogUtils
{

    private LogUtils()
    {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = false;

    private static final String TAG = "SmartCampus";

    // 下面四个是默认tag的函数
    public static void i(String msg)
    {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg)
    {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg)
    {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg)
    {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg)
    {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg)
    {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg)
    {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg)
    {
        if (isDebug)
            Log.v(tag, msg);
    }
}
