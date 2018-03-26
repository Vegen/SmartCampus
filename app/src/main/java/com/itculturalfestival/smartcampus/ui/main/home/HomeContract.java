package com.itculturalfestival.smartcampus.ui.main.home;

import com.itculturalfestival.smartcampus.entity.News;
import com.itculturalfestival.smartcampus.entity.NewsList;
import com.itculturalfestival.smartcampus.entity.db.Banner;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.mvp.view.BaseView;

import java.util.List;

/**
 * Created by vegen on 2018/3/6.
 */

public class HomeContract {
    interface View extends BaseView {
        void showNewsList(List<NewsList> newsListList);
        void moreNewsClassId(List<String> strings);
        void showBanner(List<Banner> bannerList);
    }

    interface Presenter extends BasePresenter {
        void getNewsList(String url);
        void getBanner();
    }
}
