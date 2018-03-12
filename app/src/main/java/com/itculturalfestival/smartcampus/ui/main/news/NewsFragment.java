package com.itculturalfestival.smartcampus.ui.main.news;

import com.itculturalfestival.smartcampus.AppBaseFragment;
import com.itculturalfestival.smartcampus.R;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;

/**
 * Created by Huguang on 2018/3/13.
 */

public class NewsFragment extends AppBaseFragment {
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
