package com.itculturalfestival.smartcampus.ui.main.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.Constant;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.MoreNewsAdapter;
import com.itculturalfestival.smartcampus.entity.News;
import com.itculturalfestival.smartcampus.network.Url;
import com.itculturalfestival.smartcampus.utils.ItemDecoration.ListItemDecoration;
import com.itculturalfestival.smartcampus.utils.QuickReturnTopManager;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by vegen on 2018/3/21.
 * 更多新闻
 */

public class MoreNewsActivity extends AppBaseActivity<MoreNewsContract.Presenter> implements MoreNewsContract.View {

    private String MORE_NEWS_URL;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;

    private MoreNewsAdapter moreNewsAdapter;
    private List<News> newsList;

    private int newsType;
    private String nextUrl;
    private int page = 1;
    private String __VIEWSTATE = "";
    private String __VIEWSTATEGENERATOR = "";
    private String __EVENTVALIDATION = "";

    public static void start(Context context, int newsType, String nextUrl){
        Intent intent = new Intent();
        intent.putExtra("newsType", newsType);
        intent.putExtra("nextUrl", nextUrl);
        intent.setClass(context, MoreNewsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showNewsList(List<News> newsList) {
        if (page == 1) {
            moreNewsAdapter.getData().clear();
            moreNewsAdapter.setNewData(newsList);
        }else {
            moreNewsAdapter.addData(newsList);
        }
        page ++;
    }

    @Override
    public void nextNewsListForm(Map<String, String> form) {
        __VIEWSTATE = form.get("__VIEWSTATE");
        __VIEWSTATEGENERATOR = form.get("__VIEWSTATEGENERATOR");
        __EVENTVALIDATION = form.get("__EVENTVALIDATION");
    }

    @Override
    protected MoreNewsContract.Presenter presenter() {
        if (mPresenter == null)
            mPresenter = new MoreNewsPresenter(this);
        return mPresenter;
    }

    @Override
    protected int layoutId() {
        return R.layout.app_activity_more_news;
    }

    @Override
    protected void setupUI() {
        newsType = getIntent().getIntExtra("newsType", Constant.NEWS_TYPE_FLASH);
        setTitle(setTitleText(newsType));
        nextUrl = getIntent().getStringExtra("nextUrl");
        if (nextUrl == null) nextUrl = "/PreviewPhoto.aspx?classid=";
        MORE_NEWS_URL = Url.ROOT_URL +  nextUrl;
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                initData();
            }
        });
        moreNewsAdapter = new MoreNewsAdapter();
        moreNewsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(moreNewsAdapter);
        new QuickReturnTopManager(recyclerView);
        recyclerView.addItemDecoration(new ListItemDecoration());
        View emptyView = View.inflate(this, R.layout.app_view_empty, null);
        moreNewsAdapter.setEmptyView(emptyView);
        newsList = new ArrayList<>();
        moreNewsAdapter.setNewData(newsList);
        moreNewsAdapter.setOnLoadMoreListener(() ->
                presenter().getNewsList(page, MORE_NEWS_URL, newsType, __VIEWSTATE, __VIEWSTATEGENERATOR, __EVENTVALIDATION), recyclerView);

        refreshLayout.startRefresh();
    }

    @Override
    protected void initData() {
        page = 1;
        presenter().getNewsList(MORE_NEWS_URL, newsType);
    }

    @Override
    public void hideLoading(boolean isFail) {
        super.hideLoading(isFail);
        if (refreshLayout != null){
            refreshLayout.finishRefreshing();
        }
        if (moreNewsAdapter != null){
            moreNewsAdapter.loadMoreComplete();
        }
    }

    @Override
    public void loadMoreFail() {
        super.loadMoreFail();
        if (moreNewsAdapter != null)
            moreNewsAdapter.loadMoreFail();
    }

    @Override
    public void loadMoreEnd(boolean end) {
        super.loadMoreEnd(end);
        if (moreNewsAdapter != null)
            moreNewsAdapter.loadMoreEnd();
    }

    private String setTitleText(int newType){
        switch (newType){
            case Constant.NEWS_TYPE_FLASH:
                return "校园快讯";
            case Constant.NEWS_TYPE_FOCUS:
                return "学校要闻";
            case Constant.NEWS_TYPE_COMPREHENSIVE:
                return "综合新闻";
            case Constant.NEWS_TYPE_OTHER:
                return "其他新闻";
            default:
                return "更多新闻";
        }
    }
}
