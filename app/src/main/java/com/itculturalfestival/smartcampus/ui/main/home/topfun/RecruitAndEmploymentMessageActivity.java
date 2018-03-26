package com.itculturalfestival.smartcampus.ui.main.home.topfun;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.Constant;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.RecruitMessageAdapter;
import com.itculturalfestival.smartcampus.entity.RecruitAndEmployment;
import com.itculturalfestival.smartcampus.network.Url;
import com.itculturalfestival.smartcampus.utils.ItemDecoration.ListItemDecoration;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by vegen on 2018/3/24.
 * 招生信息
 */

public class RecruitAndEmploymentMessageActivity extends AppBaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private RecruitMessageAdapter recruitMessageAdapter;
    private List<RecruitAndEmployment> recruitAndEmploymentList;
    private int type;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, RecruitAndEmploymentMessageActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected BasePresenter presenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.app_layout_list;
    }

    @Override
    protected void setupUI() {
        type = getIntent().getIntExtra("type", Constant.MESSAGE_RECRUIT);
        if (type == Constant.MESSAGE_RECRUIT) {
            setTitle("招生信息");
        } else {
            setTitle("就业信息");
        }
        refreshLayout.setEnabled(false);
        recruitMessageAdapter = new RecruitMessageAdapter();
//        recruitMessageAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recruitMessageAdapter);
        recyclerView.addItemDecoration(new ListItemDecoration(10, 0, 0));
        View emptyView = View.inflate(this, R.layout.app_view_empty, null);
        recruitMessageAdapter.setEmptyView(emptyView);
        recruitMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e(tag, "url:" + recruitAndEmploymentList.get(position).getUrl());
                if (type == Constant.MESSAGE_RECRUIT) {
                    // 打开网页端
                    TopFunArticleDetailActivity.start(RecruitAndEmploymentMessageActivity.this,
                            recruitAndEmploymentList.get(position).getTitle(),
                            recruitAndEmploymentList.get(position).getUrl()
                    );
                }else {
                    // 跳转到招聘列表
                    EmploymentListActivity.start(RecruitAndEmploymentMessageActivity.this,
                            recruitAndEmploymentList.get(position).getTitle(),
                            recruitAndEmploymentList.get(position).getUrl(),
                            position
                    );
                }
            }
        });

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_slide_right);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();

    }

    @Override
    protected void initData() {
        recruitAndEmploymentList = new ArrayList<>();
        RecruitAndEmployment recruitAndEmployment = null;
        if (type == Constant.MESSAGE_RECRUIT) {
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_news, "招生资讯", Url.ZSB_URL + "/zszx/list_36.aspx");
            recruitAndEmploymentList.add(recruitAndEmployment);
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_plan, "招生计划", Url.ZSB_URL + "/zsjh/list_131.aspx");
            recruitAndEmploymentList.add(recruitAndEmployment);
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_general_rules, "招生简章", Url.ZSB_URL + "/zsjz/list_37.aspx");
            recruitAndEmploymentList.add(recruitAndEmployment);
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_constitution, "招生章程", Url.ZSB_URL + "/zszc/index_38.aspx");
            recruitAndEmploymentList.add(recruitAndEmployment);
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_policy, "招生政策", Url.ZSB_URL + "/zszcFT/list_39.aspx");
            recruitAndEmploymentList.add(recruitAndEmployment);
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_last_year, "往年录取", Url.ZSB_URL + "/wnlq/list_40.aspx");
            recruitAndEmploymentList.add(recruitAndEmployment);
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_reward, "奖助学金", Url.ZSB_URL + "/jzxj/index_41.aspx");
            recruitAndEmploymentList.add(recruitAndEmployment);
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_question, "考生问答", Url.ZSB_URL + "/kswd/index_42.aspx");
            recruitAndEmploymentList.add(recruitAndEmployment);
        } else {
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_notice, "招聘公告", Url.JYSD_URL + "/campus/index?keyword=&page=");
            recruitAndEmploymentList.add(recruitAndEmployment);
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_job_fair, "招聘会", Url.JYSD_URL + "/jobfair/index?keyword=&page=");
            recruitAndEmploymentList.add(recruitAndEmployment);
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_preaching_conference, "宣讲会", Url.JYSD_URL + "/teachin/index?keyword=&page=");
            recruitAndEmploymentList.add(recruitAndEmployment);
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_job, "岗位信息", Url.JYSD_URL + "/job/search?keyword=&d_category[0]=0&d_category[1]=100&page=");
            recruitAndEmploymentList.add(recruitAndEmployment);
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_in_school, "校内招聘", Url.JYSD_URL + "/news/index?tag=xnzp&keyword=&page=");
            recruitAndEmploymentList.add(recruitAndEmployment);
            recruitAndEmployment = new RecruitAndEmployment(R.mipmap.app_ic_out_school, "校外招聘", Url.JYSD_URL + "/news/index?tag=xwzp&keyword=&page=");
            recruitAndEmploymentList.add(recruitAndEmployment);
        }
        recruitMessageAdapter.setNewData(recruitAndEmploymentList);
    }
}
