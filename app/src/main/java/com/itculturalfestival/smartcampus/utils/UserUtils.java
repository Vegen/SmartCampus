package com.itculturalfestival.smartcampus.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.itculturalfestival.smartcampus.MyApplication;

/**
 * Created by vegen on 2018/3/2.
 * 用户个人信息统一管理类
 */

public class UserUtils {

    private String verifyToken;

    private static UserUtils currentUser;

    private String token;

    private String userId;

    private JsonObject data;

    public static final String UMENG_PUSH_ALIAS = "umeng_alias";

    public UserUtils(JsonObject data) {
        this.data = data;
    }

    public static UserUtils getCurrentUser() {
        if (currentUser == null) {
            synchronized (UserUtils.class) {
                JsonElement data = new Gson().fromJson(
                        getDefaultPreferences().getString("userData", ""), JsonElement.class);
                if (data == null || data.isJsonNull()) {
                    return null;
                }
                return new UserUtils(data.getAsJsonObject());
            }
        }
        return currentUser;
    }

//    public static void changeCurrentUser(UserResult.Data data) {
//        currentUser = null;
//        SharedPreferences.Editor editor = getDefaultPreferences().edit()
//                .putString("analysisTag", BuildConfig.analysisTag)
//                .putString("userData", new Gson().toJson(data))
//                .putString("cookies", data == null ? "" : AexUtils.encrypt(BuildConfig.analysisTag, data.cookies.toString()));
//        if (!TextUtils.isEmpty(data.key)) {
//            editor.putString("token", data.key);
//        }
//        editor.apply();
//    }

//    public static void changeCurrentUser(UserResult.Data data, String phone) {
//        currentUser = null;
//        getDefaultPreferences().edit()
//                .putString("analysisTag", BuildConfig.analysisTag)
//                .putString("userData", new Gson().toJson(data))
//                .putString("phone", phone)
//                .putString("token", data == null ? "" : data.key)
//                .putString("cookies", data == null ? "" : AexUtils.encrypt(BuildConfig.analysisTag, data.cookies.toString()))
//                .commit();
////                .apply();
//    }

    public static void logOut() {
//        changeCurrentUser(null, "");
    }

    private static SharedPreferences getDefaultPreferences() {
        MyApplication context = MyApplication.getInstance();
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static void verifyCode(String code) {

    }

    public String getToken() {
        return getDefaultPreferences().getString("token", "");
    }

//    public static String getCookies() {
//        String cookies = AexUtils.decrypt(BuildConfig.analysisTag, getDefaultPreferences().getString("cookies", ""));
//        return cookies;
//    }

    private String get(String key) {
        if (!data.has(key)) {
            return "";
        }
        return data.get(key).getAsString();
    }

    public String getPhoneNum() {
        return get("phone_num");
    }

    public String getUserId() {
        return get("userid");
    }

    public int getWalletAmount() {
        if (!data.has("wallet_amount")) {
            return 0;
        }
        return data.get("wallet_amount").getAsInt();
    }

    public String getHidePhoneNum() {
        String phone = getPhoneNum();
        if (TextUtils.isEmpty(phone)) {
            return "";
        }

        return phone.substring(0, 3) +
                "****" +
                phone.substring(7);
    }

    public void saveNickname(String nickname) {
        getDefaultPreferences().edit().putString("nickname", nickname).apply();
    }

    public String getNickname() {
        return get("username");
    }

    public static String getUserToken() {
        if (getCurrentUser() == null) {
            return null;
        }
        return getCurrentUser().getToken();
    }

}
