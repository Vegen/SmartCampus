package com.itculturalfestival.smartcampus.ui.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
import com.itculturalfestival.smartcampus.util.APPConfig;
import com.itculturalfestival.smartcampus.util.LoginUtil;
import com.itculturalfestival.smartcampus.util.SPUtil;

/**
 * @creation_time: 2017/4/11
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class MessageActivity extends BaseActivity implements View.OnClickListener {

    private Button btnStartChat;
    private Button btnListChat;
    private EditText editText;
    private TextView userTV;
    private String userName;
    private String[] avatar={"http://img5.duitang.com/uploads/item/201507/21/20150721172011_mGYkh.thumb.224_0.jpeg",
            "http://www.qqxoo.com/uploads/allimg/160208/19291Q227-3.jpg",
            "http://www.feizl.com/upload2007/2014_02/1402261732574111.jpg",
            "http://img6.itiexue.net/1314/13143390.jpg",
            "http://img5q.duitang.com/uploads/item/201505/26/20150526033548_NjZxS.thumb.224_0.jpeg",
            "http://www.qqxoo.com/uploads/allimg/170314/1423145B3-6.jpg",
            "http://diy.qqjay.com/u2/2012/1015/ce912cbb8f78ab9f77846dac2797903b.jpg",
            "http://www.qqxoo.com/uploads/allimg/170314/1423145501-4.jpg",
            "http://diy.qqjay.com/u2/2014/1208/ac9aa749faa68eecd84ed14b2da0f9e3.jpg",
            "http://tupian.qqjay.com/tou2/2017/0120/39b35eed7d7000fc214d3f5198032f11.jpg"};

    @Override
    protected void onCreate() {
        setContentView(R.layout.activity_new_talk);

//        Intent intent=getIntent();

        btnStartChat= getView(R.id.startChat);
        btnListChat= getView(R.id.list_chat);
        editText= getView(R.id.receiver_id);
        userTV= getView(R.id.user_text_view);

//        if (intent!=null){
            userName= LoginUtil.getTel(getContext());
            userTV.setText("当前登录用户:"+userName);
//        }


        btnStartChat.setOnClickListener(this);
        btnListChat.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startChat :
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    startChat();
                }
                break;
            case R.id.list_chat :
                openListChat();
                break;
        }

    }


    private void startChat(){

        //设置要发送出去的昵称
        SPUtil.setParam(this,APPConfig.USER_NAME,userName);
        //设置要发送出去的头像
        SPUtil.setParam(this, APPConfig.USER_HEAD_IMG,avatar[(int)(10.0*Math.random())]);

        Intent intent=new Intent(this,ChatActivity.class);
        //传入参数
        Bundle args=new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID,editText.getText().toString());
        intent.putExtra("conversation",args);

        startActivity(intent);
    }

    private void openListChat(){
        startActivity(new Intent(this,ConversationListActivity.class));
    }
}

