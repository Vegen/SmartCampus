package com.itculturalfestival.smartcampus.ui.main.home.topfun;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.Constant;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.EmploymentListAdapter;
import com.itculturalfestival.smartcampus.entity.Employment;
import com.itculturalfestival.smartcampus.utils.ItemDecoration.ListItemDecoration;
import com.itculturalfestival.smartcampus.utils.QuickReturnTopManager;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Huguang on 2018/3/25.
 */

public class EmploymentListActivity extends AppBaseActivity<EmploymentListContract.Presenter> implements EmploymentListContract.View {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private EmploymentListAdapter employmentListAdapter;
    private int page = 1;
    private int type;
    private String title;
    private String url;

    public static void start(Context context,String title, String url, int type){
        Intent intent = new Intent(context, EmploymentListActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected EmploymentListContract.Presenter presenter() {
        if (mPresenter == null)
            mPresenter = new EmploymentArticleDetailPresenter(this);
        return mPresenter;
    }

    @Override
    protected int layoutId() {
        return R.layout.app_layout_list;
    }

    @Override
    protected void setupUI() {
        type = getIntent().getIntExtra("type",  Constant.NOTICE);
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        setTitle(title);

        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                initData();
            }
        });
        employmentListAdapter = new EmploymentListAdapter();
        employmentListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(employmentListAdapter);
        new QuickReturnTopManager(recyclerView);
        recyclerView.addItemDecoration(new ListItemDecoration());
        View emptyView = View.inflate(this, R.layout.app_view_empty, null);
        employmentListAdapter.setEmptyView(emptyView);
        if (type != Constant.FAIR)
            employmentListAdapter.setOnLoadMoreListener(() -> presenter().getEmploymentList(url + page, type), recyclerView);

        refreshLayout.startRefresh();
    }

    @Override
    protected void initData() {
        page = 1;
        presenter().getEmploymentList(url + page, type);
    }

    @Override
    public void showEmploymentList(List<Employment> employments) {
        if (!employments.isEmpty()){
            if (page == 1){
                employmentListAdapter.getData().clear();
            }
            employmentListAdapter.addData(employments);
            page ++;
        }
    }

    @Override
    public void hideLoading(boolean isFail) {
        super.hideLoading(isFail);
        if (refreshLayout != null){
            refreshLayout.finishRefreshing();
        }
        if (employmentListAdapter != null){
            employmentListAdapter.loadMoreComplete();
        }
    }

    @Override
    public void loadMoreFail() {
        super.loadMoreFail();
        if (employmentListAdapter != null)
            employmentListAdapter.loadMoreFail();
    }

    @Override
    public void loadMoreEnd(boolean end) {
        super.loadMoreEnd(end);
        if (employmentListAdapter != null)
            employmentListAdapter.loadMoreEnd();
    }
}
