package com.itculturalfestival.smartcampus.ui.main;

import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.BaseFragmentPagerAdapter;
import com.itculturalfestival.smartcampus.ui.custom.BottomTab;
import com.itculturalfestival.smartcampus.ui.custom.NoScrollViewPager;
import com.itculturalfestival.smartcampus.ui.main.home.HomeFragment;
import com.itculturalfestival.smartcampus.utils.EaseHelper;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

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
    }

    @Override
    protected void initData() {
        //注册一个监听连接状态的listener
        EaseHelper.getInstance().addConnectionListener(this);
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
    }

    private void setupBottomTab() {
        bottomTab.setData(R.menu.app_bottombar_menu);
        bottomTab.setupViewPager(viewPager);
        bottomTab.setOnTabClickListener(((view, itemId, position1) -> {
            viewPager.setCurrentItem(position1, false);
        }));

        bottomTab.selectTab(0);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(0);
            return;
        } else {
            exitBy2Click();
        }

    }

    public Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit;
        if (isExit == false) {
            isExit = true; // 用户第一次按下返回键
            showToast("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
        }
    }
}
