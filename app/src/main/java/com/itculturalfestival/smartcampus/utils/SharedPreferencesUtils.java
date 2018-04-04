package com.itculturalfestival.smartcampus.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by vegen on 2017/9/1.
 */

public class SharedPreferencesUtils {

    //存储的sharedpreferences文件名
    private static final String FILE_NAME = "SmartCampusTemp";

    public static void clearAllData(Context context){
        clearAllData(context, FILE_NAME);
    }

    public static void clearAllData(Context context, String fileName){
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();
    }

    /**
     * 保存数据到文件
     *
     * @param context
     * @param key
     * @param data
     */
    public static void saveData(Context context, String key, Object data) {
        saveData(context, FILE_NAME, key, data);
    }

    /**
     * 保存数据到文件
     *
     * @param context
     * @param key
     * @param data
     */
    public static void saveData(Context context, String fileName, String key, Object data) {

        String type = data.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) data);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) data);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) data);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) data);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) data);
        }

        editor.commit();
    }

    public static void saveObject(Context context, String key, Object object) {
        saveObject(context, FILE_NAME, key, object);
    }

    public static void saveObject(Context context, String fileName, String key, Object object) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(object);
        editor.putString(key, json);
        editor.commit();
    }

    public static <T> T getObject(Context context, String key, Class<T> t) {
        return getObject(context, FILE_NAME, key, t);
    }

    public static <T> T getObject(Context context, String fileName, String key, Class<T> t) {
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (fileName, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(key, "");

        Gson gson = new Gson();
        T object = gson.fromJson(json, t);
        return object;
    }

    /**
     * 从文件中读取数据
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Object getData(Context context, String key, Object defValue) {
        return getData(context, FILE_NAME, key, defValue);
    }

    /**
     * 从文件中读取数据
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Object getData(Context context, String fileName, String key, Object defValue) {

        String type = defValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (fileName, Context.MODE_PRIVATE);

        //defValue为为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return sharedPreferences.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return sharedPreferences.getLong(key, (Long) defValue);
        }

        return null;
    }
}
