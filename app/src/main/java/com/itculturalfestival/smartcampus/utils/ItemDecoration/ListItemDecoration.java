package com.itculturalfestival.smartcampus.utils.ItemDecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by vegen on 2018/3/21.
 */

public class ListItemDecoration extends RecyclerView.ItemDecoration{

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        /*item距离上20px,距离左右各16px*/
        outRect.top = 20;
        outRect.right = 16;
        outRect.left = 16;
    }
}