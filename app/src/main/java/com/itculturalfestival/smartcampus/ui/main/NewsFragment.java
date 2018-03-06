package com.itculturalfestival.smartcampus.ui.main;

import com.itculturalfestival.smartcampus.AppBaseFragment;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.News;

import java.util.List;

/**
 * Created by vegen on 2018/3/6.
 * 首页-资讯
 */

public class NewsFragment extends AppBaseFragment<MainContract.Presenter> implements MainContract.View {

    @Override
    public void showNewsList(List<News> newsList) {
        showContentView();

    }

    @Override
    protected MainContract.Presenter presenter() {
        if (mPresenter == null) mPresenter = new NewsPresenter(this);
        return mPresenter;
    }

    @Override
    protected int layoutId() {
        return R.layout.app_fragment_main;
    }

    @Override
    protected void setupUI() {

    }

    @Override
    protected void initData() {
        presenter().getNewsList("http://news.lingnan.edu.cn/PhotoZhjnc.aspx");
    }

    @Override
    public void onRefresh() {
        presenter().getNewsList("http://news.lingnan.edu.cn/PhotoZhjnc.aspx");
    }
}
