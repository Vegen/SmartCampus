package com.itculturalfestival.smartcampus;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.mvp.view.BaseMvpActivity;

/**
 * Created by vegen on 2018/3/5.
 */

public abstract class AppBaseActivity<T extends BasePresenter> extends BaseMvpActivity<T>  {
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.fitsSystemWindows(true);
        mImmersionBar.transparentBar();
        mImmersionBar.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}
