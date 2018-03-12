package com.itculturalfestival.smartcampus.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.itculturalfestival.smartcampus.AppBaseFragment;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.BaseFragmentPagerAdapter;
import com.itculturalfestival.smartcampus.entity.News;
import com.itculturalfestival.smartcampus.ui.main.news.NewsFragment;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.itculturalfestival.smartcampus.network.Url.ROOT_URL;

/**
 * Created by vegen on 2018/3/6.
 * 首页-资讯
 */

public class HomeFragment extends AppBaseFragment<MainContract.Presenter> implements MainContract.View {

    private final String NEWS_DATA_URL = ROOT_URL + "/PhotoZhjnc.aspx";

    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private BaseFragmentPagerAdapter fragmentPagerAdapter;

    public static HomeFragment getInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AppBarLayout getAppBarLayout() {
        return appBarLayout;
    }

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
        return R.layout.app_fragment_news;
    }

    @Override
    protected void setupUI() {
        List<String> imgs = new ArrayList<>();
        imgs.add("https://www.baidu.com/img/bd_logo1.png");
        imgs.add("https://www.baidu.com/img/bd_logo1.png");
        imgs.add("https://www.baidu.com/img/bd_logo1.png");
        banner.setImages(imgs);
        banner.setImageLoader(new GlideImageLoader());
        banner.start();
        banner.startAutoPlay();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        List<String> strings = new ArrayList<>();
        strings.add("快讯");
        strings.add("要闻");
        strings.add("综合");
        strings.add("其他");
        fragmentPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), fragments, strings);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context)
                    .load(path)//图片地址
                    .crossFade()
                    .into(imageView);

        }
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
}
