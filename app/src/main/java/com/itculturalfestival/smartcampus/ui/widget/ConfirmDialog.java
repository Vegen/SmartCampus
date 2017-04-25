package com.itculturalfestival.smartcampus.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.itculturalfestival.smartcampus.R;

/**
 * @creation_time: 2017/4/23
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class ConfirmDialog extends MyDialog {
    /**
     * @param context
     * msg_in  绑定信息
     * @param msg 提示信息
     */
    public ConfirmDialog(Context context, String msg ,String msg_in) {
        this(context, msg);
        //设置绑定信息
        setText(R.id.et_msg, msg_in);
    }


    /**
     *
     * @param context
     * @param msg  提示信息
     */
    public ConfirmDialog(Context context, String msg) {
        super(context, R.layout.dialog_new_talk);

        //设置提示信息
        setText(R.id.tv_msg, msg);

        //取消按钮
        setViewOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public String et_msg_string(){
        return ((EditText)getView(R.id.et_msg)).getText().toString().trim();
    }

    /**
     * 确定按钮的点击监听
     */
    public ConfirmDialog setOnConfirmClickListener(final View.OnClickListener l){
        setViewOnClickListener(R.id.btn_confirm, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.onClick(view);
                dismiss();
            }
        });
        return this;
    }

    /**
     * 设置是否显示编辑框
     */
    public ConfirmDialog setInputAble(boolean able){
        getView(R.id.et_msg).setVisibility(able ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置提示信息
     */
    public ConfirmDialog setMsg(String msg){
        setText(R.id.tv_msg, msg);
        return this;
    }



    /**
     * 设置提示信息,有标题
     */
    public ConfirmDialog setMsg(String msg, boolean showTitle){
        if (showTitle){
            TextView title = (TextView) findViewById(R.id.title);
            title.setVisibility(View.VISIBLE);
        }
        setText(R.id.tv_msg, msg);
        return this;
    }

    public ConfirmDialog setTitle(String msg, String settitle){
        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText(settitle);
        setText(R.id.tv_msg, msg);
        return this;
    }

    public ConfirmDialog onlyTitle(String settitle){
        TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        title.setText(settitle);
        return this;
    }
}
