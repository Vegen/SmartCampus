package com.itculturalfestival.smartcampus.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.itculturalfestival.smartcampus.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @creation_time: 2017/4/11
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class VerifyCodeManager {
    public final static int REGISTER = 1;
    public final static int RESET_PWD = 2;
    public final static int BIND_PHONE = 3;

    private Context mContext;
    private int recLen = 60;
    private Timer timer = new Timer();
    private Handler mHandler = new Handler();
    private String phone;

    private EditText phoneEdit;
    private Button getVerifiCodeButton;

    public VerifyCodeManager(Context context, EditText editText, Button btn) {
        this.mContext = context;
        this.phoneEdit = editText;
        this.getVerifiCodeButton = btn;
    }

    public boolean getVerifyCode(int type) {
        // 获取验证码之前先判断手机号

        phone = phoneEdit.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            T.showShort(mContext, R.string.tip_please_input_phone);
        } else if (phone.length() < 11) {
            T.showShort(mContext, R.string.tip_phone_regex_not_right);
        } else if (!RegexUtil.checkMobile(phone)) {
            T.showShort(mContext, R.string.tip_phone_regex_not_right);
        }else {
            // 两种方式：1.集成第三方SDK，调用skd的方法获取验证码
            // SMSSDK.getVerificationCode("86", phone);


            // 2. 请求服务端，由服务端为客户端发送验证码
//		HttpRequestHelper.getInstance().getVerifyCode(mContext, phone, type,
//				getVerifyCodeHandler);

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            setButtonStatusOff();
                            if (recLen < 1) {
                                setButtonStatusOn();
                            }
                        }
                    });
                }
            };

            timer = new Timer();
            timer.schedule(task, 0, 1000);
            return true;
        }
        return false;
    }

    private void setButtonStatusOff() {
        getVerifiCodeButton.setText(String.format(
                mContext.getResources().getString(R.string.count_down), recLen--));
        getVerifiCodeButton.setClickable(false);
        getVerifiCodeButton.setTextColor(Color.parseColor("#f3f4f8"));
        getVerifiCodeButton.setBackgroundColor(Color.parseColor("#b1b1b3"));
    }

    private void setButtonStatusOn() {
        timer.cancel();
        getVerifiCodeButton.setText("重新发送");
        getVerifiCodeButton.setTextColor(Color.parseColor("#b1b1b3"));
        getVerifiCodeButton.setBackgroundColor(Color.parseColor("#f3f4f8"));
        recLen = 60;
        getVerifiCodeButton.setClickable(true);
    }

//	private AsyncHttpResponseHandler getVerifyCodeHandler = new AsyncHttpResponseHandler() {
//
//		@Override
//		public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//			try {
//				if (arg2 != null) {
//					String respone = new String(arg2);
//					LogUtils.e("verifyCode", respone);
//					// {"status":"false","code":15,"message":"\u8be5\u7535\u8bdd\u53f7\u7801\u5df2\u88ab\u5360\u7528\uff0c\u8bf7\u91cd\u65b0\u9009\u62e9"}
//					JSONObject jsonObject = new JSONObject(respone);
//					jsonObject.optBoolean("status");
//					int code = jsonObject.optInt("code");
//					switch (code) {
//					case 15: // 账号已存在
//						ToastUtils.showShort(mContext,
//								R.string.tip_phone_exist_please_login);
//						setButtonStatusOn();
//						break;
//					case 0:
//						ToastUtils.showShort(mContext, "验证码发送成功");
//						break;
//					case 6:
//						ToastUtils.showShort(mContext, "手机号不存在，请注册");
//						setButtonStatusOn();
//						break;
//					case 2003: // 账号不存在
//						ToastUtils.showShort(mContext,
//								R.string.tip_phone_not_exist);
//						setButtonStatusOn();
//						break;
//					case 2015: // 手机账号已经绑定
//						ToastUtils.showShort(mContext, "手机号已被绑定，请直接使用该手机号登录");
//						break;
//					case 1001: // 客户端验证错误
//						ToastUtils.showShort(mContext, "客户端验证失败");
//						break;
//					case 1005: // 验证码错误
//						ToastUtils.showShort(mContext, "验证码不正确");
//						break;
//					case 2007: // 用户保存失败
//
//						break;
//
//					default:
//						ToastUtils.showShort(mContext, "发送验证码失败，请重试");
//						setButtonStatusOn();
//						break;
//					}
//
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
//
//		@Override
//		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//				Throwable arg3) {
//			if (arg2 != null) {
//				ToastUtils.showShort(mContext, "发送验证码失败，请重试");
//				setButtonStatusOn();
//			}
//		}
//	};

}
