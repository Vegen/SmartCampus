package com.vegen.smartcampus.baseframework.mvp.view;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vegen.smartcampus.baseframework.commonbase.BaseActivity;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;

/**
 * Created by vegen on 2018/2/23.
 */

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView {


    protected T mPresenter;

    protected abstract T presenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter() != null) presenter().destroy();
    }

    @Override
    public void showLoading(String msg) {
        if (msg == null || msg.isEmpty())
            showProgressDialog();
        else
            showProgressDialog(msg);
    }

    @Override
    public void hideLoading(boolean isFail) {
        dismissProgressDialog();
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void loadMoreFail() {

    }

    @Override
    public void loadMoreEnd(boolean end) {

    }

    @Override
    public Application application() {
        return this.getApplication();
    }
}
