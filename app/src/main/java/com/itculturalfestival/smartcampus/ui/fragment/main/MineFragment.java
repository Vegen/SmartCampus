package com.itculturalfestival.smartcampus.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.TeamDB;
import com.itculturalfestival.smartcampus.ui.activity.mine.MyDataActivity;
import com.itculturalfestival.smartcampus.ui.activity.mine.SettingActivity;
import com.itculturalfestival.smartcampus.ui.activity.team.TeamInformationActivity;
import com.itculturalfestival.smartcampus.ui.activity.team.TeamIntroduceActivity;
import com.itculturalfestival.smartcampus.ui.base.BaseFragment;
import com.itculturalfestival.smartcampus.ui.view.CircleImageView;
import com.itculturalfestival.smartcampus.ui.view.RecyclerDecoration;
import com.itculturalfestival.smartcampus.ui.widget.ArcPopupWindow;
import com.itculturalfestival.smartcampus.util.LoginUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @creation_time: 2017/4/22
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 我的
 */

public class MineFragment extends BaseFragment {
    private View view;
//    private RecyclerView rv_team_list;
//    private List<TeamDB> teamList;
    private LinearLayout my_data;
    private LinearLayout ll_collection;
    private LinearLayout ll_my_information;
    private LinearLayout ll_setting;
    private CircleImageView civ_head;
    private TextView tv_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, null);
        setView(view);
        initView();
        initData();
        return view;
    }

    private void initView(){
//        rv_team_list = getView(R.id.rv_team_list);
        ll_setting = getView(R.id.ll_setting);
        ll_collection = getView(R.id.ll_collection);
        ll_my_information = getView(R.id.ll_my_information);
        my_data = getView(R.id.my_data);
        civ_head = getView(R.id.civ_head);
        tv_name = getView(R.id.tv_name);
    }

    private void initData(){
        Glide.with(getContext()).load("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg").into(civ_head);
        tv_name.setText(LoginUtil.getName(getContext()));
//        initRecyclerView();

        my_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyDataActivity.class));
            }
        });

        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });

        ll_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeamInformationActivity.actionStart(getContext(), 1);
            }
        });

        ll_my_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeamInformationActivity.actionStart(getContext(), 1);
            }
        });
    }

//    private void initRecyclerView(){
//        teamList = new ArrayList<>();
//        teamList.add(new TeamDB("岭师计协","http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg","",0,""));
//        teamList.add(new TeamDB("岭师教协","http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg","",1,""));
//        teamList.add(new TeamDB("信息学院宣传工作委员会","http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg" ,"",2,""));
//        teamList.add(new TeamDB("14网络班","http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg" ,"",3,""));
//        teamList.add(new TeamDB("15数媒班" ,"http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg","",4,""));
//        teamList.add(new TeamDB("音乐爱好者协会", "http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg","",5,""));
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rv_team_list.setLayoutManager(layoutManager);
//        final CommonAdapter commonAdapter =  new CommonAdapter<TeamDB>(getContext(), R.layout.view_team_type, teamList) {
//            @Override
//            protected void convert(ViewHolder holder, TeamDB teamBean, int position) {
//                holder.setText(R.id.tv_type, String.valueOf(teamBean.getName()));
//            }
//        };
//        rv_team_list.setAdapter(commonAdapter);
//        rv_team_list.addItemDecoration(new RecyclerDecoration(getContext(), RecyclerDecoration.VERTICAL_LIST));
//        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                TeamIntroduceActivity.actionStart(getContext(), teamList.get(position).getId());
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                return false;
//            }
//        });
//    }

}
