package com.itculturalfestival.smartcampus.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.itculturalfestival.smartcampus.ui.view.RecyclerDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;

/**
 * @creation_time: 2017/4/12
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 弹窗
 */

public class BaseDialog extends Dialog {

    private View convertView;

    public BaseDialog(Context context, int layoutId) {
        super(context);
        convertView = LayoutInflater.from(context).inflate(layoutId, null);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(convertView);
        //设置窗口背景透明
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public <T extends View> T getView(int viewId){
        return (T) convertView.findViewById(viewId);
    }

    public String getText(int viewId){
        return ((TextView) getView(viewId)).getText().toString();
    }

    public BaseDialog setText(int viewId, String text){
        ((TextView) getView(viewId)).setText(text);
        return this;
    }

    public BaseDialog setAdapter(Context context, int viewId, CommonAdapter adapter){
        RecyclerView recyclerView = getView(viewId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new RecyclerDecoration(getContext(), RecyclerDecoration.VERTICAL_LIST));
        }
        return this;
    }

    public BaseDialog setViewOnClickListener(int viewId, View.OnClickListener listener){
        View view = getView(viewId);
        if (view != null)
            view.setOnClickListener(listener);
        return this;
    }

    @Override
    public void show() {
        if (!isShowing()) super.show();
    }

    @Override
    public void dismiss() {
        if (isShowing()) super.dismiss();
    }

    public String getResString(int resId){
        return getContext().getResources().getString(resId);
    }
}
