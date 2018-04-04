package com.itculturalfestival.smartcampus.ui.start;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.GuideViewPagerAdapter;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @creation_time: 2017/4/3
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 引导页
 */

public class GuideActivity extends AppBaseActivity implements View.OnClickListener {

    private ViewPager vp;
    private GuideViewPagerAdapter adapter;
    private List<View> views;
    private ImageView iv_process;
    // 引导页图片资源
    private static final int[] pics = {R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3};

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("enter")) {
            enterLogin();
            return;
        }

        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }


    private void enterLogin() {
        Intent intent = new Intent(GuideActivity.this,
                LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected BasePresenter presenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.app_activity_guide;
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected void setupUI() {
        ImmersionBar.with(this).init();
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        views = new ArrayList<View>();

        // 初始化引导页视图列表
        for (int i = 0; i < pics.length; i++) {
            View view = null;
            if (i != pics.length - 1) {
                view = LayoutInflater.from(this).inflate(R.layout.view_guide_process, null);
            }else {
                view = LayoutInflater.from(this).inflate(R.layout.view_guide_end, null);
                iv_process = (ImageView) view.findViewById(R.id.iv_process);
                FrameLayout fl_enter = (FrameLayout) view.findViewById(R.id.fl_enter);
                fl_enter.setTag("enter");
                fl_enter.setOnClickListener(this);
            }
            iv_process = (ImageView) view.findViewById(R.id.iv_process);
            iv_process.setBackgroundResource(pics[i]);
            views.add(view);

        }

        vp = (ViewPager) findViewById(R.id.vp_guide);
        // 初始化adapter
        adapter = new GuideViewPagerAdapter(views);
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new PageChangeListener());

        initDots();
    }

    @Override
    protected void initData() {

    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_bottom);
        dots = new ImageView[pics.length];

        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            // 得到一个LinearLayout下面的每一个子元素
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(false);// 都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(true); // 设置为白色，即选中状态

    }

    /**
     * 设置当前view
     *
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vp.setCurrentItem(position);
    }

    /**
     * 设置当前指示点
     *
     * @param position
     */
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int position) {
            // arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。

        }

        // 当前页面被滑动时调用
        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
            // arg0 :当前页面，及你点击滑动的页面
            // arg1:当前页面偏移的百分比
            // arg2:当前页面偏移的像素位置

        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {
            // 设置底部小点选中状态
            setCurDot(position);
        }

    }
}
