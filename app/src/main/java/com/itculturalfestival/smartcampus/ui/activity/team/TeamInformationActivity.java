package com.itculturalfestival.smartcampus.ui.activity.team;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.RecyclerAdapter;
import com.itculturalfestival.smartcampus.entity.InformationDB;
import com.itculturalfestival.smartcampus.ui.activity.information.InformationActivity;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
import com.itculturalfestival.smartcampus.ui.view.Header;
import com.itculturalfestival.smartcampus.ui.view.RecyclerDecoration;
import com.itculturalfestival.smartcampus.util.LoginUtil;
import com.itculturalfestival.smartcampus.util.T;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @creation_time: 2017/4/12
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 团队资讯
 */

public class TeamInformationActivity extends BaseActivity {

    private RecyclerView rv_team_information;
    private Header header;
    private List<InformationDB> informationList;
    private RecyclerAdapter adapter;
//    private SwipeRefreshLayout swipe_information;
    private boolean isLoading;              //是否在加载中
    private int end;                        //temp变量

    @Override
    protected void onCreate() {
        initLayout(R.layout.activity_team_information);
        initView();
    }

    public static void actionStart(Context context, int team_id){
        actionStart(context, team_id, true);
    }

    /**
     * 暴露的跳转方法
     * @param context
     * @param team_id
     */
    public static void actionStart(Context context, int team_id, boolean admin){
        Intent intent = new Intent(context, TeamInformationActivity.class);
        intent.putExtra("team_id", team_id);
        intent.putExtra("admin", admin);
        context.startActivity(intent);
    }

    private void initView(){
        rv_team_information = getView(R.id.rv_team_information);
        header = getView(R.id.header);
        if (!getIntent().getBooleanExtra("admin", true)){
            header.setFunHide(true);
        }
        header.setTitle("资讯列表");
        header.setFunClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), WriteInformationActivity.class));
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView(){
        initInformationList();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_team_information.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(getContext(), informationList);
        rv_team_information.setAdapter(adapter);
        rv_team_information.addItemDecoration(new RecyclerDecoration(getContext(), RecyclerDecoration.VERTICAL_LIST));

        //上拉加载更多
        rv_team_information.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Log.e("RecyclerView", "StateChanged = " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                Log.e("onScrolled", "lastVisibleItemPosition+1=" + (1+lastVisibleItemPosition) + "  adapter.getItemCount()=" + adapter.getItemCount());
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    Log.d("test", "loading executed");

                        loadMore();

                }
            }
        });

        //item的点击事件
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                InformationActivity.setIntent(getContext(), "http://mp.weixin.qq.com/s/pefI6LkgKa56DiTLdib_FQ");
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    /**
     * 初始化资讯数据
     */
    private void initInformationList(){
        informationList = new ArrayList<>();
        end = 0;
        for (int i = end; i < end + 10; i ++){
            InformationDB informationBean = new InformationDB(LoginUtil.getUserId(getContext()),"协会资讯协会资讯协会资讯协会资讯协会资讯协会资讯" + (i)
                    ,"helldhsdhasd", "http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "",
                    new Date(), "协会组别" + i, 1, "类别" + 1);
            informationList.add(informationBean);
        }
        end += 10;
    }

    /**
     * 上拉加载更多
     */
    private void loadMore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = end; i < end + 10; i ++){
                            InformationDB informationBean = new InformationDB(LoginUtil.getUserId(getContext()),"协会成功举办活动协会成功举办活动" + (i + 20)
                                    ,"helldhsdhasd", "http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "",
                                    new Date(), "协会组别" + i, 1, "类别" + 1);
                            informationList.add(informationBean);
                        }
                        end += 10;
                        Log.e("RecyclerView", "load more completed, end = " + end);
                        isLoading = false;
                        adapter.notifyDataSetChanged();
//                        swipe_information.setRefreshing(false);
                        adapter.notifyItemRemoved(adapter.getItemCount());
                    }
                });
            }
        }).start();
    }
}
