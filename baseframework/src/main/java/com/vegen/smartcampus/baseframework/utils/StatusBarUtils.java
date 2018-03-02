package com.vegen.smartcampus.baseframework.utils;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by vegen on 2018/3/2.
 */

public class StatusBarUtils {

    private static float statusAlpha = 0.3f;

    /**
     * 设置透明状态栏
     * @param activity
     */
    public static void setTransparentStatusBar(@NonNull Activity activity, boolean supportActionBar){
        ImmersionBar.with(activity)
                .reset()                    // 重置所以沉浸式参数
                .transparentStatusBar()     // 透明状态栏，不写默认透明色
                .statusBarDarkFont(true, statusAlpha)    // 如果不支持状态栏字体变色可以使用statusAlpha来指定状态栏透明度，比如白色状态栏的时候可以用到
                .supportActionBar(supportActionBar)
                .init();
    }

    /**
     * 设置透明状态栏
     * @param activity
     */
    public static void setTransparentStatusBar(@NonNull Activity activity, boolean isDark, boolean supportActionBar){
        ImmersionBar.with(activity)
                .reset()                    // 重置所以沉浸式参数
                .transparentStatusBar()     // 透明状态栏，不写默认透明色
                .statusBarDarkFont(isDark, statusAlpha)  // 状态栏文字图标，如果不支持状态栏字体变色可以使用statusAlpha来指定状态栏透明度，比如白色状态栏的时候可以用到
                .supportActionBar(supportActionBar)
                .init();
    }

    /**
     * 设置某种颜色的状态栏
     * @param activity
     * @param barColor
     */
    public static void setStatusBarColor(@NonNull Activity activity, int barColor, boolean supportActionBar){
        // 设置状态栏为白色
        ImmersionBar.with(activity).reset()
                .statusBarColor(barColor)
                .statusBarDarkFont(true, statusAlpha)
                .supportActionBar(supportActionBar)
                .init();
    }

    /**
     * 设置某种颜色的状态栏
     * @param activity
     * @param barColor
     */
    public static void setStatusBarColor(@NonNull Activity activity, int barColor, boolean isDark, boolean supportActionBar){
        // 设置状态栏为白色
        ImmersionBar.with(activity).reset()
                .statusBarColor(barColor)
                .statusBarDarkFont(isDark, statusAlpha)
                .supportActionBar(supportActionBar)
                .init();
    }

    public static void destroy(@NonNull Activity activity){
        ImmersionBar.with(activity).destroy(); //必须调用该方法，防止内存泄漏
    }
}
