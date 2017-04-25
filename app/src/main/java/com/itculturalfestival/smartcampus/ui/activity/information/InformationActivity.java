package com.itculturalfestival.smartcampus.ui.activity.information;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
import com.itculturalfestival.smartcampus.ui.view.Header;
import com.itculturalfestival.smartcampus.util.L;

/**
 * @creation_time: 2017/4/13
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 资讯详情
 */

public class InformationActivity extends BaseActivity {

    private String html;
    private WebView webView;
    private static Header header;

    @Override
    protected void onCreate() {
        initLayout(R.layout.activity_information);
        initView();
    }

    private void initView(){
        header = getView(R.id.header);
        webView = getView(R.id.wv_information);
        initWebView();
    }

    public static void setIntent(Context context, String html){
        setIntent(context, html, "资讯详情");
    }

    public static void setIntent(Context context, String html, String title){
        Intent intent = new Intent(context, InformationActivity.class);
        intent.putExtra("html", html);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    private void initWebView(){
        Intent intent = getIntent();
        header.setTitle(intent.getStringExtra("title"));
        webView.loadUrl(intent.getStringExtra("html"));
        //在本WebView上打开网页
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //自适应屏幕
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //使页面支持缩放
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
