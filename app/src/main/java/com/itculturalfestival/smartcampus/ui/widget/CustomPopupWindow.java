package com.itculturalfestival.smartcampus.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * @creation_time: 2017/4/8
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 自定义弹窗
 */

public class CustomPopupWindow extends PopupWindow {
    private View convertView;       //内容布局
    private Context context;

    /**
     * @param context
     * @param convertViewId 内容布局
     */
    public CustomPopupWindow(Context context, int convertViewId){
        super(LayoutInflater.from(context).inflate(convertViewId, null));
        this.context = context;
        this.convertView = getContentView();
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE); //防止弹窗被虚拟按键挡住
    }

    /**
     * 设置宽度
     */
    public CustomPopupWindow setWindowWidth(int width){
        this.setWidth(width);
        return this;
    }

    /**
     * 设置高度
     */
    public CustomPopupWindow setWindowHeight(int height){
        this.setHeight(height);
        return this;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        if (!isShowing()) {
            super.showAtLocation(parent, gravity, x, y);
        }
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    protected Context getContext(){
        return context;
    }

    /**
     * 获取View
     */
    public <T extends View> T getView(int viewId){
        return (T) convertView.findViewById(viewId);
    }

    /**
     * 设置TextView
     */
    public CustomPopupWindow setText(int viewId, String text){
        ((TextView) getView(viewId)).setText(text);
        return this;
    }

    /**
     * 设置ImageView
     */
    public CustomPopupWindow setImage(int viewId, Bitmap bitmap){
        ((ImageView) getView(viewId)).setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置ImageView
     */
    public CustomPopupWindow setImage(int viewId, int resourceId){
        ((ImageView) getView(viewId)).setImageResource(resourceId);
        return this;
    }

    /**
     * 设置ImageView
     */
    public CustomPopupWindow setImage(int viewId, Drawable drawable){
        ((ImageView) getView(viewId)).setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置ListView和GridView的适配器
     */
    public CustomPopupWindow setAdapter(int viewId, Adapter adapter){
        ListView listView = (ListView) getView(viewId);
        if (listView != null) {
            listView.setAdapter((ListAdapter) adapter);
        }
        return this;
    }

    /**
     * 设置ListView和GridView的适配器
     * @param maxScreenPart 最多可占用的屏幕高度百分比
     */
    public CustomPopupWindow setAdapter(int viewId, Adapter adapter, double maxScreenPart){
        ListView listView = (ListView) getView(viewId);
        if (listView != null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            int maxHeight = (int) (wm.getDefaultDisplay().getHeight() * maxScreenPart);

            //统计listView中所有子view的总高度
            int height = 0;
            for (int i = 0; i < adapter.getCount(); i++){
                View itemView = adapter.getView(i, null, listView);
                itemView.measure(0, 0);
                height += itemView.getMeasuredHeight();
            }

            if (height > maxHeight) {
                params.height = maxHeight;
            }
            listView.setAdapter((ListAdapter) adapter);
        }
        return this;
    }

    /**
     * 设置点击监听
     */
    public CustomPopupWindow setViewOnClickListener(int viewId, View.OnClickListener listener){
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    public CustomPopupWindow setOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener){
        ((ListView) getView(viewId)).setOnItemClickListener(listener);
        return this;
    }

    public CustomPopupWindow setOnItemLongClickListener(int viewId, AdapterView.OnItemLongClickListener listener){
        ((ListView) getView(viewId)).setOnItemLongClickListener(listener);
        return this;
    }

    public View.OnTouchListener getDefaultTouchListener(){
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return true;
            }
        };
    }
    public Drawable getRDrawable(int id){
        return ContextCompat.getDrawable(context, id);
    }
    public int getRColor(int id){
        return ContextCompat.getColor(context, id);
    }
    public String getResString(int resId){
        return getContext().getResources().getString(resId);
    }
}
