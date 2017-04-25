package com.itculturalfestival.smartcampus.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @creation_time: 2017/4/4
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 引导页的ViewPager适配器
 */

public class GuideViewPagerAdapter extends PagerAdapter {

    private List<View> views;

    public GuideViewPagerAdapter(List<View> views){
        super();
        this.views = views;
    }

    @Override
    public int getCount() {
        return views != null ? views.size(): 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position), 0);
        return views.get(position);
    }
}
