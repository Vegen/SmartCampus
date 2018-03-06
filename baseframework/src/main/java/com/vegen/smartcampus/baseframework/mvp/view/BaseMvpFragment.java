package com.vegen.smartcampus.baseframework.mvp.view;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.vegen.smartcampus.baseframework.commonbase.BaseFragment;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;

/**
 * Created by vegen on 2018/2/23.
 */

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    protected T mPresenter;

    protected abstract T presenter();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter() != null) presenter().destroy();
    }
}
