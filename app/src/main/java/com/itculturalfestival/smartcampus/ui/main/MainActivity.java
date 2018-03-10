package com.itculturalfestival.smartcampus.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.view.BottomTab;
import com.itculturalfestival.smartcampus.ui.view.NoScrollViewPager;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vegen on 2018/3/1.
 */

public class MainActivity extends AppBaseActivity {
    @Bind(R.id.viewpager)
    NoScrollViewPager viewpager;
//    @Bind(R.id.bottom_tab)
//    BottomTab bottomTab;
//    @Bind(R.id.statusBar)
//    View statusBar;
//    @Bind(R.id.toolbar_title)
//    TextView toolbarTitle;
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//    @Bind(R.id.toolbarLayout)
//    LinearLayout toolbarLayout;
//    @Bind(R.id.guide)
//    ImageView guide;
//    @Bind(R.id.container)
//    RelativeLayout container;

//    @Bind(R.id.viewpager)
//    NoScrollViewPager viewpager;
//    @Bind(R.id.statusBar)
//    View statusBar;
    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;
//    @Bind(R.id.ll_toolbarLayout)
//    LinearLayout llToolbarLayout;
//    @Bind(R.id.rl_contain)
//    RelativeLayout rlContain;
//    @Bind(R.id.bottom_tab)
//    BottomTab bottomTab;

    private MainFragmentPageAdapter pageAdapter;
    private int position = 0;
    private int lastPosition;

    @Override
    protected int layoutId() {
        return R.layout.app_activity_main;
    }

    private String __VIEWSTATE = "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";
    private String __EVENTVALIDATION = "/wEdAAf0CaXkU5PaT/4b0hsN+IjH2aUoMsPIebwSkRVsbn16mhpjaipFQxK02QeDxcoLIJsTxX9BozEzFobEMlGSJ4J0aIjZHlHuBMdD9QgYwFiERGOF21brLkQf0lJ2Sf7fw+jxMTBHOaHQK9KhaYAlfWMSj1iA85wzhe4o3GnxujW67FwqTloEC+AG1QHO4sowUL4=";

    @Override
    protected BasePresenter presenter() {
        return null;
    }

    @Override
    protected void setupUI() {
        ButterKnife.bind(this);
        // 透明状态栏
//        StatusBarUtils.setTransparentStatusBar(this, false);
        setTitle("智慧校园");
//        setSupportActionBar(toolbar);

        setupViewPager();
        setupBottomTab();
        lastPosition = viewpager.getCurrentItem();

        /*

        Disposable disposable = Observable.create(new ObservableOnSubscribe<Document>() {
            @Override
            public void subscribe(ObservableEmitter<Document> e) throws Exception {
//                Document doc = Jsoup.connect("http://news.lingnan.edu.cn/PreviewClass.aspx?site=0&ClassID=748200805405").get();

                Connection connection = Jsoup.connect("http://news.lingnan.edu.cn/PreviewClass.aspx?site=0&ClassID=748200805405");
                connection.data("__EVENTTARGET", "PageNavigator1$LnkBtnNext");
                connection.data("__EVENTARGUMENT", "");
                connection.data("__VIEWSTATE", __VIEWSTATE);
                connection.data("__VIEWSTATEGENERATOR", "84A8A426");
                connection.data("__EVENTVALIDATION", __EVENTVALIDATION);
                connection.data("Date", "30");
                connection.data("tags", "");
                Document document =  connection.post();

//                Map<String, String> map = new HashMap<String, String>();
//                map.put("__EVENTTARGET", "PageNavigator1$LnkBtnNext");
//                map.put("__EVENTARGUMENT", "");
//                map.put("__VIEWSTATE", __VIEWSTATE);
//                map.put("__VIEWSTATEGENERATOR", "84A8A426");
//                map.put("__EVENTVALIDATION", __EVENTVALIDATION);
//                map.put("Date", "30");
//                map.put("tags", "");

//                Connection.Response response = Jsoup.connect("http://news.lingnan.edu.cn/PreviewClass.aspx?site=0&ClassID=748200805405")
//                        .data(map)
//                        .method(Connection.Method.POST)
//                        .timeout(20000)
//                        .execute();

                e.onNext(document);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Document>() {
                    @Override
                    public void accept(Document document) throws Exception {
                        Element body = document.body();

                        Element form1 = body.getElementById("form1").getElementById("__VIEWSTATE");

                        Element newslist = body.getElementById("ListNews").getElementById("leftbox").getElementById("newslist");
                        Elements links = newslist.getElementsByTag("li");
                        String text = "";
                        for (Element link : links) {
                            Elements linkss = link.getElementsByTag("a");
                            String linkHref = linkss.get(0).attr("title");
//                            String linkText = link.text();
                            text += linkHref + "\n";
                        }
//                        tvTest.setText(text);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        tvTest.setText("cuowu");
                    }
                });
*/
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        NewsFragment newsFragment = (NewsFragment) pageAdapter.instantiateItem(viewpager, 0);

//        newsFragment.getRoot().addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (toolbarLayout != null) {
//                    toolbarLayout.setAlpha(getStatusAlpha(homeFragment));
//                }
//            }
//        });
    }

    private void setupViewPager() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(NewsFragment.getInstance());
        fragments.add(TeamFragment.getInstance());
        fragments.add(CircleFragment.getInstance());
        fragments.add(MessageFragment.getInstance());
        fragments.add(UserFragment.getInstance());

        pageAdapter = new MainFragmentPageAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(pageAdapter);
        viewpager.setOffscreenPageLimit(fragments.size());
        viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                changeToolbar(position);
                if (position == 0) {
                    // 设置成透明状态栏
                    StatusBarUtils.setTransparentStatusBar(MainActivity.this, false);
                } else {
                    // 设置状态栏为主题色
                    StatusBarUtils.setStatusBarColor(MainActivity.this, R.color.colorPrimary, false);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
    }

    private void changeToolbar(int position) {
        lastPosition = position;
//        toolbar.setVisibility(View.VISIBLE);
    }

    private void setupBottomTab() {
//        bottomTab.setData(R.menu.app_bottombar_menu);
//        bottomTab.setupViewPager(viewpager);
//        bottomTab.setOnTabClickListener(((view, itemId, position1) ->{
//            viewpager.setCurrentItem(position1, false);
//        }));
//
//        bottomTab.selectTab(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class MainFragmentPageAdapter extends FragmentStatePagerAdapter {

        List<Fragment> fragments;

        public MainFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
