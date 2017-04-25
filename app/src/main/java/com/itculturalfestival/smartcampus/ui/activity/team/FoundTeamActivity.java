package com.itculturalfestival.smartcampus.ui.activity.team;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
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
 * @describe: 发现团队
 */

public class FoundTeamActivity extends BaseActivity {

    private RecyclerView rv_team_type;
    private List<TeamTypeBean> typeList;

    @Override
    protected void onCreate() {
        initLayout(R.layout.activity_found_team);
        initView();
    }
    private void initView(){
        rv_team_type = getView(R.id.rv_team_type);
        typeList = new ArrayList<>();
        typeList.add(new TeamTypeBean("学术类", 0));
        typeList.add(new TeamTypeBean("实践类", 1));
        typeList.add(new TeamTypeBean("体育类", 2));
        typeList.add(new TeamTypeBean("艺术类", 3));
        typeList.add(new TeamTypeBean("团学组织" ,4));
        typeList.add(new TeamTypeBean("其他", 5));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_team_type.setLayoutManager(layoutManager);
        final CommonAdapter commonAdapter =  new CommonAdapter<TeamTypeBean>(this, R.layout.view_team_type, typeList) {
            @Override
            protected void convert(ViewHolder holder, TeamTypeBean teamTypeBean, int position) {
                holder.setText(R.id.tv_type, teamTypeBean.getType_name());
            }
        };
        rv_team_type.setAdapter(commonAdapter);
        rv_team_type.addItemDecoration(new RecyclerDecoration(getContext(), RecyclerDecoration.VERTICAL_LIST));
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getContext(), TeamByTypeActivity.class);
                intent.putExtra("type_number", typeList.get(position).getType_number());
                intent.putExtra("type_name", typeList.get(position).getType_name());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    public class TeamTypeBean{
        private String type_name;
        private int type_number;

        public TeamTypeBean(){}

        public TeamTypeBean(String type_name, int type_number){
            this.type_name = type_name;
            this.type_number = type_number;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public int getType_number() {
            return type_number;
        }

        public void setType_number(int type_number) {
            this.type_number = type_number;
        }
    }

}
