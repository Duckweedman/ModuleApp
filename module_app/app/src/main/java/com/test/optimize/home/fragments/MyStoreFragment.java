package com.test.optimize.home.fragments;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.test.optimize.R;
import com.test.modulebrary.base.BaseFragment;

/**
 * Created by meijunqiang on 2017/11/6  11:06.
 * 描述：MyStoreFragment 我要开店
 */

public class MyStoreFragment extends BaseFragment {
    private WebView mHomeWebView;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresent() {

    }

    @Override
    public void initData() {
        mHomeWebView = (WebView) findViewById(R.id.home_web_view);
        mHomeWebView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {                 // Handle the error

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 接受证书
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mHomeWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mHomeWebView.getSettings().setAppCacheEnabled(false);
        mHomeWebView.getSettings().setBuiltInZoomControls(false);
        mHomeWebView.getSettings().setSupportZoom(false);
        mHomeWebView.getSettings().setDisplayZoomControls(false);
        mHomeWebView.getSettings().setDefaultFontSize(40);
        mHomeWebView.getSettings().setJavaScriptEnabled(true);
        mHomeWebView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        mHomeWebView.loadUrl("http://www.sina.com.cn/");
    }

    @Override
    public void doBusiness() {

    }
}
