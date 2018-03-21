package com.vegen.smartcampus.baseframework.mvp.view;

/**
 * Created by vegen on 2018/2/23.
 */

import android.app.Application;

public interface BaseView {

    void showLoading(String msg);

    void hideLoading();

    void showMessage(String message);

    void loadMoreFail();

    void loadMoreEnd(boolean end);

    Application application();

}