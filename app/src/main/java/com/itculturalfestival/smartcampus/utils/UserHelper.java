package com.itculturalfestival.smartcampus.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.itculturalfestival.smartcampus.Constant;
import com.itculturalfestival.smartcampus.entity.db.SmartUser;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * @creation_time: 2017/4/9
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class UserHelper {
    
    /**
     * 判断微信客户端是否可用
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 登录后保存的信息
     * @param context
     * @param smartUser
     */
    public static void saveUserDetail(Context context, SmartUser smartUser){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.USER_DETAIL, Context.MODE_PRIVATE).edit();
        editor.putString(Constant.USER_NAME, smartUser.getUsername() == null ? "" : smartUser.getUsername());
        editor.putString(Constant.USER_SEX, smartUser.getSex() == null ? "" : smartUser.getSex());
        editor.putString(Constant.USER_SCHOOL_NAME, smartUser.getSchoolName() == null ? "" : smartUser.getSchoolName());
        editor.putString(Constant.USER_PHONE, smartUser.getPhone() == null ? "" : smartUser.getPhone());
        editor.putString(Constant.USER_HEAD_URL, smartUser.getHeadUrl() == null ? "" : smartUser.getHeadUrl());
        editor.putInt(Constant.USER_SCHOOL_ID, smartUser.getSchoolId() == null ? -1 : (int)smartUser.getSchoolId());
        editor.commit();
    }

    /**
     * 退出登录后清空本地数据
     * @param context
     */
    public static void clearUserDetail(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constant.USER_DETAIL, Context.MODE_PRIVATE).edit();
        editor.clear();
    }

    /**
     * 也即是手机号注册唯一标识不可更改
     * @param context
     * @return
     */
    public static String getName(Context context){
        return (String) SharedPreferencesUtils.getData(context, Constant.USER_DETAIL, Constant.USER_NAME, "");
    }

    public static String getSex(Context context){
        return (String) SharedPreferencesUtils.getData(context, Constant.USER_DETAIL, Constant.USER_SEX, "");
    }

    public static String getSchoolName(Context context){
        return (String) SharedPreferencesUtils.getData(context, Constant.USER_DETAIL, Constant.USER_SCHOOL_NAME, "");
    }

    public static String getPhone(Context context){
        return (String) SharedPreferencesUtils.getData(context, Constant.USER_DETAIL, Constant.USER_PHONE, "");
    }

    public static String getHeadUrl(Context context){
        return (String) SharedPreferencesUtils.getData(context, Constant.USER_DETAIL, Constant.USER_HEAD_URL, "");
    }

    public static Integer getShoolId(Context context){
        return (Integer) SharedPreferencesUtils.getData(context, Constant.USER_DETAIL, Constant.USER_SCHOOL_ID, -1);
    }

}
