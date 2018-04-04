package com.itculturalfestival.smartcampus.ui.start;

import android.content.Intent;
import android.os.Handler;

import com.gyf.barlibrary.ImmersionBar;
import com.hyphenate.chat.EMClient;
import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.Constant;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.db.SmartUser;
import com.itculturalfestival.smartcampus.ui.main.MainActivity;
import com.itculturalfestival.smartcampus.utils.EaseHelper;
import com.itculturalfestival.smartcampus.utils.SharedPreferencesUtils;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobUser;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

/**
 * @creation_time: 2017/4/3
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 启动页
 */

public class SplashActivity extends AppBaseActivity {

    private static final int SHOW_TIME_MIN = 2;         // 最小显示时间
    private boolean isFirstCome;                        // 是否第一次进入应用

    @Override
    protected BasePresenter presenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.app_activity_splash;
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected void setupUI() {
        ImmersionBar.with(this).init();
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        isFirstCome = (boolean) SharedPreferencesUtils.getData(getContext(), Constant.FirstCome, true);
        if (isFirstCome) {
            SharedPreferencesUtils.saveData(getContext(), Constant.FirstCome, false);
            nextActivity(GuideActivity.class);
        } else {
            if (EaseHelper.getInstance().isLoggedIn() && BmobUser.getCurrentUser(SmartUser.class) != null) {
                // 加载所有群组和会话
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                nextActivity(MainActivity.class);
            }else {
                nextActivity(LoginActivity.class);
            }
        }
    }

    private void nextActivity(Class<?> cls) {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                LogUtils.e("" + aLong);
            }

            @Override
            public void onError(Throwable e) {
                startActivity(new Intent(SplashActivity.this,
                        cls));
                finish();
            }

            @Override
            public void onComplete() {
                startActivity(new Intent(SplashActivity.this,
                        cls));
                finish();
            }
        });
//        Observable.timer(0, SHOW_TIME_MIN, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                LogUtils.e("" + aLong);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                startActivity(new Intent(SplashActivity.this,
//                        cls));
//                finish();
//            }
//
//            @Override
//            public void onComplete() {
//                startActivity(new Intent(SplashActivity.this,
//                        cls));
//                finish();
//            }
//        });
    }

    @Override
    protected void initData() {

    }
}
