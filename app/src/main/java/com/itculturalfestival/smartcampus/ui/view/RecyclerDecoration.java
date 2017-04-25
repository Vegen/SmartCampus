package com.itculturalfestival.smartcampus.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @creation_time: 2017/4/7
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: Recycler的分割线
 */

public class RecyclerDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    public RecyclerDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
//        Log.v("recyclerview - itemdecoration", "onDraw()");

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}

//public class RecyclerDecoration extends RecyclerView.ItemDecoration {
//    private Context mContext;
//    private Drawable mDivider;
//    private int mOrientation;
//    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
//    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
//
//    //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
//    public static final int[] ATRRS  = new int[]{
//            android.R.attr.listDivider
//    };
//
//    public RecyclerDecoration(Context context, int orientation) {
//        this.mContext = context;
//        final TypedArray ta = context.obtainStyledAttributes(ATRRS);
//        this.mDivider = ta.getDrawable(0);
//        ta.recycle();
//        setOrientation(orientation);
//    }
//
//    //设置屏幕的方向
//    public void setOrientation(int orientation){
//        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST){
//            throw new IllegalArgumentException("invalid orientation");        }        mOrientation = orientation;
//    }
//
//    @Override
//    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        if (mOrientation == HORIZONTAL_LIST){
//            drawVerticalLine(c, parent, state);
//        }else {
//            drawHorizontalLine(c, parent, state);
//        }
//    }
//
//    //画横线, 这里的parent其实是显示在屏幕显示的这部分
//    public void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state){
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();
//        final int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++){
//            final View child = parent.getChildAt(i);
//
//            //获得child的布局信息
//            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
//            final int top = child.getBottom() + params.bottomMargin;
//            final int bottom = top + mDivider.getIntrinsicHeight();
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//            //Log.d("wnw", left + " " + top + " "+right+"   "+bottom+" "+i);
//        }
//    }
//
//    //画竖线
//    public void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state){
//        int top = parent.getPaddingTop();
//        int bottom = parent.getHeight() - parent.getPaddingBottom();
//        final int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++){
//            final View child = parent.getChildAt(i);
//
//            //获得child的布局信息
//            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
//            final int left = child.getRight() + params.rightMargin;
//            final int right = left + mDivider.getIntrinsicWidth();
//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
//        }
//    }
//
//    //由于Divider也有长宽高，每一个Item需要向下或者向右偏移
//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        if(mOrientation == HORIZONTAL_LIST){
//            //画横线，就是往下偏移一个分割线的高度
//            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
//        }else {
//            //画竖线，就是往右偏移一个分割线的宽度
//            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
//        }
//    }
//}
