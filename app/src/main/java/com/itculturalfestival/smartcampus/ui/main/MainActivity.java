package com.itculturalfestival.smartcampus.ui.main;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.BaseFragmentPagerAdapter;
import com.itculturalfestival.smartcampus.ui.custom.BottomTab;
import com.itculturalfestival.smartcampus.ui.custom.NoScrollViewPager;
import com.itculturalfestival.smartcampus.ui.main.home.HomeFragment;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.Bmob;

import static com.itculturalfestival.smartcampus.Constant.BmobApplicationId;

/**
 * Created by vegen on 2018/3/1.
 */

public class MainActivity extends AppBaseActivity {


    @Bind(R.id.viewPager)
    NoScrollViewPager viewPager;
    @Bind(R.id.bottom_tab)
    BottomTab bottomTab;
    @Bind(R.id.container)
    LinearLayout container;
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
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected void setupUI() {
        setDisplayHomeAsUpEnabled(false);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        setupViewPager();
        setupBottomTab();
        lastPosition = viewPager.getCurrentItem();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        HomeFragment homeFragment = (HomeFragment) pageAdapter.instantiateItem(viewPager, 0);
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
//                changeToolbar(position);
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

    private void setupBottomTab() {
        bottomTab.setData(R.menu.app_bottombar_menu);
        bottomTab.setupViewPager(viewPager);
        bottomTab.setOnTabClickListener(((view, itemId, position1) -> {
            viewPager.setCurrentItem(position1, false);
        }));

        bottomTab.selectTab(0);

    }
}
