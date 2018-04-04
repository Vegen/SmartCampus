package com.itculturalfestival.smartcampus.ui.start;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMError;
import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.db.SmartUser;
import com.itculturalfestival.smartcampus.ui.custom.CleanEditText;
import com.itculturalfestival.smartcampus.utils.RegexUtil;
import com.itculturalfestival.smartcampus.utils.UserHelper;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import butterknife.Bind;

/**
 * @creation_time: 2017/4/11
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 登录
 */

public class LoginActivity extends AppBaseActivity<LoginContract.Presenter> implements LoginContract.View {

    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.et_email_phone)
    CleanEditText etEmailPhone;
    @Bind(R.id.et_password)
    CleanEditText etPassword;
    @Bind(R.id.input_layout)
    LinearLayout inputLayout;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_third_login_explain)
    TextView tvThirdLoginExplain;
    @Bind(R.id.third_login_explain_layout)
    RelativeLayout thirdLoginExplainLayout;
    @Bind(R.id.iv_wechat)
    ImageView ivWechat;
    @Bind(R.id.iv_qq)
    ImageView ivQq;
    @Bind(R.id.iv_sina)
    ImageView ivSina;
    @Bind(R.id.third_login_layout)
    RelativeLayout thirdLoginLayout;
    @Bind(R.id.tv_create_account)
    TextView tvCreateAccount;
    @Bind(R.id.seprate)
    View seprate;
    @Bind(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @Bind(R.id.bottom_layout)
    RelativeLayout bottomLayout;
    @Bind(R.id.layout_root)
    RelativeLayout layoutRoot;

    public static void start(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected LoginContract.Presenter presenter() {
        if (mPresenter == null)
            mPresenter = new LoginPresenter(this);
        return mPresenter;
    }

    @Override
    protected int layoutId() {
        return R.layout.app_activity_login;
    }

    @Override
    protected void setupUI() {
        initView();
    }

    @Override
    protected void initData() {

    }

    private void initView() {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        etEmailPhone.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etEmailPhone.setTransformationMethod(HideReturnsTransformationMethod
                .getInstance());

        etPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etPassword.setImeOptions(EditorInfo.IME_ACTION_GO);
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_GO) {
                clickLogin();
            }
            return false;
        });

        btnLogin.setOnClickListener(view -> clickLogin());
        tvCreateAccount.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), SignUpActivity.class));
        });
    }

    private void clickLogin() {
        final String phone = etEmailPhone.getText().toString();
        String password = etPassword.getText().toString();
        if (checkInput(phone, password)) {
            showProgressDialog("正在登陆，请稍后...");
            presenter().login(phone, password);
        }
    }

    /**
     * 检查输入
     * @param phone
     * @param password
     * @return
     */
    public boolean checkInput(String phone, String password) {
        // 账号为空时提示
        if (TextUtils.isEmpty(phone)) {
            showToast(R.string.tip_account_empty);
        } else {
            // 账号不匹配手机号格式（11位数字且以1开头）
            if (!RegexUtil.checkMobile(phone)) {
                showToast(R.string.tip_account_regex_not_right);
            } else if (TextUtils.isEmpty(password)) {
                showToast(R.string.tip_password_can_not_be_empty);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void loginSuccess(SmartUser smartUser) {
        UserHelper.saveUserDetail(getContext(), smartUser);
    }

    @Override
    public void loginError(int i, String s) {
        runOnUiThread(() -> {
            dismissProgressDialog();
            LogUtils.e("Login", "登录失败 Error code:" + i + ", message:" + s);
            /**
             * 关于错误码可以参考官方api详细说明
             * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
             */
            switch (i) {
                // 网络异常 2
                case EMError.NETWORK_ERROR:
                    showToast("网络错误 code: " + i + ", message:" + s);
                    break;
                // 无效的用户名 101
                case EMError.INVALID_USER_NAME:
                    showToast("无效的用户名 code: " + i + ", message:" + s);
                    break;
                // 无效的密码 102
                case EMError.INVALID_PASSWORD:
                    showToast("无效的密码 code: " + i + ", message:" + s);
                    break;
                // 用户认证失败，用户名或密码错误 202
                case EMError.USER_AUTHENTICATION_FAILED:
                    showToast("用户认证失败，用户名或密码错误 code: " + i + ", message:" + s);
                    break;
                // 用户不存在 204
                case EMError.USER_NOT_FOUND:
                    showToast("用户不存在 code: " + i + ", message:" + s);
                    break;
                // 无法访问到服务器 300
                case EMError.SERVER_NOT_REACHABLE:
                    showToast("无法访问到服务器 code: " + i + ", message:" + s);
                    break;
                // 等待服务器响应超时 301
                case EMError.SERVER_TIMEOUT:
                    showToast("等待服务器响应超时 code: " + i + ", message:" + s);
                    break;
                // 服务器繁忙 302
                case EMError.SERVER_BUSY:
                    showToast("服务器繁忙 code: " + i + ", message:" + s);
                    break;
                // 未知 Server 异常 303 一般断网会出现这个错误
                case EMError.SERVER_UNKNOWN_ERROR:
                    showToast("未知的服务器异常 code: " + i + ", message:" + s);
                    break;
                default:
                    showToast("ml_sign_in_failed code: " + i + ", message:" + s);
                    break;
            }
        });
    }
}
