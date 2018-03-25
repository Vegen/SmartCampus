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
import com.itculturalfestival.smartcampus.ui.main.home.topfun.RecruitAndEmploymentMessageActivity;
import com.itculturalfestival.smartcampus.utils.GlideImageLoader;
import com.vegen.smartcampus.baseframework.utils.SystemUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

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

        List<String> imgs = new ArrayList<>();
        imgs.add("http://bpic.588ku.com/back_pic/00/13/15/08564453d0190aa.jpg!/fh/300/quality/90/unsharp/true/compress/true");
        imgs.add("http://bpic.588ku.com/back_pic/03/65/44/8057ae8dfe9b121.jpg!/fh/300/quality/90/unsharp/true/compress/true");
        imgs.add("http://bpic.588ku.com/back_pic/00/08/53/17562a43dac4e41.jpg!/fh/300/quality/90/unsharp/true/compress/true");
        banner.setImages(imgs);
        banner.setImageLoader(new GlideImageLoader());
        banner.start();
        banner.startAutoPlay();

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
        swipeRefreshLayout.setProgressViewOffset(false, 180, 380);
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
        presenter().getNewsList(NEWS_DATA_URL);
    }
}
