package com.itculturalfestival.smartcampus.ui.main.home.topfun;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.Constant;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.main.home.ArticleDetailContract;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import butterknife.Bind;

/**
 * Created by vegen on 2018/3/24.
 */

public class TopFunArticleDetailActivity extends AppBaseActivity {

    @Bind(R.id.tv_news_title)
    TextView tvNewsTitle;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private String title;
    private String url;
//    private int type;

    public static void start(Context context, String title, String url) {
        Intent intent = new Intent(context, TopFunArticleDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
//        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int layoutId() {
        return R.layout.app_activity_article_detail;
    }

    @Override
    protected void setupUI() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
//        type = getIntent().getIntExtra("type", Constant.MESSAGE_RECRUIT);
        setTitle(title);

        refreshLayout.setEnableLoadmore(false);
        refreshLayout.startRefresh();

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        // 强制只加载网页端
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
//        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        //支持获取手势焦点
        webView.requestFocusFromTouch();
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setDomStorageEnabled(true);
        tvNewsTitle.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100 && refreshLayout != null){
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.finishRefreshing();
                            refreshLayout.setEnableRefresh(false);
                            refreshLayout.setEnabled(false);
                            refreshLayout.setHeaderHeight(0);
                        }
                    }, 600);

                }
            }
        });
    }

    @Override
    protected BasePresenter presenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解决webView带来的内存泄漏
        if (webView != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView!= null && webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu_article, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (!TextUtils.isEmpty(url)) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        String[] mPermissionList = new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.READ_LOGS,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.SET_DEBUG_APP,
                                Manifest.permission.SYSTEM_ALERT_WINDOW,
                                Manifest.permission.GET_ACCOUNTS,
                                Manifest.permission.WRITE_APN_SETTINGS
                        };
                        ActivityCompat.requestPermissions(this, mPermissionList, 123);
                    }

//                    ShareUtils.shareWeb(this, Url.ROOT_URL + newsUrl, newsTitle, newsTitle + "...", "", R.mipmap.ic_logo);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hideLoading(boolean isFail) {
        super.hideLoading(isFail);
        if (isFail) {
            if (refreshLayout == null) return;
            refreshLayout.postDelayed(() -> {
                refreshLayout.finishRefreshing();
                refreshLayout.setEnableRefresh(false);
            }, 600);
        }else {
            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    if (newProgress == 100 && refreshLayout != null){
                        refreshLayout.finishRefreshing();
                        refreshLayout.setEnableRefresh(false);
                    }
                }
            });
        }
    }
}
