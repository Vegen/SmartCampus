package com.itculturalfestival.smartcampus.ui.main.home.recruit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.RecruitMessageAdapter;
import com.itculturalfestival.smartcampus.entity.Recruit;
import com.itculturalfestival.smartcampus.network.Url;
import com.itculturalfestival.smartcampus.utils.ItemDecoration.ListItemDecoration;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by vegen on 2018/3/24.
 * 招生信息
 */

public class RecruitMessageActivity extends AppBaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecruitMessageAdapter recruitMessageAdapter;
    private List<Recruit> recruitList;

    public static void start(Context context){
        Intent intent = new Intent(context, RecruitMessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected BasePresenter presenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.app_activity_recruit_message;
    }

    @Override
    protected void setupUI() {
        setTitle("招生信息");
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
                RecruitArticleDetailActivity.start(RecruitMessageActivity.this, recruitList.get(position).getTitle(), recruitList.get(position).getUrl());
            }
        });

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_slide_right);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();

    }

    @Override
    protected void initData() {
        recruitList = new ArrayList<>();
        Recruit recruit= null;
        recruit = new Recruit(0, "招生资讯", Url.ZSB_URL + "/zszx/list_36.aspx");
        recruitList.add(recruit);
        recruit = new Recruit(0, "招生计划", Url.ZSB_URL + "/zsjh/list_131.aspx");
        recruitList.add(recruit);
        recruit = new Recruit(0, "招生简章", Url.ZSB_URL + "/zsjz/list_37.aspx");
        recruitList.add(recruit);
        recruit = new Recruit(0, "招生章程", Url.ZSB_URL + "/zszc/index_38.aspx");
        recruitList.add(recruit);
        recruit = new Recruit(0, "招生政策", Url.ZSB_URL + "/zszcFT/list_39.aspx");
        recruitList.add(recruit);
        recruit = new Recruit(0, "往年录取", Url.ZSB_URL + "/wnlq/list_40.aspx");
        recruitList.add(recruit);
        recruit = new Recruit(0, "奖助学金", Url.ZSB_URL + "/jzxj/index_41.aspx");
        recruitList.add(recruit);
        recruit = new Recruit(0, "考生问答", Url.ZSB_URL + "/kswd/index_42.aspx");
        recruitList.add(recruit);
        recruitMessageAdapter.setNewData(recruitList);
    }
}
