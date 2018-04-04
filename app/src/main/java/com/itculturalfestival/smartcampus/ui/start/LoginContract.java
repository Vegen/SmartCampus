package com.itculturalfestival.smartcampus.ui.start;

import com.itculturalfestival.smartcampus.entity.db.SmartUser;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.mvp.view.BaseView;

/**
 * Created by vegen on 2018/4/4.
 */

public class LoginContract {
    public interface View extends BaseView {
        void loginSuccess(SmartUser smartUser);
        void loginError(final int i, final String s);
    }

    public interface Presenter extends BasePresenter {
        void login(String phone, String password);
    }
}
