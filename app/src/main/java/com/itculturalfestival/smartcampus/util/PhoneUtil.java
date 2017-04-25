package com.itculturalfestival.smartcampus.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.itculturalfestival.smartcampus.bean.ContactBean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @creation_time: 2017/3/27
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 封装一些手机相关的功能
 */

public class PhoneUtil {

    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER
    };
    private static final int PHONES_NUMBER_INDEX = 1;          //电话号码
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;    //联系人名称

    /**
     * 打电话
     * @param context
     * @param tel 电话号码
     * @param isDial 是否跳到拨号界面
     */
    public static void call(Context context, String tel, boolean isDial){
        Intent intent;
        if (isDial){    //直接拨打电话
            intent = new Intent(Intent.ACTION_DIAL);
        }else {         //跳到拨号界面
            intent = new Intent(Intent.ACTION_CALL);
        }
        intent.setData(Uri.parse("tel:" + tel));
        try{
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 发短信
     * @param context
     * @param tel 电话号码
     */
    public static void sendMessage(Context context, String tel){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + tel));
        try{
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 得到手机通讯录联系人信息
     */
    public static List<ContactBean> getPhoneContacts(Context context) {
        if (null == context) return null;
        ContentResolver resolver = context.getContentResolver();
        List<ContactBean> list = new ArrayList<>();
        Cursor phoneCursor = null;
        // 获取手机联系人
        try {
            phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber)) continue;
                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX).trim();
                //联系人名称为空时跳过
                if (TextUtils.isEmpty(contactName)) continue;

                list.add(new ContactBean(contactName, phoneNumber));
            }
            phoneCursor.close();
        }
        return list;
    }

    /**
     * 得到手机SIM卡联系人人信息
     */
    public static List<ContactBean> getSIMContacts(Context context) {
        if (null == context) return null;
        ContentResolver resolver = context.getContentResolver();
        List<ContactBean> list = new ArrayList<>();

        // 获取Sims卡联系人
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = null;
        try {
            phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX).trim();
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))  continue;
                // 得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX).trim();
                //联系人名称为空时跳过
                if (TextUtils.isEmpty(contactName)) continue;

                list.add(new ContactBean(contactName, phoneNumber));
            }
            phoneCursor.close();
        }

        return list;
    }

    /**
     * 获取手机和sim中的联系人
     */
    public static List<ContactBean> getAllContacts(Context context){
        if (null == context) return null;
        List<ContactBean> list = getPhoneContacts(context);
        List<ContactBean> list1= getSIMContacts(context);
        for (ContactBean bean : list1){
            if (!list.contains(bean)){  //去重
                list.add(bean);
            }
        }
        return list;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context){
        if (null == context) return 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context){
        if (null == context) return 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取虚拟功能键高度
     */
    public static int getVirtualBarHeight(Context context) {
        if (null == context) return 0;
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    /**
     * 打开软键盘
     * @param view 接受输入的view
     */
    public static void showSoftInput(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏软键盘
     * @param view 接受输入的view
     */
    public static void hideSoftInput(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 检测手机数据是否可用
     */
    public static boolean isMobileNetConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        return cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
    }

    /**
     * 检测WIFI是否开启可用
     */
    public static boolean isWifiNetConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        return cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
    }

    /**
     * 检测网络是否可用
     */
    public static boolean isNetConnected(Context context){
        return isMobileNetConnected(context) || isWifiNetConnected(context);
    }
}
