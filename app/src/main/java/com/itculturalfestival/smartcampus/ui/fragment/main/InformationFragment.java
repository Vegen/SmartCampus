package com.itculturalfestival.smartcampus.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.adapter.RecyclerAdapter;
import com.itculturalfestival.smartcampus.entity.InformationDB;
import com.itculturalfestival.smartcampus.ui.activity.information.InformationActivity;
import com.itculturalfestival.smartcampus.ui.activity.information.SearchActivity;
import com.itculturalfestival.smartcampus.ui.activity.team.WriteInformationActivity;
import com.itculturalfestival.smartcampus.ui.base.BaseFragment;
import com.itculturalfestival.smartcampus.ui.view.RecyclerDecoration;
import com.itculturalfestival.smartcampus.ui.view.SearchView;
import com.itculturalfestival.smartcampus.ui.widget.ArcPopupWindow;
import com.itculturalfestival.smartcampus.util.DateUtil;
import com.itculturalfestival.smartcampus.util.GlideImageLoader;
import com.itculturalfestival.smartcampus.util.L;
import com.itculturalfestival.smartcampus.util.LoginUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @creation_time: 2017/4/22
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 资讯
 */

public class InformationFragment extends BaseFragment {

    private View view;
    private Banner banner;                  //轮播控件
    private List<Integer> images;           //temp轮播图
    private List<String> titles;            //temp轮播配文
    private List<InformationDB> informationList;
    private RecyclerView rv_information;
    private SwipeRefreshLayout swipe_information;
    private RecyclerAdapter adapter;
    private boolean isLoading;              //是否在加载中
    private int end;                        //temp变量
    private FloatingActionButton fa_menu;
    private ArcPopupWindow arcPopupWindow;
    private ImageView iv_search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_information, null);
        setView(view);
        initView();
        initData();
        return view;
    }

    private void initView(){
        banner = getView(R.id.banner);
        iv_search = getView(R.id.iv_search);
        rv_information = getView(R.id.rv_information);
        swipe_information = getView(R.id.swipe_information);
        fa_menu = getView(R.id.fa_menu);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
    }

    private void initData(){
        initBanner();
        initRecyclerView();
        initSwipe();
        initMenu();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();     //开始轮播
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();      //结束轮播
    }

    /**
     * 初始化菜单
     */
    private void initMenu(){
        fa_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (arcPopupWindow == null) {
//                arcPopupWindow = new ArcPopupWindow(getContext(), getActivity());
////                }
//                arcPopupWindow.show();
                startActivity(new Intent(getContext(), WriteInformationActivity.class));
            }
        });
    }

    private void initSwipe(){
        swipe_information.setColorSchemeResources(R.color.colorTheme);
        swipe_information.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshInformation();
            }
        });
    }

    /**
     * 下拉刷新数据
     */
    private void refreshInformation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 10; i >=0; i --){
                            InformationDB informationBean = new InformationDB(LoginUtil.getUserId(getContext()), (i) + "信息学院成功举办活动" + (i)
                                    ,"helldhsdhasd", "http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "",
                                    new Date(), "信息学院班级" + i, 1, "体育类");
                            informationList.add(0, informationBean);
                        }
                        adapter.notifyDataSetChanged();
//                        adapter.notifyItemRemoved(adapter.getItemCount());
                        swipe_information.setRefreshing(false);
                    }
                });
            }
        }).start();
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = end; i < end + 10; i ++){
                            L.e("上拉加载更多", "" + DateUtil.getBeforeHour(new Date(),i + 20));
                            InformationDB informationBean = new InformationDB(LoginUtil.getUserId(getContext()),"音乐协会专场表演音乐协会专场表演音乐协会专场表演" + (i + 20)
                                    ,"helldhsdhasd", "http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "",
                                    DateUtil.getBeforeHour(new Date(),i + 20), "音乐爱好者协会钢琴组", 1, "音乐类");
                            informationList.add(informationBean);
                        }
                        end += 10;
                        Log.e("RecyclerView", "load more completed, end = " + end);
                        isLoading = false;
                        adapter.notifyDataSetChanged();
                        swipe_information.setRefreshing(false);
                        adapter.notifyItemRemoved(adapter.getItemCount());
                    }
                });
            }
        }).start();
    }

    private void initRecyclerView(){
        initInformationList();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_information.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(getContext(), informationList);
        rv_information.setAdapter(adapter);
        rv_information.addItemDecoration(new RecyclerDecoration(getContext(), RecyclerDecoration.VERTICAL_LIST));

        //上拉加载更多
        rv_information.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    boolean isRefreshing = swipe_information.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        loadMore();
                    }
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
            InformationDB informationBean = new InformationDB(LoginUtil.getUserId(getContext()),"岭南师范学院学院" + (i) + "完满召开考研动员大会" + i
                    ,"helldhsdhasd", "http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg", "",
                    DateUtil.getBeforeHour(new Date(),i), "岭南师范学院学院" + i, 1, "体育类");
            informationList.add(informationBean);
        }
        end += 10;
    }

    /**
     * 初始化轮播图
     */
    private void initBanner(){
        images = new ArrayList<>();
        images.add(R.mipmap.banner1);
        images.add(R.mipmap.banner2);
        images.add(R.mipmap.banner3);
        images.add(R.mipmap.banner4);
        images.add(R.mipmap.banner5);
        titles = new ArrayList<>();
        titles.add("音乐协会专场表演");
        titles.add("摄影协会招新");
        titles.add("读书协会讲座");
        titles.add("话剧协会户外拓展活动");
        titles.add("大学生记者团出游调研");
//        informationBeanList = new ArrayList<>();
//        for (int i = 0; i < 5; i ++){
//            InformationDB informationBean = new InformationDB();
//            informationBean.setTitle("");
//            informationBean.setIllustration("");
//        }

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(4000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        //banner的点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                InformationActivity.setIntent(getContext(), "http://mp.weixin.qq.com/s/pefI6LkgKa56DiTLdib_FQ");
            }
        });
    }

}
