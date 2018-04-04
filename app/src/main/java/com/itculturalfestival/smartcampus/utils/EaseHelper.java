package com.itculturalfestival.smartcampus.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.NetUtils;
import com.itculturalfestival.smartcampus.BuildConfig;
import com.itculturalfestival.smartcampus.MyApplication;
import com.itculturalfestival.smartcampus.ui.start.LoginActivity;

/**
 * Created by vegen on 2018/3/27.
 */

public class EaseHelper {

    private String TAG = "EaseHelper";
    private static EaseHelper instance = null;
    private EaseUI easeUI;
    private Context appContext;

    public synchronized static EaseHelper getInstance() {
        if (instance == null) {
            instance = new EaseHelper();
        }
        return instance;
    }

    /**
     * init helper
     *
     * @param context
     *            application context
     */
    public void init(Context context) {
        EMOptions options = initChatOptions();

        if (EaseUI.getInstance().init(context, options)) {
            appContext = context;
            EMClient.getInstance().setDebugMode(BuildConfig.DEBUG);
            easeUI = EaseUI.getInstance();
            PreferenceManager.init(context);
        }
    }

    private EMOptions initChatOptions(){
        Log.d(TAG, "init HuanXin Options");
        EMOptions options = new EMOptions();
        // set if accept the invitation automatically
        options.setAcceptInvitationAlways(false);
        // set if you need read ack
        options.setRequireAck(true);
        // set if you need delivery ack
        options.setRequireDeliveryAck(false);
        return options;
    }


    /**
     * if ever logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * logout
     *
     * @param unbindDeviceToken
     *            whether you need unbind your device token
     * @param callback
     *            callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
        Log.d(TAG, "logout: " + unbindDeviceToken);
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
                reset();
                Log.d(TAG, "logout: onSuccess");
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                reset();
                Log.d(TAG, "logout: onError");
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    private void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reset(){
        UserHelper.clearUserDetail(MyApplication.getInstance().getApplicationContext());
    }

    public void addConnectionListener(Activity activity){
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener(activity));
    }

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {

        private Activity activity;

        MyConnectionListener(Activity activity){
            this.activity = activity;
        }

        @Override
        public void onConnected() {
        }
        @Override
        public void onDisconnected(final int error) {
            if (activity == null) return;
            activity.runOnUiThread(() -> {
                if(error == EMError.USER_REMOVED){
                    // 显示帐号已经被移除
                    onUserException(appContext, "帐号已经被移除");
                }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    // 显示帐号在其他设备登录
                    EMClient.getInstance().logout(true);//退出登录
                    onUserException(appContext, "帐号在其他设备登录");
                } else {
                    if (NetUtils.hasNetwork(activity)) {
                        // 连接不到聊天服务器
                        Toast.makeText(appContext,"连接不到聊天服务器",Toast.LENGTH_SHORT).show();
                    } else {
                        // 当前网络不可用，请检查网络设置
                        Toast.makeText(appContext,"当前网络不可用，请检查网络设置",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * user met some exception: conflict, removed or forbidden
     */
    protected void onUserException(Context context, String exception){
        EMLog.e(TAG, "onUserException: " + exception);
        Toast.makeText(context,exception,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(exception, true);
        context.startActivity(intent);
    }


}
