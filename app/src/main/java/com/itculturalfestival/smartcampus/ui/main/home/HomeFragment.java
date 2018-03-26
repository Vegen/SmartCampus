package com.itculturalfestival.smartcampus.ui.main.home;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.itculturalfestival.smartcampus.AppBaseFragment;
import com.itculturalfestival.smartcampus.Constant;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.BaseFragmentPagerAdapter;
import com.itculturalfestival.smartcampus.entity.NewsList;
import com.itculturalfestival.smartcampus.ui.main.home.topfun.LostAndFoundActivity;
import com.itculturalfestival.smartcampus.ui.main.home.topfun.RecruitAndEmploymentMessageActivity;
import com.itculturalfestival.smartcampus.ui.main.home.topfun.TopFunArticleDetailActivity;
import com.itculturalfestival.smartcampus.utils.GlideImageLoader;
import com.vegen.smartcampus.baseframework.utils.LogUtils;
import com.vegen.smartcampus.baseframework.utils.SystemUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import io.reactivex.disposables.Disposable;
import rx.Subscription;

import static com.itculturalfestival.smartcampus.network.Url.ROOT_URL;

/**
 * Created by vegen on 2018/3/6.
 * 首页
 */

public class HomeFragment extends AppBaseFragment<HomeContract.Presenter> implements HomeContract.View, AppBarLayout.OnOffsetChangedListener {

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
    @Bind(R.id.rl_recruit)
    LinearLayout rlRecruit;
    @Bind(R.id.rl_employment)
    LinearLayout rlEmployment;
    @Bind(R.id.rl_lost_and_found)
    LinearLayout rlLostAndFound;

    private BaseFragmentPagerAdapter fragmentPagerAdapter;
    private NewsFragment flashFragment;
    private NewsFragment focusFragment;
    private NewsFragment comprehensiveFragment;
    private NewsFragment otherFragment;

    private List<String> imgs = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<com.itculturalfestival.smartcampus.entity.db.Banner> bannerList = new ArrayList<>();

    public static HomeFragment getInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected HomeContract.Presenter presenter() {
        if (mPresenter == null) mPresenter = new HomePresenter(this);
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
    protected boolean isAnimatorDismissLoading() {
        return false;
    }

    @Override
    protected void setupUI() {
        showContentView();
        ImmersionBar.setTitleBar(getActivity(), toolbar);
        float marginHeight = SystemUtils.getStatusBarHeight(getContext()) + SystemUtils.getActionBarHeight(getContext());
        viewTop.getLayoutParams().height = (int) marginHeight;
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new GlideImageLoader());
        banner.setOnBannerListener(position -> {
            if (bannerList.isEmpty()) return;
            TopFunArticleDetailActivity.start(getContext(), bannerList.get(position).getTitle(), bannerList.get(position).getNextUrl());
        });
        List<Fragment> fragments = new ArrayList<>();
        flashFragment = NewsFragment.getInstance(Constant.NEWS_TYPE_FLASH);
        focusFragment = NewsFragment.getInstance(Constant.NEWS_TYPE_FOCUS);
        comprehensiveFragment = NewsFragment.getInstance(Constant.NEWS_TYPE_COMPREHENSIVE);
        otherFragment = NewsFragment.getInstance(Constant.NEWS_TYPE_OTHER);
        fragments.add(flashFragment);
        fragments.add(focusFragment);
        fragments.add(comprehensiveFragment);
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
    }

    @Override
    public void onStart() {
        super.onStart();//开始轮播
        if (banner != null) banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (banner != null) banner.stopAutoPlay();
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        if (banner != null) banner.stopAutoPlay();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if (banner != null) banner.startAutoPlay();
    }

    @Override
    public void showNewsList(List<NewsList> newsListList) {
        if (newsListList == null) return;
        setNewsListNewData(Constant.NEWS_TYPE_FLASH, 2, newsListList);
        setNewsListNewData(Constant.NEWS_TYPE_FOCUS, 0, newsListList);
        setNewsListNewData(Constant.NEWS_TYPE_COMPREHENSIVE, 1, newsListList);
        setNewsListNewData(Constant.NEWS_TYPE_OTHER, 3, newsListList);
    }

    @Override
    public void moreNewsClassId(List<String> strings) {
        if (strings == null) return;
        setMoreNewsClassId(Constant.NEWS_TYPE_FLASH, 2, strings);
        setMoreNewsClassId(Constant.NEWS_TYPE_FOCUS, 0, strings);
        setMoreNewsClassId(Constant.NEWS_TYPE_COMPREHENSIVE, 1, strings);
        setMoreNewsClassId(Constant.NEWS_TYPE_OTHER, 3, strings);
    }

    @Override
    public void showBanner(List<com.itculturalfestival.smartcampus.entity.db.Banner> bannerList) {
        imgs.clear();
        titles.clear();
        this.bannerList.clear();
        if (bannerList != null) {
            this.bannerList.addAll(bannerList);
            for (com.itculturalfestival.smartcampus.entity.db.Banner banner : bannerList) {
                imgs.add(banner.getImgUrl());
                titles.add(banner.getTitle());
            }
        }
        banner.setImages(imgs);
        banner.setBannerTitles(titles);
        banner.start();
    }

    private int loadFinish;

    @Override
    public void hideLoading(boolean isFail) {
//        super.hideLoading(isFail);
        if (loadFinish == 2) {
            setSwipeRefreshLayoutRefreshing(false);
        }
        loadFinish ++;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int absVerticalOffset = Math.abs(verticalOffset);
        float alpha = (float) absVerticalOffset / banner.getHeight();
        if (alpha > 1) alpha = 1;
        toolbar.setAlpha(alpha);
        if (alpha == 0) {
            swipeRefreshLayout.setEnabled(true);
        } else {
            swipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        loadData();
    }

    @OnClick({R.id.rl_recruit, R.id.rl_employment, R.id.rl_lost_and_found})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_recruit:
                // 招生
                RecruitAndEmploymentMessageActivity.start(getContext(), Constant.MESSAGE_RECRUIT);
                break;
            case R.id.rl_employment:
                // 就业
                RecruitAndEmploymentMessageActivity.start(getContext(), Constant.MESSAGE_EMPLOYMENT);
                break;
            case R.id.rl_lost_and_found:
                // 失物招领
                LostAndFoundActivity.start(getContext());
                break;
        }
    }


    private void setNewsListNewData(int pager, int dataPosition, List<NewsList> newsListList) {
        ((NewsFragment) viewPager.getAdapter().instantiateItem(viewPager, pager)).getHomeNewsAdapter().setNewData(newsListList.get(dataPosition).getNewsList());
    }

    private void setMoreNewsClassId(int pager, int dataPosition, List<String> strings) {
        ((NewsFragment) viewPager.getAdapter().instantiateItem(viewPager, pager)).setClassId(strings.get(dataPosition));
    }

    private void loadData() {
        loadFinish = 1;
        presenter().getBanner();
        presenter().getNewsList(NEWS_DATA_URL);
    }
}
