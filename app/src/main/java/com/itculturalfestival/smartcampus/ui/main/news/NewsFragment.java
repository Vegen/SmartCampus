package com.itculturalfestival.smartcampus.ui.main.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itculturalfestival.smartcampus.AppBaseFragment;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.HomeNewsAdapter;
import com.itculturalfestival.smartcampus.utils.FullyGridLayoutManager;
import com.itculturalfestival.smartcampus.utils.ItemDecoration.DividerGridItemDecoration;
import com.itculturalfestival.smartcampus.utils.ItemDecoration.GridItemDecoration;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import butterknife.Bind;

/**
 * Created by Huguang on 2018/3/13.
 */

public class NewsFragment extends AppBaseFragment {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    TextView tvFooter;

    private HomeNewsAdapter homeNewsAdapter;

    public static NewsFragment getInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter presenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.app_fragment_news;
    }

    @Override
    protected void setupUI() {
        showContentView();
        homeNewsAdapter = new HomeNewsAdapter();
        homeNewsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        GridLayoutManager layoutManage = new FullyGridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManage);
        recyclerView.setAdapter(homeNewsAdapter);
        recyclerView.addItemDecoration(new GridItemDecoration());
        View emptyView = View.inflate(getContext(), R.layout.app_view_empty, null);
        homeNewsAdapter.setEmptyView(emptyView);
        View footerView = LayoutInflater.from(getActivity()).inflate(R.layout.app_footer_home_news, (ViewGroup)root, false);
        homeNewsAdapter.setFooterView(footerView);
        homeNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e(tag, "position:" + position);
            }
        });
        tvFooter = footerView.findViewById(R.id.tv_footer);
        if (tvFooter != null){
            tvFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.e(tag, "tvFootertvFooter:");
                }
            });
        }
    }

    @Override
    protected void initData() {

    }

    public HomeNewsAdapter getHomeNewsAdapter(){
        return homeNewsAdapter;
    }
}
