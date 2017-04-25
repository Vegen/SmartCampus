package com.itculturalfestival.smartcampus.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * @creation_time: 2017/4/22
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 显示消息个数的圆点
 */

public class MsgCountView extends View {

    /**
     * 设置消息数量
     */
    public void setMsgCount(int msgCount){
        this.msgCount = msgCount;
        invalidate();
    }

    /**
     * 获取消息数量
     */
    public int getMsgCount(){
        return msgCount;
    }

    private Paint mPaint;       //画笔
    private int msgCount;       //消息个数

    public MsgCountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //根据控件的大小调整字体的大小
        mPaint.setTextSize(w / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //消息数量为0时不绘制
        if (msgCount <= 0) return;

        int width = getWidth();
        int height = getHeight();

        //画圆形背景
        mPaint.setColor(Color.RED);
        canvas.drawCircle(width / 2, height / 2, width / 2, mPaint);

        //画消息个数的文字
        String s = msgCount + "";
        if (msgCount > 99) s = "99+";

        //统一字符大小
        StringBuffer sb = new StringBuffer(s);
        for (int i = 0; i < sb.length(); i++){
            sb.replace(i, i + 1, "0");
        }

        mPaint.setColor(Color.WHITE);
        Rect bound = new Rect();
        mPaint.getTextBounds(sb.toString(), 0, sb.length(), bound);
        canvas.drawText(s, (width - bound.width()) / 2, (height + bound.height()) / 2, mPaint);
    }
}
