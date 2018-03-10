package com.itculturalfestival.smartcampus.ui.main;

import com.itculturalfestival.smartcampus.entity.News;
import com.itculturalfestival.smartcampus.entity.NewsList;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.mvp.view.BaseView;

import java.util.List;

/**
 * Created by vegen on 2018/3/6.
 */

public class MainContract {
    interface View extends BaseView {
        void showNewsList(List<NewsList> newsListList);
    }

    interface Presenter extends BasePresenter {
        void getNewsList(String url);
    }
}
