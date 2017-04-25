package com.itculturalfestival.smartcampus.ui.activity.team;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
import com.itculturalfestival.smartcampus.ui.view.CircleImageView;
import com.itculturalfestival.smartcampus.ui.view.RecyclerDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @creation_time: 2017/4/10
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 根据类型发现团队
 */

public class TeamByTypeActivity extends BaseActivity {

    private RecyclerView rv_team_list;
    private List<TeamListBean> teamList;

    @Override
    protected void onCreate() {
        initLayout(R.layout.activity_found_team_by_type);
        rv_team_list = getView(R.id.rv_team_list);
        teamList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_team_list.setLayoutManager(layoutManager);
        String head = "http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg";
        Intent intent = getIntent();
        for (int i = 0; i < 10; i ++ ){
            teamList.add(new TeamListBean(i, head, intent.getStringExtra("type_name") + (i + 1)));
        }
        CommonAdapter commonAdapter = new CommonAdapter<TeamListBean>(this, R.layout.view_item_team_list, teamList) {
            @Override
            protected void convert(ViewHolder holder, TeamListBean teamListBean, int position) {
                Glide.with(getContext()).load(teamListBean.getHead()).into((CircleImageView)holder.getView(R.id.civ_team_head));
                holder.setText(R.id.tv_team_name, teamListBean.getTeam_name());
            }
        };
        rv_team_list.setAdapter(commonAdapter);
        rv_team_list.addItemDecoration(new RecyclerDecoration(this, RecyclerDecoration.VERTICAL_LIST));
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                TeamIntroduceActivity.actionStart(getContext(), teamList.get(position).team_id, false);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }

    public class TeamListBean{
        private String head;
        private String team_name;
        private int team_id;
        public TeamListBean(){}
        public TeamListBean(int team_id, String head, String team_name){
            this.team_id = team_id;
            this.head = head;
            this.team_name = team_name;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getTeam_name() {
            return team_name;
        }

        public void setTeam_name(String team_name) {
            this.team_name = team_name;
        }

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }
    }
}
