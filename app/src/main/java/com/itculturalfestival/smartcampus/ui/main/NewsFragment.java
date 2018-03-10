package com.itculturalfestival.smartcampus.ui.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.itculturalfestival.smartcampus.AppBaseFragment;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.News;
import com.itculturalfestival.smartcampus.entity.NewsList;

import java.util.List;

import butterknife.Bind;

import static com.itculturalfestival.smartcampus.network.Url.ROOT_URL;

/**
 * Created by vegen on 2018/3/6.
 * 首页-资讯
 */

public class NewsFragment extends AppBaseFragment<MainContract.Presenter> implements MainContract.View {

    private final String NEWS_DATA_URL = ROOT_URL + "/PhotoZhjnc.aspx";

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    public static NewsFragment getInstance(){
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected MainContract.Presenter presenter() {
        if (mPresenter == null) mPresenter = new NewsPresenter(this);
        return mPresenter;
    }

    @Override
    protected int layoutId() {
        return R.layout.app_fragment_news;
    }

    @Override
    protected void setupUI() {
    }

    @Override
    protected void initData() {
        loadData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        loadData();
    }

    protected void loadData() {
        presenter().getNewsList(NEWS_DATA_URL);
    }

    @Override
    public void showNewsList(List<NewsList> newsListList) {
        showContentView();
    }
}
