package com.itculturalfestival.smartcampus.ui.activity.start;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.UserDB;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
import com.itculturalfestival.smartcampus.ui.view.CleanEditText;
import com.itculturalfestival.smartcampus.util.L;
import com.itculturalfestival.smartcampus.util.RegexUtil;
import com.itculturalfestival.smartcampus.util.T;
import com.itculturalfestival.smartcampus.util.VerifyCodeManager;

import java.util.List;

/**
 * @creation_time: 2017/4/11
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 注册界面,一般会使用手机登录，通过获取手机验证码，跟服务器交互完成注册
 */

public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SignupActivity";
    // 界面控件
    private CleanEditText phoneEdit;
    private CleanEditText passwordEdit;
    private CleanEditText verifyCodeEdit;
    private Button getVerifiCodeButton;
    private CleanEditText et_nickname;
    private VerifyCodeManager codeManager;
    private List<UserDB> userList;
    private LinearLayout ll_school;
    private TextView tv_school;
    // 弹出框
    private ProgressDialog mDialog;

    @Override
    protected void onCreate() {
        setContentView(R.layout.activity_signup);
        initViews();
        codeManager = new VerifyCodeManager(this, phoneEdit, getVerifiCodeButton);
    }

    private void initViews() {
        et_nickname = getView(R.id.et_nickname);
        ll_school = getView(R.id.ll_school);
        tv_school = getView(R.id.tv_school);
        getVerifiCodeButton = getView(R.id.btn_send_verifi_code);
        getVerifiCodeButton.setOnClickListener(this);
        phoneEdit = getView(R.id.et_phone);
        phoneEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);// 下一步
        verifyCodeEdit = getView(R.id.et_verifiCode);
        verifyCodeEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);// 下一步
        passwordEdit = getView(R.id.et_password);
        passwordEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        passwordEdit.setImeOptions(EditorInfo.IME_ACTION_GO);
        passwordEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // 点击虚拟键盘的done
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO) {
                    commit();
                }
                return false;
            }
        });

        tv_school.setText("岭南师范学院");
        ll_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("暂只开放岭南师范学院");
            }
        });
    }

    private void commit() {
        final String phone = phoneEdit.getText().toString().trim();
        final String password = passwordEdit.getText().toString().trim();
        String code = verifyCodeEdit.getText().toString().trim();

        if (checkInput(phone, password, code)) {

            // 注册是耗时过程，所以要显示一个dialog来提示下用户
            mDialog = new ProgressDialog(this);
            mDialog.setMessage("注册中，请稍后...");
            mDialog.show();
            
            // TODO:请求服务端注册账号
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().createAccount(phone, password);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!SignUpActivity.this.isFinishing()) {
                                    mDialog.dismiss();
                                }
                                showToast("注册成功");
                                UserDB user = new UserDB("", et_nickname.getText().toString(),"" , tv_school.getText().toString(), "", "", "",  "", phone, password, "");
                                user.saveThrows();
                                finish();
                            }
                        });
                    } catch (final HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!SignUpActivity.this.isFinishing()) {
                                    mDialog.dismiss();
                                }
                                /**
                                 * 关于错误码可以参考官方api详细说明
                                 * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                                 */
                                int errorCode = e.getErrorCode();
                                String message = e.getMessage();
                                Log.d("SignUp", String.format("sign up - errorCode:%d, errorMsg:%s", errorCode, e.getMessage()));
                                switch (errorCode) {
                                    // 网络错误
                                    case EMError.NETWORK_ERROR:
                                        showToast("网络错误 code: " + errorCode + ", message:" + message);
                                        break;
                                    // 用户已存在
                                    case EMError.USER_ALREADY_EXIST:
                                        showToast("用户已存在 code: " + errorCode + ", message:" + message);
                                        break;
                                    // 参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册
                                    case EMError.USER_ILLEGAL_ARGUMENT:
                                        showToast("参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册 code: " + errorCode + ", message:" + message);
                                        break;
                                    // 服务器未知错误
                                    case EMError.SERVER_UNKNOWN_ERROR:
                                        showToast("服务器未知错误 code: " + errorCode + ", message:" + message);
                                        break;
                                    case EMError.USER_REG_FAILED:
                                        showToast("账户注册失败 code: " + errorCode + ", message:" + message);
                                        break;
                                    default:
                                        showToast("ml_sign_up_failed code: " + errorCode + ", message:" + message);
                                        break;
                                }
                            }
                        });

                    }catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!SignUpActivity.this.isFinishing()) {
                                    mDialog.dismiss();
                                }
                                showToast("注册失败");
                            }
                        });
                    }
                }
            }).start();
        }
    }

    private boolean checkInput(String phone, String password, String code) {
//        boolean isDBExist = !DataSupport.limit(1).find(UserDB.class).isEmpty();
//        if (isDBExist) {
//            userList = DataSupport.where("tel = ? ", phone).find(UserDB.class);
//            if (!userList.isEmpty()){
//                showToast("该手机号已注册");
//                return false;
//            }
//        }


        if (TextUtils.isEmpty(phone)) { // 电话号码为空
            T.showShort(this, R.string.tip_phone_can_not_be_empty);
        } else {
            if (!RegexUtil.checkMobile(phone)) { // 电话号码格式有误
                T.showShort(this, R.string.tip_phone_regex_not_right);
            } else if (TextUtils.isEmpty(code)) { // 验证码不正确
                T.showShort(this, R.string.tip_please_input_code);
            } else if (password.length() < 6 || password.length() > 32
                    || TextUtils.isEmpty(password)) { // 密码格式
                T.showShort(this,
                        R.string.tip_please_input_6_32_password);
            } else if (!verifyCodeEdit.getText().toString().equals("1234")){
                showToast("验证码不正确");
            }else if(TextUtils.isEmpty(et_nickname.getText().toString())){
                showToast("昵称不能为空");
            } else if(TextUtils.isEmpty(tv_school.getText().toString())){
                showToast("请选择学校");
            }else {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_verifi_code:
                // TODO 请求接口发送验证码

                if (codeManager.getVerifyCode(VerifyCodeManager.REGISTER)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast("收到验证码：1234");
                                    verifyCodeEdit.setText("1234");
                                }
                            });
                        }
                    }).start();
                }

                break;
            case R.id.btn_create_account:
                commit();
                break;
            default:
                break;
        }
    }

}
