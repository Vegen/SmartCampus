package com.itculturalfestival.smartcampus.ui.main;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.BaseFragmentPagerAdapter;
import com.itculturalfestival.smartcampus.ui.view.BottomTab;
import com.itculturalfestival.smartcampus.ui.view.NoScrollViewPager;
import com.itculturalfestival.smartcampus.utils.CommonUtils;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by vegen on 2018/3/1.
 */

public class MainActivity extends AppBaseActivity {

    @Bind(R.id.viewPager)
    NoScrollViewPager viewPager;
    @Bind(R.id.bottom_tab)
    BottomTab bottomTab;
    @Bind(R.id.statusBar)
    View statusBar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbarLayout)
    LinearLayout toolbarLayout;
    @Bind(R.id.container)
    RelativeLayout container;
    private BaseFragmentPagerAdapter pageAdapter;
    private int position = 0;
    private int lastPosition;
    private int mStatusHeight = 0;

    @Override
    protected int layoutId() {
        return R.layout.app_activity_main;
    }

    @Override
    protected BasePresenter presenter() {
        return null;
    }

    @Override
    protected void setupUI() {
        setSupportActionBar(toolbar);
        setDisplayHomeAsUpEnabled(false);

        setupViewPager();
        setupBottomTab();
        lastPosition = viewPager.getCurrentItem();
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary).init();
        mStatusHeight = CommonUtils.getStatusBarHeight(this);
        statusBar.getLayoutParams().height = mStatusHeight;
        setStatusBarTranslucent(true);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        HomeFragment homeFragment = (HomeFragment) pageAdapter.instantiateItem(viewPager, 0);
        setStatusAlpha(homeFragment, 0);
    }

    private void setupViewPager() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.getInstance());
        fragments.add(TeamFragment.getInstance());
        fragments.add(CircleFragment.getInstance());
        fragments.add(MessageFragment.getInstance());
        fragments.add(UserFragment.getInstance());

        pageAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pageAdapter);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                changeToolbar(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (makeTranslucent) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    private void changeToolbar(int position) {
        lastPosition = position;
        toolbar.setTitle(position == 0 ? "智慧校园" : bottomTab.getTabText(position));
        if (position == 0) {
            HomeFragment homeFragment = (HomeFragment) pageAdapter.instantiateItem(viewPager, 0);
            setStatusAlpha(homeFragment, position);
        }else {
            toolbarLayout.setAlpha(1);
        }
    }

    private void setStatusAlpha(HomeFragment homeFragment, int position) {

//        homeFragment.getCoordinatorLayout().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if (position == 0) {
//                    if (Math.abs(top) >= toolbar.getHeight()) {
//                        toolbarLayout.setAlpha(1);
//                    } else {
//                        toolbarLayout.setAlpha((float) (1.0 * Math.abs(top) / toolbar.getHeight()));
//                    }
//                }
//            }
//        });
        homeFragment.getAppBarLayout().addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (toolbarLayout != null && toolbar != null) {
                    if (Math.abs(verticalOffset) >= toolbar.getHeight()) {
                        toolbarLayout.setAlpha(1);
                    } else {
                        if (position == 0) {
                            toolbarLayout.setAlpha((float) (1.0 * Math.abs(verticalOffset) / toolbar.getHeight()));
                        }
                        if (position != 0) {
                            toolbarLayout.setAlpha(1);
                        }
                    }
                }
            }
        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            homeFragment.getAppBarLayout().setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (Math.abs(scrollY) >= toolbar.getHeight()){
//                    toolbarLayout.setAlpha(1);
//                }else {
//                    toolbarLayout.setAlpha((float) (1.0 * Math.abs(scrollY) / toolbar.getHeight()));
//                }
////                if (position != 0){
////                    toolbarLayout.setAlpha(1);
////                }
//                }
//            });
//        }
    }

    private void setupBottomTab() {
        bottomTab.setData(R.menu.app_bottombar_menu);
        bottomTab.setupViewPager(viewPager);
        bottomTab.setOnTabClickListener(((view, itemId, position1) ->{
            viewPager.setCurrentItem(position1, false);
        }));

        bottomTab.selectTab(0);

    }
}
