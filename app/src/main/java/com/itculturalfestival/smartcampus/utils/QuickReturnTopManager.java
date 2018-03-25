package com.itculturalfestival.smartcampus.utils;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.itculturalfestival.smartcampus.R;

/**
 * Created by vegen on 2018/3/25.
 */
public class QuickReturnTopManager {

    // 当滑动的距离超过该值时，显示topView
    public static final int SCROLL_Y_THRESHOLD_DP = 500;
    int y;

    RecyclerView recyclerView;

    View topView;

    Context context;

    int lastScrollY;

    public QuickReturnTopManager(RecyclerView recyclerView) {
        this.context = recyclerView.getContext();
        this.recyclerView = recyclerView;


        ViewGroup root;

        ViewParent viewGroup = recyclerView;

        while (!((viewGroup instanceof FrameLayout && !(viewGroup instanceof NestedScrollView)) || viewGroup instanceof RelativeLayout)) {
            viewGroup = viewGroup.getParent();
        }

        root = (ViewGroup) viewGroup;
        this.topView = LayoutInflater.from(context).inflate(R.layout.app_view_top, root, false);
        topView.setAlpha(0f);
        root.addView(topView);

        y = ScreenUtils.dpToPxInt(recyclerView.getContext(), SCROLL_Y_THRESHOLD_DP);

        setup();
    }

    private void setup() {
        topView.setOnClickListener(v -> {
            hideTopView();
            recyclerView.scrollToPosition(0);
            if (onScrollToTopCallback != null) {
                onScrollToTopCallback.callback();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (recyclerView.computeVerticalScrollOffset() >= y && dy < 0) {
                    showTopView();
                }else {
                    hideTopView();
                }


            }
        });
    }

    public void showTopView() {
        if (topView.isClickable()) {
            return;
        }

        AnimationUtils.showFromBottom(topView);

        topView.setVisibility(View.VISIBLE);
        topView.setClickable(true);
    }

    public void hideTopView() {
        if (!topView.isClickable()) {
            return;
        }

        AnimationUtils.hideToBottom(topView);

        topView.setClickable(false);
    }

    OnScrollToTopCallback onScrollToTopCallback;

    public void setOnScrollToTopCallback(OnScrollToTopCallback onScrollToTopCallback) {
        this.onScrollToTopCallback = onScrollToTopCallback;
    }

    public interface OnScrollToTopCallback {

        void callback();

    }

}
