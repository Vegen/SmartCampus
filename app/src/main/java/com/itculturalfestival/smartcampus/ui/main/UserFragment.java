package com.itculturalfestival.smartcampus.ui.main;

import android.os.Bundle;

import com.itculturalfestival.smartcampus.AppBaseFragment;
import com.itculturalfestival.smartcampus.R;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;

/**
 * Created by vegen on 2018/3/7.
 * 首页-我的
 */

public class UserFragment extends AppBaseFragment {

    public static UserFragment getInstance(){
        Bundle args = new Bundle();
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter presenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.test;
    }

    @Override
    protected void setupUI() {
        showEmptyView();
    }

    @Override
    protected void initData() {

    }
}
