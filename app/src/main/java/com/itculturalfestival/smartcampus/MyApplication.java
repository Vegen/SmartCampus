package com.itculturalfestival.smartcampus;

import android.support.multidex.MultiDexApplication;

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
        LitePal.initialize(this);
        Bmob.initialize(this, BmobApplicationId);       // todo 修正渠道
        LogUtils.isDebug = BuildConfig.DEBUG;
    }

}
