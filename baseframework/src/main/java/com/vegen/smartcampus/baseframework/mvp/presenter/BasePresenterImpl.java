package com.vegen.smartcampus.baseframework.mvp.presenter;

import com.vegen.smartcampus.baseframework.network.HttpLinker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vegen on 2018/2/23.
 */

public class BasePresenterImpl<T> implements BasePresenter {

    protected T mView;

    protected List<HttpLinker> mHttpLinkers = new ArrayList<>();

    public BasePresenterImpl(T view) {
        mView = view;
        start();
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        for (HttpLinker httpLinker : mHttpLinkers) {
            httpLinker.release();
        }

        mView = null;
    }

}
