package com.itculturalfestival.smartcampus.ui.main.home;

import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.mvp.view.BaseView;

/**
 * Created by vegen on 2018/3/22.
 */

public class ArticleDetailContract {

    interface View extends BaseView {
        void showNewsContent(String newsContent);
    }

    interface Presenter extends BasePresenter {
        void getNewsContent(String url);
    }

}
