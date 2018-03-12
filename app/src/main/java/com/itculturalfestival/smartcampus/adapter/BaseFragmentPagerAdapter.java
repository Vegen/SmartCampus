package com.itculturalfestival.smartcampus.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huguang on 2018/3/13.
 */

public class BaseFragmentPagerAdapter  extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        mFragmentList.addAll(fragmentList);
        mTitleList.addAll(titleList);
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList.addAll(fragmentList);
        for (int i = 0; i < fragmentList.size(); i ++){
            mTitleList.add("");
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
