package com.itculturalfestival.smartcampus.ui.main.home.recruit;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.main.home.ArticleDetailContract;
import com.itculturalfestival.smartcampus.utils.ProgressHelper;

import butterknife.Bind;

/**
 * Created by vegen on 2018/3/24.
 */

public class RecruitArticleDetailActivity extends AppBaseActivity<ArticleDetailContract.Presenter> implements ArticleDetailContract.View {

    @Bind(R.id.tv_news_title)
    TextView tvNewsTitle;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private String title;
    private String url;

    public static void start(Context context, String title, String url) {
        Intent intent = new Intent(context, RecruitArticleDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
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
        setTitle(title);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setSupportZoom(true);
        tvNewsTitle.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        // 假装在加载...
//        ProgressHelper.setProgress(progressBar, SystemUtils.getRandom(20, 40));
//        presenter().getNewsContent(url);      // todo: 只加载电脑端
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
                if (newProgress != 100) {
                    ProgressHelper.setProgress(progressBar, newProgress);
                }else {
                    ProgressHelper.setProgress(progressBar, 100);
                    progressBar.postDelayed(() -> ProgressHelper.hide(progressBar), 600);
                }
            }
        });
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
    public void showNewsContent(String newsContent) {
//        LogUtils.e(tag, "显示内容：" + newsContent);
//        webView.loadDataWithBaseURL(null, newsContent, "text/html", "utf-8", null);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(newsContent);
//                return true;
//            }
//        });
//        ProgressHelper.setProgress(progressBar, 100);
//        progressBar.postDelayed(() -> ProgressHelper.hide(progressBar), 600);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        ProgressHelper.hide(progressBar);
    }
    @Override
    protected ArticleDetailContract.Presenter presenter() {
        if (mPresenter == null)
            mPresenter = new RecruitArticleDetailPresenter(this);
        return mPresenter;
    }
}
