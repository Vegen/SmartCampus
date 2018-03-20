package com.itculturalfestival.smartcampus.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @creation_time: 2017/4/23
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 正方形的布局，去宽和高中的较大者作为边长
 */

public class SquareLayout extends LinearLayout {

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureSpec = widthMeasureSpec > heightMeasureSpec ? widthMeasureSpec : heightMeasureSpec;
        super.onMeasure(measureSpec, measureSpec);
    }
}
