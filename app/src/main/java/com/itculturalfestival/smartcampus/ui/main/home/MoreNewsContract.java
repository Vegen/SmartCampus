package com.itculturalfestival.smartcampus.ui.main.home;

import com.itculturalfestival.smartcampus.entity.News;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.mvp.view.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by vegen on 2018/3/21.
 */

public class MoreNewsContract {
    interface View extends BaseView {
        void showNewsList(List<News> newsList);
        void nextNewsListForm(Map<String, String> form);
    }

    interface Presenter extends BasePresenter {
        void getNewsList(String url, int newsType);                       // 初始化时用
        void getNewsList(int page, String url, int newsType, String view_state, String view_state_generator, String event_validation);   // 加载更多时候用
    }
}
