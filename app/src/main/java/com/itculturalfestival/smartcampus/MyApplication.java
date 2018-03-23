package com.itculturalfestival.smartcampus;

import android.support.multidex.MultiDexApplication;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.SocializeConstants;
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
        UMShareAPI.get(this);
        LitePal.initialize(this);
//        Bmob.initialize(this, BmobApplicationId);       // todo 修正渠道
        LogUtils.isDebug = BuildConfig.DEBUG;
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
        Config.DEBUG = true;
        PlatformConfig.setWeixin("replace_yours","replace_yours");
        PlatformConfig.setQQZone("replace_yours", "replace_yours");
        PlatformConfig.setSinaWeibo("replace_yours", "replace_yours","http://sns.whalecloud.com");
    }

}
