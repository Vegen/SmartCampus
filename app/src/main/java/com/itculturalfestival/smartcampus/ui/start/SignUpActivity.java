package com.itculturalfestival.smartcampus.ui.start;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMError;
import com.hyphenate.exceptions.HyphenateException;
import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.db.SmartUser;
import com.itculturalfestival.smartcampus.ui.custom.CleanEditText;
import com.itculturalfestival.smartcampus.utils.RegexUtil;
import com.itculturalfestival.smartcampus.utils.VerifyCodeManager;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import butterknife.Bind;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @creation_time: 2017/4/11
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 注册界面, 一般会使用手机登录，通过获取手机验证码，跟服务器交互完成注册
 */

public class SignUpActivity extends AppBaseActivity<SignUpContract.Presenter> implements SignUpContract.View, View.OnClickListener {
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.et_phone)
    CleanEditText etPhone;
    @Bind(R.id.btn_send_verify_code)
    Button btnSendVerifyCode;
    @Bind(R.id.layout_phone)
    RelativeLayout layoutPhone;
    @Bind(R.id.et_password)
    CleanEditText etPassword;
    @Bind(R.id.et_verifiCode)
    CleanEditText etVerifiCode;
    @Bind(R.id.et_nickname)
    CleanEditText etNickname;
    @Bind(R.id.tv_school)
    TextView tvSchool;
    @Bind(R.id.ll_school)
    LinearLayout llSchool;
    @Bind(R.id.line)
    View line;
    @Bind(R.id.btn_create_account)
    Button btnCreateAccount;
    @Bind(R.id.tv_user_rule)
    TextView tvUserRule;
    @Bind(R.id.layout_root)
    RelativeLayout layoutRoot;

    private VerifyCodeManager codeManager;

    @Override
    protected SignUpContract.Presenter presenter() {
        if (mPresenter == null)
            mPresenter = new SignUpPresenter(this);
        return mPresenter;
    }

    @Override
    protected int layoutId() {
        return R.layout.app_activity_signup;
    }

    @Override
    protected void setupUI() {
        initViews();
        codeManager = new VerifyCodeManager(this, etPhone, btnSendVerifyCode);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void signUpSuccess() {
        presenter().signUpServer(etPhone.getText().toString().trim(),
                etPassword.getText().toString().trim(),
                "女",
                tvSchool.getText().toString().trim(),
                1
        );
    }

    @Override
    public void hideLoading(boolean isFail) {
//        super.hideLoading(isFail);
        runOnUiThread(() -> {
            dismissProgressDialog();
            if (!isFail) {
                showToast("注册成功");
                finish();
            }
        });
    }

    @Override
    public void showMessage(String message) {
//        super.showMessage(message);
        runOnUiThread(() -> showMessage(message));
    }

    @Override
    public void signUpError(HyphenateException e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!SignUpActivity.this.isFinishing()) {
                    dismissProgressDialog();
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
    }

    private void initViews() {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        etPhone.setImeOptions(EditorInfo.IME_ACTION_NEXT);// 下一步
        etVerifiCode.setImeOptions(EditorInfo.IME_ACTION_NEXT);// 下一步
        etPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etPassword.setImeOptions(EditorInfo.IME_ACTION_GO);
        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            // 点击虚拟键盘的done
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_GO) {
                commit();
            }
            return false;
        });

        tvSchool.setText("岭南师范学院");
        tvSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("暂只开放岭南师范学院");
            }
        });

        btnSendVerifyCode.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);
    }

    private void commit() {
        final String phone = etPhone.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        String code = etVerifiCode.getText().toString().trim();
        if (checkInput(phone, password, code)) {
            showProgressDialog("注册中，请稍后...");
            presenter().signUp(phone, password);
        }
    }

    private boolean checkInput(String phone, String password, String code) {
        if (TextUtils.isEmpty(phone)) { // 电话号码为空
            showToast(R.string.tip_phone_can_not_be_empty);
        } else {
            if (!RegexUtil.checkMobile(phone)) { // 电话号码格式有误
                showToast(R.string.tip_phone_regex_not_right);
            } else if (TextUtils.isEmpty(code)) { // 验证码不正确
                showToast(R.string.tip_please_input_code);
            } else if (password.length() < 6 || password.length() > 32
                    || TextUtils.isEmpty(password)) { // 密码格式
                showToast(R.string.tip_please_input_6_32_password);
            } else if (!etVerifiCode.getText().toString().equals("1234")) {
                showToast("验证码不正确");
            } else if (TextUtils.isEmpty(etPhone.getText().toString())) {
                showToast("昵称不能为空");
            } else if (TextUtils.isEmpty(tvSchool.getText().toString())) {
                showToast("请选择学校");
            } else {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_verify_code:
                // TODO 请求接口发送验证码

                if (codeManager.getVerifyCode(VerifyCodeManager.REGISTER)) {
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
                                    etVerifiCode.setText("1234");
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
