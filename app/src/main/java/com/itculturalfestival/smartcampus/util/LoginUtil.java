package com.itculturalfestival.smartcampus.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * @creation_time: 2017/4/9
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class LoginUtil {


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

    public static int getUserId(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getInt("id", -1);
    }

    public static String getName(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getString("name", "");
    }

    public static String getSex(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getString("sex", "");
    }

    public static String getSchool(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getString("school", "");
    }

    public static String getFaculty(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getString("faculty", "");
    }

    public static String getMajor(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getString("major", "");
    }

    public static String getEducation(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getString("education", "");
    }
    public static String getEMail(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getString("e_mail", "");
    }
    public static String getTel(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getString("tel", "");
    }
    public static String getEnrollmentYear(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getString("enrollment_Year", "");
    }
    public static String getLoginTime(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getString("login_time", "1700-01-01");
    }
}
