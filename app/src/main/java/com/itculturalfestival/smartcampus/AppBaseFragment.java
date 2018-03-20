package com.itculturalfestival.smartcampus;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.itculturalfestival.smartcampus.event.EmptyEvent;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.mvp.view.BaseMvpFragment;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by vegen on 2018/3/5.
 */

public abstract class AppBaseFragment<T extends BasePresenter> extends BaseMvpFragment<T> implements SwipeRefreshLayout.OnRefreshListener {

    protected View root;

    protected boolean isPrepared;

    protected boolean isVisible;

    protected boolean isLoad;

    protected boolean reloadBeforeRecreate = false;

    private View contentView;
    private View loadingView;
    private View emptyView;
    private View errorView;

    long now;

    protected String tag = this.getClass().getSimpleName();

    @Nullable
    @Bind(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        if (reloadBeforeRecreate) {
            reloadBeforeRecreate = false;
            lazyLoad();
        } else {
            attemptLoad();
        }
    }

    protected void lazyLoad() {
    }

    protected void attemptLoad() {
        AppBaseFragment parentFragment = (AppBaseFragment) getParentFragment();
        if (parentFragment != null && !parentFragment.isVisible) return;
        if (!isPrepared || !isVisible || isLoad) return;
        isLoad = true;
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected <T extends View> T getView(int viewId){
        return (T) root.findViewById(viewId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        now = System.currentTimeMillis();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.app_fragment_base_loading, container, false);
        if (layoutId() == 0) {
            LogUtils.e("Content Layout Id is 0");
            return root;
        }

        contentView = inflater.inflate(layoutId(), null);
        ((FrameLayout) root).addView(contentView);

        loadingView = inflater.inflate(getLoadingViewId(), null);
        ((FrameLayout) root).addView(loadingView);

        emptyView = inflater.inflate(getEmptyLayoutId(), null);
        ((FrameLayout) root).addView(emptyView);

        errorView = inflater.inflate(getErrorLayoutId(), null);
        ((FrameLayout) root).addView(errorView);
        errorView.findViewById(R.id.iv_error).setOnClickListener(v -> {
            showLoadingView();
            lazyLoad();
        });

        showLoadingView();
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        attemptLoad();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
            swipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    protected void showContentView() {
        if (contentView.getVisibility() == View.VISIBLE) {
            return;
        }

        contentView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);

        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);

        ObjectAnimator objectAnimator = ObjectAnimator
                .ofPropertyValuesHolder(loadingView, p1)
                .setDuration(500);
        objectAnimator.addListener(
                new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loadingView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
        objectAnimator.start();

        ObjectAnimator.ofFloat(contentView, "alpha", 0f, 1f).start();

    }

    protected void showEmptyView() {
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);

        ObjectAnimator.ofFloat(loadingView, "alpha", 1f, 0f).start();
        ObjectAnimator.ofFloat(loadingView, "scaleY", 1f, 0f).start();
        ObjectAnimator.ofFloat(loadingView, "scaleX", 1f, 0f).start();

        ObjectAnimator.ofFloat(emptyView, "alpha", 0f, 1f).start();

        EventBus.getDefault().post(new EmptyEvent(getClass().getSimpleName()));
    }

    protected void showLoadingView() {
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    protected void showErrorView() {
        contentView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    public View getRoot(){
        return root;
    }

    @Override
    public void onRefresh() {
        setSwipeRefreshLayoutRefreshing(true);
    }

    @Override
    public void showLoading(String msg) {
        super.showLoading(msg);
        setSwipeRefreshLayoutRefreshing(true);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        setSwipeRefreshLayoutRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected void setSwipeRefreshLayoutEnable(boolean enable) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(enable);
        }
    }

    protected void setSwipeRefreshLayoutRefreshing(boolean refreshing) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.post(() -> {
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(refreshing);
                }
            });
        }
    }

    protected
    @LayoutRes
    int getLoadingViewId() {
        return R.layout.app_view_loading;
    }

    protected
    @LayoutRes
    int getEmptyLayoutId() {
        return R.layout.app_view_empty;
    }

    protected
    @LayoutRes
    int getErrorLayoutId() {
        return R.layout.app_view_error;
    }
}
