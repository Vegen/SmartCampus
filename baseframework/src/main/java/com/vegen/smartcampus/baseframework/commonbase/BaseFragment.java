package com.vegen.smartcampus.baseframework.commonbase;

/**
 * Created by vegen on 2018/2/23.
 */

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vegen.smartcampus.baseframework.mvp.view.BaseView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment implements BaseView {

    protected abstract int layoutId();

    protected abstract void setupUI();

    protected abstract void initData();

    protected final String tag = this.getClass().getSimpleName();

    private List<Disposable> disposableList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeEventBus();
        disposeAll();
    }

    protected void addDisposable(Disposable disposable) {
        disposableList.add(disposable);
    }

    protected void disposeAll() {
        try {
            for (int i = 0; i < disposableList.size(); i++) {
                Disposable disposable = disposableList.get(i);
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        } catch (Exception ignored) {
        }
    }

    protected void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    protected void showToast(@StringRes int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_LONG).show();
    }

    protected void openEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    protected void closeEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 关闭粘性事件的EventBus
     * @param eventType 粘性事件
     */
    protected <C> void closeStickyEventBus(Class<C> eventType){
        closeEventBus();
        C stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null){
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public Application application() {
        return getActivity().getApplication();
    }

}
