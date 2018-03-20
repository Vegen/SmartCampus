package com.vegen.smartcampus.baseframework.commonbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.vegen.smartcampus.baseframework.network.HttpError;
import com.vegen.smartcampus.baseframework.ui.LoadingProgressDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * Created by vegen on 2018/2/23.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int layoutId();

    protected abstract void setupUI();

    protected abstract void initData();

    protected final String tag = this.getClass().getSimpleName();

    private List<Disposable> disposableList = new ArrayList<>();

    private LoadingProgressDialog loadingProgressDialog;

    protected View actionbarLayout;

    private InputMethodManager imm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        ButterKnife.bind(this);
        setupUI();
        initData();
    }

    /**
     * 是否需要登录用户，是表示会检测当前用户是否已登录
     * 否则则提示，并结束该Activity
     * @return 默认为false
     */
    protected boolean isNeedLoginUser() {
        return false;
    }


    protected void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.imm = null;
        ButterKnife.unbind(this);
        closeEventBus();
        disposeAll();
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

    protected void addDisposable(Disposable disposable) {
        disposableList.add(disposable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void showToast(@StringRes int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(@StringRes int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
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

    public LoadingProgressDialog showProgressDialog() {
        if (loadingProgressDialog == null) {
            loadingProgressDialog = new LoadingProgressDialog(this);
        }
        loadingProgressDialog.show();
        return loadingProgressDialog;
    }

    public void showProgressDialog(String message) {
        if (loadingProgressDialog == null) {
            loadingProgressDialog = new LoadingProgressDialog(this);
        }
        loadingProgressDialog.show(message);
    }

    public LoadingProgressDialog showProgressDialog(String message, boolean canceledOnTouchOutside) {
        if (loadingProgressDialog == null) {
            loadingProgressDialog = new LoadingProgressDialog(this);
        }
        loadingProgressDialog.show(message, canceledOnTouchOutside);

        return loadingProgressDialog;
    }

    public void dismissProgressDialog() {
        if (loadingProgressDialog != null) {
            loadingProgressDialog.dismiss();
        }
    }

    protected void setSwipeRefreshing(final SwipeRefreshLayout swipeRefreshLayout, final boolean refreshing) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(refreshing);
                }
            });
        }
    }

    public void showErrorToast(Throwable throwable) {
        throwable.printStackTrace();
        String errorMessage = HttpError.getErrorMessage(throwable);
        if (errorMessage != null) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
