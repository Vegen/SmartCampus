package com.itculturalfestival.smartcampus.ui.activity.mine;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.activity.start.LoginActivity;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
import com.itculturalfestival.smartcampus.util.ActivityCollectorUtil;

/**
 * @creation_time: 2017/4/19
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 设置
 */

public class SettingActivity extends BaseActivity {

    private TextView tv_signout;

    @Override
    protected void onCreate() {
        initLayout(R.layout.activity_setting);
        tv_signout = getView(R.id.tv_signout);
        tv_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signout();
            }
        });
    }

    /**
     * 退出登录
     */
    private void signout(){
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(getContext(), LoginActivity.class));
                ActivityCollectorUtil.finishAllActivity();
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
