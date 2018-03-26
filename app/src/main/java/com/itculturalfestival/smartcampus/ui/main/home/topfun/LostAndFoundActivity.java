package com.itculturalfestival.smartcampus.ui.main.home.topfun;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.LostAndFoundAdapter;
import com.itculturalfestival.smartcampus.entity.db.Lost;
import com.itculturalfestival.smartcampus.utils.ItemDecoration.GridItemDecoration;
import com.itculturalfestival.smartcampus.utils.QuickReturnTopManager;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import java.util.List;

import butterknife.Bind;

/**
 * Created by vegen on 2018/3/26.
 * 失物招领
 */

public class LostAndFoundActivity extends AppBaseActivity<LostAndFoundContract.Presenter> implements LostAndFoundContract.View {

    private static final int SKIP = 8;                       // 加载条数

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private LostAndFoundAdapter lostAndFoundAdapter;
    private int skip;       // 偏移量

    public static void start(Context context) {
        Intent intent = new Intent(context, LostAndFoundActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showLostList(List<Lost> lostList) {
        if (lostList != null) {
            if (skip == 0) {
                lostAndFoundAdapter.getData().clear();
            }
            if (lostList.size() < SKIP) {
                lostAndFoundAdapter.loadMoreEnd();
            }
            lostAndFoundAdapter.addData(lostList);
            skip += SKIP;
        }
    }

    @Override
    public void hideLoading(boolean isFail) {
        super.hideLoading(isFail);
        if (refreshLayout != null) refreshLayout.finishRefreshing();
        if (lostAndFoundAdapter != null) lostAndFoundAdapter.loadMoreComplete();
    }

    @Override
    protected LostAndFoundContract.Presenter presenter() {
        if (mPresenter == null)
            mPresenter = new LostAndFoundPresenter(this);
        return mPresenter;
    }

    @Override
    public void loadMoreFail() {
        super.loadMoreFail();
        lostAndFoundAdapter.loadMoreFail();
    }

    @Override
    public void loadMoreEnd(boolean end) {
        super.loadMoreEnd(end);
        lostAndFoundAdapter.loadMoreEnd();
    }

    @Override
    protected int layoutId() {
        return R.layout.app_layout_list;
    }

    @Override
    protected void setupUI() {
        setTitle("失物招领");

        lostAndFoundAdapter = new LostAndFoundAdapter();
        lostAndFoundAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        RecyclerView.LayoutManager layoutManage = /*new FullyGridLayoutManager(this, 2); // */new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManage);
        recyclerView.setAdapter(lostAndFoundAdapter);
        new QuickReturnTopManager(recyclerView);
        recyclerView.addItemDecoration(new GridItemDecoration());
        View emptyView = View.inflate(this, R.layout.app_view_empty, null);
        lostAndFoundAdapter.setEmptyView(emptyView);
        lostAndFoundAdapter.setOnLoadMoreListener(() -> {
                    presenter().getLostList(SKIP, skip);
                    LogUtils.e("更多更多！！！");
                }, recyclerView);
        lostAndFoundAdapter.disableLoadMoreIfNotFullPage();
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                loadData();
            }
        });

    }

    @Override
    protected void initData() {
        loadData();
    }

    private void loadData() {
        skip = 0;
        LogUtils.e("刷新刷新！！！");
        presenter().getLostList(SKIP, skip);
    }
}
