package com.itculturalfestival.smartcampus.ui.main.home;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;

import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;

/**
 * Created by vegen on 2018/3/21.
 * 文章详情
 */

public class ArticleDetailActivity extends AppBaseActivity {

    public static void start(Context context, String newsUrl){
        Intent intent = new Intent();
        intent.putExtra("newsUrl", newsUrl);
        intent.setClass(context, ArticleDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu_article, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected BasePresenter presenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.app_activity_article_detail;
    }

    @Override
    protected void setupUI() {

    }

    @Override
    protected void initData() {

    }
}
