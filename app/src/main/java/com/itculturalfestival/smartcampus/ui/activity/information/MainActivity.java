package com.itculturalfestival.smartcampus.ui.activity.information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.activity.message.ChatActivity;
import com.itculturalfestival.smartcampus.ui.base.BaseFragmentActivity;
import com.itculturalfestival.smartcampus.ui.fragment.main.AssistantFragment;
import com.itculturalfestival.smartcampus.ui.fragment.main.InformationFragment;
import com.itculturalfestival.smartcampus.ui.fragment.main.MessageFragment;
import com.itculturalfestival.smartcampus.ui.fragment.main.MineFragment;
import com.itculturalfestival.smartcampus.ui.fragment.main.TeamFragment;
import com.itculturalfestival.smartcampus.ui.view.MsgCountView;
import com.itculturalfestival.smartcampus.ui.view.NoScrollViewPager;
import com.itculturalfestival.smartcampus.ui.activity.start.LoginActivity;
import com.itculturalfestival.smartcampus.util.T;

import java.util.ArrayList;
import java.util.List;

/**
 * @creation_time: 2017/3/27
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 首页
 */

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    private boolean mIsExit;                //是否退出
    private LinearLayout ll_information;
    private ImageView iv_information;
    private TextView tv_information;
    private RelativeLayout rl_message;
    private ImageView iv_message;
    private TextView tv_message;
    private MsgCountView msgCountView;
    private LinearLayout ll_team;
    private ImageView iv_team;
    private TextView tv_team;
    private LinearLayout ll_assistant;
    private ImageView iv_assistant;
    private TextView tv_assistant;
    private LinearLayout ll_mine;
    private ImageView iv_mine;
    private TextView tv_mine;
    private NoScrollViewPager noScrollViewPager;
    private List<Fragment> fragments;
    private FragmentPagerAdapter pagerAdapter;
    private MessageFragment messageFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断sdk是否登录成功过，并没有退出和被踢，否则跳转到登陆界面
        if (!EMClient.getInstance().isLoggedInBefore()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        initLayout(R.layout.activity_main);
        initView();
    }

    private void initView(){
        ll_information = getView(R.id.ll_information);
        ll_information.setOnClickListener(this);
        iv_information = getView(R.id.iv_information);
        tv_information = getView(R.id.tv_information);
        rl_message = getView(R.id.rl_message);
        rl_message.setOnClickListener(this);
        iv_message = getView(R.id.iv_message);
        tv_message = getView(R.id.tv_message);
        msgCountView = getView(R.id.msgCountView);
        ll_team = getView(R.id.ll_team);
        ll_team.setOnClickListener(this);
        iv_team = getView(R.id.iv_team);
        tv_team = getView(R.id.tv_team);
        ll_assistant = getView(R.id.ll_assistant);
        ll_assistant.setOnClickListener(this);
        iv_assistant = getView(R.id.iv_assistant);
        tv_assistant = getView(R.id.tv_assistant);
        ll_mine = getView(R.id.ll_mine);
        ll_mine.setOnClickListener(this);
        iv_mine = getView(R.id.iv_mine);
        tv_mine = getView(R.id.tv_mine);
        selected(0);

        noScrollViewPager = (NoScrollViewPager) findViewById(R.id.noScrollViewPager);
        noScrollViewPager.setNoScroll(true);                                //设置不能滚动
        noScrollViewPager.setCurrentItem(0, false);
        fragments = new ArrayList<>();
        InformationFragment informationFragment = new InformationFragment();
        messageFragment = new MessageFragment();
        TeamFragment teamFragment = new TeamFragment();
        AssistantFragment assistantFragment = new AssistantFragment();
        MineFragment mineFragment = new MineFragment();
        fragments.add(informationFragment);
        fragments.add(messageFragment);
        fragments.add(teamFragment);
        fragments.add(assistantFragment);
        fragments.add(mineFragment);
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        noScrollViewPager.setAdapter(pagerAdapter);

        messageFragment.setConversationListItemClickListener(new MessageFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent=new Intent(getContext(),ChatActivity.class);
                //传入参数
                Bundle args=new Bundle();
                args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                args.putString(EaseConstant.EXTRA_USER_ID,conversation.conversationId());
                intent.putExtra("conversation",args);
                startActivity(intent);
            }
        });
    }

    EMMessageListener messageListener=new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            //接收到新的消息
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageRead(List<EMMessage> list) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                // refresh conversation list
                if (messageFragment != null) {
                    messageFragment.refresh();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    }

    /**
     * 点击选择了菜单哪个模块
     * @param which
     */
    private void selected(int which){
        switch (which){
            case 0://资讯
                iv_message.setImageResource(R.drawable.ic_message_normal);
                tv_message.setTextColor(getRColor(R.color.text_color_light));
                iv_team.setImageResource(R.drawable.ic_team_normal);
                tv_team.setTextColor(getRColor(R.color.text_color_light));
                iv_assistant.setImageResource(R.drawable.ic_assistant_normal);
                tv_assistant.setTextColor(getRColor(R.color.text_color_light));
                iv_mine.setImageResource(R.drawable.ic_mine_normal);
                tv_mine.setTextColor(getRColor(R.color.text_color_light));
                iv_information.setImageResource(R.drawable.ic_information_select);
                tv_information.setTextColor(getRColor(R.color.colorTheme));
                break;
            case 1://消息
                iv_message.setImageResource(R.drawable.ic_message_select);
                tv_message.setTextColor(getRColor(R.color.colorTheme));
                iv_team.setImageResource(R.drawable.ic_team_normal);
                tv_team.setTextColor(getRColor(R.color.text_color_light));
                iv_assistant.setImageResource(R.drawable.ic_assistant_normal);
                tv_assistant.setTextColor(getRColor(R.color.text_color_light));
                iv_mine.setImageResource(R.drawable.ic_mine_normal);
                tv_mine.setTextColor(getRColor(R.color.text_color_light));
                iv_information.setImageResource(R.drawable.ic_information_normal);
                tv_information.setTextColor(getRColor(R.color.text_color_light));
                break;
            case 2://团队
                iv_message.setImageResource(R.drawable.ic_message_normal);
                tv_message.setTextColor(getRColor(R.color.text_color_light));
                iv_team.setImageResource(R.drawable.ic_team_select);
                tv_team.setTextColor(getRColor(R.color.colorTheme));
                iv_assistant.setImageResource(R.drawable.ic_assistant_normal);
                tv_assistant.setTextColor(getRColor(R.color.text_color_light));
                iv_mine.setImageResource(R.drawable.ic_mine_normal);
                tv_mine.setTextColor(getRColor(R.color.text_color_light));
                iv_information.setImageResource(R.drawable.ic_information_normal);
                tv_information.setTextColor(getRColor(R.color.text_color_light));
                break;
            case 3://助手
                iv_message.setImageResource(R.drawable.ic_message_normal);
                tv_message.setTextColor(getRColor(R.color.text_color_light));
                iv_team.setImageResource(R.drawable.ic_team_normal);
                tv_team.setTextColor(getRColor(R.color.text_color_light));
                iv_assistant.setImageResource(R.drawable.ic_assistant_select);
                tv_assistant.setTextColor(getRColor(R.color.colorTheme));
                iv_mine.setImageResource(R.drawable.ic_mine_normal);
                tv_mine.setTextColor(getRColor(R.color.text_color_light));
                iv_information.setImageResource(R.drawable.ic_information_normal);
                tv_information.setTextColor(getRColor(R.color.text_color_light));
                break;
            case 4://我的
                iv_message.setImageResource(R.drawable.ic_message_normal);
                tv_message.setTextColor(getRColor(R.color.text_color_light));
                iv_team.setImageResource(R.drawable.ic_team_normal);
                tv_team.setTextColor(getRColor(R.color.text_color_light));
                iv_assistant.setImageResource(R.drawable.ic_assistant_normal);
                tv_assistant.setTextColor(getRColor(R.color.text_color_light));
                iv_mine.setImageResource(R.drawable.ic_mine_select);
                tv_mine.setTextColor(getRColor(R.color.colorTheme));
                iv_information.setImageResource(R.drawable.ic_information_normal);
                tv_information.setTextColor(getRColor(R.color.text_color_light));
                break;
        }
    }

    /**
     * 提示再按一次退出
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();
            } else {
                T.showShort(getContext(), getRString(R.string.exit_tip));
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_information:
                noScrollViewPager.setCurrentItem(0, false);
                selected(0);
                break;

            case R.id.rl_message:
                noScrollViewPager.setCurrentItem(1, false);
                selected(1);
                break;

            case R.id.ll_team:
                noScrollViewPager.setCurrentItem(2, false);
                selected(2);
                break;

            case R.id.ll_assistant:
                noScrollViewPager.setCurrentItem(3, false);
                selected(3);
                break;

            case R.id.ll_mine:
                noScrollViewPager.setCurrentItem(4, false);
                selected(4);
                break;
        }
    }
}
