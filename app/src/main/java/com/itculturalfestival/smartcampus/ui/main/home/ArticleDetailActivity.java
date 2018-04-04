package com.itculturalfestival.smartcampus.ui.main.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.network.Url;
import com.itculturalfestival.smartcampus.utils.ShareUtils;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.umeng.socialize.UMShareAPI;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import butterknife.Bind;

/**
 * Created by vegen on 2018/3/21.
 * 文章详情
 */

public class ArticleDetailActivity extends AppBaseActivity<ArticleDetailContract.Presenter> implements ArticleDetailContract.View {

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    @Bind(R.id.tv_news_title)
    TextView tvNewsTitle;

    private String newsTitle;
    private String newsUrl;

    public static void start(Context context, String newsTitle, String newsUrl) {
        Intent intent = new Intent();
        intent.putExtra("newsTitle", newsTitle);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (!TextUtils.isEmpty(newsUrl)) {
                    if(Build.VERSION.SDK_INT>=23){
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
                        ActivityCompat.requestPermissions(this,mPermissionList,123);
                    }

                    ShareUtils.shareWeb(this, Url.ROOT_URL + newsUrl, newsTitle, newsTitle + "...", "", R.mipmap.ic_logo);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
    }

    @Override
    protected int layoutId() {
        return R.layout.app_activity_article_detail;
    }

    @Override
    protected void setupUI() {
        newsTitle = getIntent().getStringExtra("newsTitle");
        newsUrl = getIntent().getStringExtra("newsUrl");
        setTitle(newsTitle);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setSupportZoom(true);
        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.startRefresh();
    }

    @Override
    protected void initData() {
        presenter().getNewsContent(Url.ROOT_URL + newsUrl);
    }

    @Override
    protected ArticleDetailContract.Presenter presenter() {
        if (mPresenter == null)
            mPresenter = new ArticleDetailPresenter(this);
        return mPresenter;
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
        UMShareAPI.get(this).release();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showNewsContent(String newsContent) {
        LogUtils.e(tag, "显示内容：" + newsContent);
        tvNewsTitle.setText(newsTitle);
        webView.loadDataWithBaseURL(null, newsContent, "text/html", "utf-8", null);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadDataWithBaseURL(null, newsContent, "text/html", "utf-8", null);
                return true;
            }
        });
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
