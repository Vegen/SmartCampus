package com.itculturalfestival.smartcampus.ui.main;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.itculturalfestival.smartcampus.AppBaseFragment;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.BaseFragmentPagerAdapter;
import com.itculturalfestival.smartcampus.entity.NewsList;
import com.itculturalfestival.smartcampus.ui.main.news.NewsFragment;
import com.itculturalfestival.smartcampus.utils.GlideImageLoader;
import com.vegen.smartcampus.baseframework.utils.SystemUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.itculturalfestival.smartcampus.network.Url.ROOT_URL;

/**
 * Created by vegen on 2018/3/6.
 * 首页-资讯
 */

public class HomeFragment extends AppBaseFragment<MainContract.Presenter> implements MainContract.View, AppBarLayout.OnOffsetChangedListener {

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
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.view_top)
    View viewTop;
    private BaseFragmentPagerAdapter fragmentPagerAdapter;
    private NewsFragment focusFragment;
    private NewsFragment comprehensiveFragment;
    private NewsFragment flashFragment;
    private NewsFragment otherFragment;

    public static HomeFragment getInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
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
        return R.layout.app_fragment_home;
    }

    @Override
    public void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    protected void setupUI() {
        ImmersionBar.setTitleBar(getActivity(), toolbar);
        float marginHeight = SystemUtils.getStatusBarHeight(getContext()) + SystemUtils.getActionBarHeight(getContext());
        viewTop.getLayoutParams().height = (int) marginHeight;

        List<String> imgs = new ArrayList<>();
        imgs.add("http://bpic.588ku.com/back_pic/00/13/15/08564453d0190aa.jpg!/fh/300/quality/90/unsharp/true/compress/true");
        imgs.add("http://bpic.588ku.com/back_pic/03/65/44/8057ae8dfe9b121.jpg!/fh/300/quality/90/unsharp/true/compress/true");
        imgs.add("http://bpic.588ku.com/back_pic/00/08/53/17562a43dac4e41.jpg!/fh/300/quality/90/unsharp/true/compress/true");
        banner.setImages(imgs);
        banner.setImageLoader(new GlideImageLoader());
        banner.start();
        banner.startAutoPlay();

        List<Fragment> fragments = new ArrayList<>();
        focusFragment = NewsFragment.getInstance();
        comprehensiveFragment = NewsFragment.getInstance();
        flashFragment = NewsFragment.getInstance();
        otherFragment = NewsFragment.getInstance();
        fragments.add(focusFragment);
        fragments.add(comprehensiveFragment);
        fragments.add(flashFragment);
        fragments.add(otherFragment);
        List<String> strings = new ArrayList<>();
        strings.add("快讯");
        strings.add("要闻");
        strings.add("综合");
        strings.add("其他");
        fragmentPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), fragments, strings);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragments.size());
        swipeRefreshLayout.setProgressViewOffset(false, 180, 380);
    }

    @Override
    public void showNewsList(List<NewsList> newsListList) {
        showContentView();
        focusFragment.getHomeNewsAdapter().setNewData(newsListList.get(0).getNewsList());
        comprehensiveFragment.getHomeNewsAdapter().setNewData(newsListList.get(1).getNewsList());
        flashFragment.getHomeNewsAdapter().setNewData(newsListList.get(2).getNewsList());
        otherFragment.getHomeNewsAdapter().setNewData(newsListList.get(3).getNewsList());
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int absVerticalOffset = Math.abs(verticalOffset);
        float alpha = (float) absVerticalOffset / banner.getHeight();
        if (alpha > 1) alpha = 1;
        toolbar.setAlpha(alpha);
        if (alpha == 0){
            swipeRefreshLayout.setEnabled(true);
        }else {
            swipeRefreshLayout.setEnabled(false);
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
