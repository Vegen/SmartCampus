package com.itculturalfestival.smartcampus;

import android.support.multidex.MultiDexApplication;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.util.NetUtils;
import com.itculturalfestival.smartcampus.ui.main.MainActivity;
import com.itculturalfestival.smartcampus.utils.EaseHelper;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import org.litepal.LitePal;

import cn.bmob.v3.Bmob;

import static com.itculturalfestival.smartcampus.Constant.BmobApplicationId;


/**
 * @creation_time: 2017/4/17
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class MyApplication extends MultiDexApplication {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtils.isDebug = BuildConfig.DEBUG;
        // 数据库
        LitePal.initialize(this);
        // 后端云
        Bmob.initialize(this, BmobApplicationId);
        // 友盟相关配置
        initUmeng();
        // 环信配置
        EaseHelper.getInstance().init(this.getApplicationContext());
    }

    private void initUmeng(){
        UMShareAPI.get(this);
        /**
         * 友盟
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        Config.DEBUG = BuildConfig.DEBUG;
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("1106797634", "i60Y8lkTlTvotzYh");
        PlatformConfig.setSinaWeibo("204838108", "12a4d1e6fbae4bf6667d67d8a7d30f5f", "http://sns.whalecloud.com");
    }

}
