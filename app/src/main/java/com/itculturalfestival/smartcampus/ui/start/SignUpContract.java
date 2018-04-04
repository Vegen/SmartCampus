package com.itculturalfestival.smartcampus.ui.start;

import com.hyphenate.exceptions.HyphenateException;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.mvp.view.BaseView;

/**
 * Created by vegen on 2018/4/4.
 */

public class SignUpContract {
    public interface View extends BaseView {
        void signUpSuccess();
        void signUpError(HyphenateException e);
    }

    public interface Presenter extends BasePresenter {
        void signUp(String phone, String password);
        // 头像 账号 密码 性别 学校名 学校id
        void signUpServer(String phone, String password, String sex, String schoolName, Integer schoolId);
    }
}
