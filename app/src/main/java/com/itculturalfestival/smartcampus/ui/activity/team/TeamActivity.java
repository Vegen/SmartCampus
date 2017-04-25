package com.itculturalfestival.smartcampus.ui.activity.team;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.ExpandableAdapter;
import com.itculturalfestival.smartcampus.bean.team.ExpandableListBean;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
import com.itculturalfestival.smartcampus.ui.widget.ArcPopupWindow;
import com.itculturalfestival.smartcampus.ui.view.CircleImageView;
import com.itculturalfestival.smartcampus.util.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @creation_time: 2017/4/9
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 团队首页
 */

public class TeamActivity extends BaseActivity {
    private ExpandableListView elv_team;
    private LinearLayout ll_create_team;
    private CircleImageView civ_create_team;
    private TextView tv_create_team;
    private LinearLayout ll_found_team;
    private CircleImageView civ_found_team;
    private TextView tv_found_team;
    private List<ExpandableListBean> groupArray;
    private List<List<ExpandableListBean>> childArray;
    private FloatingActionButton fa_menu;
    private ArcPopupWindow arcPopupWindow;
    private int totalHeight;
    private ViewGroup.LayoutParams params;
    private Map<Integer, Boolean> map_select;
    private boolean[] item_selected;
    private final int DURATION = 400;           //动画持续时间
    private Animation anim_down_up;             //箭头向上旋转的动画
    private Animation anim_up_down;             //箭头向下旋转的动画

    @Override
    protected void onCreate() {
        initLayout(R.layout.fragment_team);
        initView();
        initData();
    }

    private void initView(){
        elv_team = getView(R.id.elv_team);
        elv_team.setDivider(getRDrawable(R.drawable.shape_divider));
        ll_create_team = getView(R.id.ll_create_team);
        civ_create_team = getView(R.id.civ_create_team);
        tv_create_team  = getView(R.id.tv_create_team);
        ll_found_team = getView(R.id.ll_found_team);
        civ_found_team = getView(R.id.civ_found_team);
        tv_found_team = getView(R.id.tv_found_team);
        fa_menu = getView(R.id.fa_menu);
    }

    private void initData(){
        //好友、群组、我的团队
        groupArray = new ArrayList<>();
        childArray = new ArrayList<>();
        ExpandableListBean listBean = null;
        listBean = new ExpandableListBean(null, "我的好友");
        groupArray.add(listBean);
        listBean = new ExpandableListBean(null, "我的群组");
        groupArray.add(listBean);
        listBean = new ExpandableListBean(null, "我的团队");
        groupArray.add(listBean);

        List<ExpandableListBean> expandableListBeen = null;

        expandableListBeen = new ArrayList<>();
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "小明1");
        expandableListBeen.add(listBean);
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "小明2");
        expandableListBeen.add(listBean);
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "小明3");
        expandableListBeen.add(listBean);
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "小明4");
        expandableListBeen.add(listBean);
        childArray.add(expandableListBeen);

        expandableListBeen = new ArrayList<>();
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "群组1");
        expandableListBeen.add(listBean);
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "群组2");
        expandableListBeen.add(listBean);
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "群组3");
        expandableListBeen.add(listBean);
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "群组4");
        expandableListBeen.add(listBean);
        childArray.add(expandableListBeen);

        /*
        ("岭师计协","http://
("岭师教协","http://
("信息学院宣传工作委员会","
("14网络班","http:/
("15数媒班" ,"http:
("信息工程学院", "http
         */

        expandableListBeen = new ArrayList<>();
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "岭师计协");
        expandableListBeen.add(listBean);
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "信息学院宣传工作委员会");
        expandableListBeen.add(listBean);
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "14网络班");
        expandableListBeen.add(listBean);
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "15数媒班");
        expandableListBeen.add(listBean);
        listBean = new ExpandableListBean("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "音乐爱好者协会");
        expandableListBeen.add(listBean);
        childArray.add(expandableListBeen);

        final ExpandableAdapter expandableAdapter = new ExpandableAdapter(getContext(), groupArray, childArray);
        elv_team.setAdapter(expandableAdapter);
        elv_team.setGroupIndicator(null);

        //初始化向上旋转的动画
        anim_down_up = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim_down_up.setDuration(DURATION);
        anim_down_up.setFillAfter(true);
        //初始化向下旋转的动画
        anim_up_down = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim_up_down.setDuration(DURATION);
        anim_up_down.setFillAfter(true);

        map_select = new HashMap<>();
        totalHeight = 0;
        item_selected = new boolean[3];
        item_selected[0] = false;
        item_selected[1] = false;
        item_selected[2] = false;
        //初始时高度
        params = elv_team.getLayoutParams();
        for (int j = 0; j < expandableAdapter.getGroupCount(); j++) {
            View listItem = expandableAdapter.getGroupView(j, false, null, elv_team);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
//            Log.e("team", "listItem.getMeasuredHeight() = " + listItem.getMeasuredHeight() + "   elv_team.getDividerHeight()" + elv_team.getDividerHeight());
        }
        params.height = totalHeight + elv_team.getDividerHeight() * (expandableAdapter.getGroupCount() - 1);
        elv_team.setLayoutParams(params);
        //点击时高度调整
        elv_team.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                ImageView iv_status = (ImageView) view.findViewById(R.id.iv_status);
                TextView textView = (TextView) view.findViewById(R.id.tv_group);
                L.e("高度调整", "点击" + i + "   getText:" + textView.getText());
                iv_status.startAnimation(item_selected[i] ? anim_up_down : anim_down_up);

                if (!item_selected[i]){
                    //未选时点击展开
                    for (int j = 0; j < expandableAdapter.getChildrenCount(i); j++) {
                        View listItem = expandableAdapter.getChildView(i,j, false, null, elv_team);
                        listItem.measure(0, 0);
                        totalHeight += listItem.getMeasuredHeight();
                        Log.e("totalHeight", "totalHeight=" + totalHeight);
                    }
                    totalHeight += elv_team.getDividerHeight() * (expandableAdapter.getChildrenCount(i) - 1);
                    Log.e("totalHeightend展开", "totalHeight=" + totalHeight);
                    params.height = totalHeight;
                    elv_team.setLayoutParams(params);
                }else {
                    //已选时点击收缩
                    for (int j = 0; j < expandableAdapter.getChildrenCount(i); j++) {
                        View listItem = expandableAdapter.getChildView(i,j, false, null, elv_team);
                        listItem.measure(0, 0);
                        totalHeight -= listItem.getMeasuredHeight();
                    }
                    totalHeight  = totalHeight - elv_team.getDividerHeight() * (expandableAdapter.getChildrenCount(i) - 1);
                    Log.e("totalHeightend收缩", "totalHeight=" + totalHeight);
                    params.height = totalHeight;
                    elv_team.setLayoutParams(params);
                }

                item_selected[i] = ! item_selected[i];
                return false;
            }
        });

        elv_teamListener();

        //创建团队
        Glide.with(getContext()).load(R.drawable.ic_create_team).into(civ_create_team);
        tv_create_team.setText("创建团队");
        //发现团队
        Glide.with(getContext()).load(R.drawable.ic_found_team).into(civ_found_team);
        tv_found_team.setText("发现团队");

        ll_create_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CreateTeamActivity.class));
            }
        });

        ll_found_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FoundTeamActivity.class));
            }
        });
    }

    private void elv_teamListener(){
        elv_team.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                if (i == 2){
                    TeamIntroduceActivity.actionStart(getContext(), 1);
                }
                return false;
            }
        });
    }

}
