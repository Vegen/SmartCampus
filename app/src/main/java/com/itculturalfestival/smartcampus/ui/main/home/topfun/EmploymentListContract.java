package com.itculturalfestival.smartcampus.ui.main.home.topfun;

import com.itculturalfestival.smartcampus.entity.Employment;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.mvp.view.BaseView;

import java.util.List;

/**
 * Created by Huguang on 2018/3/25.
 */

public class EmploymentListContract {
    public interface View extends BaseView {
        void showEmploymentList(List<Employment> employments);
    }

    public interface Presenter extends BasePresenter {
        void getEmploymentList(String url, int type);
    }
}
