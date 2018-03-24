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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itculturalfestival.smartcampus.AppBaseActivity;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.network.Url;
import com.itculturalfestival.smartcampus.utils.ProgressHelper;
import com.itculturalfestival.smartcampus.utils.ShareUtils;
import com.itculturalfestival.smartcampus.utils.ToastUtils;
//import com.umeng.socialize.ShareAction;
//import com.umeng.socialize.UMShareAPI;
//import com.umeng.socialize.UMShareListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.tencent.tauth.IUiListener;
//import com.tencent.tauth.Tencent;
//import com.tencent.tauth.UiError;
import com.vegen.smartcampus.baseframework.utils.LogUtils;
import com.vegen.smartcampus.baseframework.utils.SystemUtils;

import butterknife.Bind;

/**
 * Created by vegen on 2018/3/21.
 * 文章详情
 */

public class ArticleDetailActivity extends AppBaseActivity<ArticleDetailContract.Presenter> implements ArticleDetailContract.View {

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
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

//                    ShareUtils.shareWeb(this, Url.ROOT_URL + newsUrl, newsTitle, newsTitle + "...", "", R.mipmap.ic_logo);
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

//    Tencent mTencent;

    @Override
    protected void setupUI() {
//        mTencent = Tencent.createInstance("1106797634", this.getApplicationContext());
//        baseUiListener = new BaseUiListener();
//        mTencent.login(this, "", baseUiListener);


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
    }

//    BaseUiListener baseUiListener;

//    private class BaseUiListener implements IUiListener{
//
//        @Override
//        public void onComplete(Object o) {
//            LogUtils.e(tag, "onComplete");
//        }
//
//        @Override
//        public void onError(UiError uiError) {
//            LogUtils.e(tag, "onError" + uiError.errorDetail);
//        }
//
//        @Override
//        public void onCancel() {
//            LogUtils.e(tag, "onCancel");
//        }
//    }

    @Override
    protected void initData() {
        // 假装在加载...
        ProgressHelper.setProgress(progressBar, SystemUtils.getRandom(20, 40));
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//        Tencent.onActivityResultData(requestCode,resultCode,data,listener);
    }

    @Override
    public void showNewsContent(String newsContent) {
        LogUtils.e(tag, "显示内容：" + newsContent);
        tvNewsTitle.setText(newsTitle);
        webView.loadDataWithBaseURL(null, newsContent, "text/html", "utf-8", null);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(newsContent);
                return true;
            }
        });
        ProgressHelper.setProgress(progressBar, 100);
        progressBar.postDelayed(() -> ProgressHelper.hide(progressBar), 600);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        ProgressHelper.hide(progressBar);
    }
}
