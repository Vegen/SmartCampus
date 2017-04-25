package com.itculturalfestival.smartcampus.ui.activity.start;

import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.activity.information.MainActivity;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
import com.itculturalfestival.smartcampus.util.AppConstants;
import com.itculturalfestival.smartcampus.util.DateUtil;
import com.itculturalfestival.smartcampus.util.LoginUtil;
import com.itculturalfestival.smartcampus.util.SPUtil;

/**
 * @creation_time: 2017/4/3
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 启动页
 */

public class SplashActivity extends BaseActivity {

    private static final int SHOW_TIME_MIN = 2000;// 最小显示时间
    private long mStartTime;// 开始时间
    private boolean IsFirst;//第一次进入应用
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    long loadingTime = System.currentTimeMillis() - mStartTime;// 计算一下总共花费的时间
                    if (loadingTime < SHOW_TIME_MIN) {
                        // 如果比最小显示时间还短，就延时进入MainActivity，否则直接进入
                        mHandler.postDelayed(goToMainActivity, SHOW_TIME_MIN
                                - loadingTime);
                    } else {
                        mHandler.post(goToMainActivity);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    //进入下一个Activity
    Runnable goToMainActivity = new Runnable() {

        @Override
        public void run() {
            IsFirst = (boolean) SPUtil.get(getContext(), AppConstants.FIRST_COME, true);
            if (IsFirst) {
                startActivity(new Intent(SplashActivity.this,
                        GuideActivity.class));
                SPUtil.put(getContext(), AppConstants.FIRST_COME, false);
                finish();
            } else {
                if (DateUtil.daysBetween(LoginUtil.getLoginTime(getContext()), DateUtil.textForNow2()) > 7) {
                    startActivity(new Intent(SplashActivity.this,
                            LoginActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this,
                            MainActivity.class));
                }
                finish();
            }

        }
    };

    @Override
    protected void onCreate() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spalash);

        mStartTime = System.currentTimeMillis();//记录开始时间1

        mHandler.sendEmptyMessage(1);
    }

    private void saveTag() {
        SPUtil.put(getContext(), AppConstants.FIRST_COME,false);
    }

    @Override
    public void finish() {
        super.finish();
        saveTag();
    }
}
