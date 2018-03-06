package com.itculturalfestival.smartcampus;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.vegen.smartcampus.baseframework.BuildConfig;

import org.litepal.LitePal;

import java.util.Iterator;
import java.util.List;


/**
 * @creation_time: 2017/4/17
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class MyApplication extends MultiDexApplication {

    public static Context mContext ;
    // 记录是否已经初始化
    private boolean isInit = false;
    private static MyApplication instance;

    public static final String ApplicationId = "329abfb679b7560b6d3bc80ef9204d2c";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext  = this;
        LitePal.initialize(this);

//        Bmob.initialize(this, ApplicationId);       // todo 修正渠道

        // 初始化环信SDK
//        initEasemob();
        instance = this;
        initHuanXin();
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//        //初始化
//        EaseUI.getInstance().init(mContext , options);
//        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
//        setGlobalListeners();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    private void initHuanXin(){

//        EaseUiHelper.getInstance().init(this.getApplicationContext());
//        //设置全局监听
//        setGlobalListeners();
    }

    /**
     *
     */
    private void initEasemob() {
        // 获取当前进程 id 并取得进程名
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        /**
         * 如果app启用了远程的service，此application:onCreate会被调用2次
         * 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
         * 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
         */
        if (processAppName == null || !processAppName.equalsIgnoreCase(mContext.getPackageName())) {
            // 则此application的onCreate 是被service 调用的，直接返回
            return;
        }
        if (isInit) {
            return;
        }

        // 调用初始化方法初始化sdk
//        EMClient.getInstance().init(mContext, initOptions());

        // 设置开启debug模式
//        EMClient.getInstance().setDebugMode(true);

        // 设置初始化已经完成
        isInit = true;
    }

    /**
     * SDK初始化的一些配置
     * 关于 EMOptions 可以参考官方的 API 文档
     * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1chat_1_1_e_m_options.html
     */
//    private EMOptions initOptions() {
//
//        EMOptions options = new EMOptions();
//        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
//        // options.setAppKey("lzan13#hxsdkdemo");
//        // 设置自动登录
//        options.setAutoLogin(true);
//        // 设置是否需要发送已读回执
//        options.setRequireAck(true);
//        // 设置是否需要发送回执，
//        options.setRequireDeliveryAck(true);
//        // 设置是否需要服务器收到消息确认
////        options.setRequireServerAck(true);
//        // 设置是否根据服务器时间排序，默认是true
//        options.setSortMessageByServerTime(false);
//        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
//        options.setAcceptInvitationAlways(false);
//        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
//        options.setAutoAcceptGroupInvitation(false);
//        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
//        options.setDeleteMessagesAsExitGroup(false);
//        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
//        options.allowChatroomOwnerLeave(true);
//        // 设置google GCM推送id，国内可以不用设置
//        // options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
//        // 设置集成小米推送的appid和appkey
//        // options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);
//
//        return options;
//    }

    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param pid 进程的id
     * @return 返回进程的名字
     */
    private String getAppName(int pid) {
        String processName = null;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    processName = info.processName;
                    // 返回当前进程名
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }



    /**
     * 设置一个全局的监听
     */
    protected void setGlobalListeners(){


//        // create the global connection listener
//        connectionListener = new EMConnectionListener() {
//            @Override
//            public void onDisconnected(int error) {
//                EMLog.d("global listener", "onDisconnect" + error);
//                if (error == EMError.USER_REMOVED) {// 显示帐号已经被移除
//                    onUserException(Constant.ACCOUNT_REMOVED);
//                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {// 显示帐号在其他设备登录
//                    onUserException(Constant.ACCOUNT_CONFLICT);
//                    EMClient.getInstance().logout(true);//退出登录
//                    Toast.makeText(getApplicationContext(),"退出成功",Toast.LENGTH_SHORT).show();
////                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                } else if (error == EMError.SERVER_SERVICE_RESTRICTED) {//
//                    onUserException(Constant.ACCOUNT_FORBIDDEN);
//                }
//            }
//
//            @Override
//            public void onConnected() {
//                // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
//
//            }
//        };
//
//        //register connection listener
//        EMClient.getInstance().addConnectionListener(connectionListener);
    }

    /**
     * user met some exception: conflict, removed or forbidden
     */
    protected void onUserException(String exception){
//        EMLog.e(TAG, "onUserException: " + exception);
        Toast.makeText(getApplicationContext(),exception,Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(getBaseContext(), UserQrCodeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(exception, true);
//        this.startActivity(intent);
    }

}
