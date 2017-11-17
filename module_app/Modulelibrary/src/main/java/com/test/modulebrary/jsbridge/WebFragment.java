package com.test.modulebrary.jsbridge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.test.modulebrary.R;
import com.test.modulebrary.base.BaseFragment;
import com.test.modulebrary.base.BasePresent;
import com.test.modulebrary.event.EventCenter;
import com.test.modulebrary.util.Logout;
import com.test.modulebrary.util.ToastUtil;
import com.test.modulebrary.view.CommonToolBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by meijunqiang on 2017/11/10  10:10.
 * 描述：WebFragment 主体是WebView的Fragment
 */

public class WebFragment extends BaseFragment<BasePresent> {

    private BridgeWebView mWebView;
    private ProgressBar mWebProgress;
    private CommonToolBar mWebToolBar;
    private boolean loadFinish;
    private String loadUrl;
    private String title;
    private WebSettings mWebSetting;
    private String userAgentString;
    private boolean hide;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_web;
    }

    @Override
    public void initPresent() {

    }

    public void setUrl(String url) {
        this.loadUrl = url;
        if (null != mWebView) {
            mWebView.loadUrl(this.loadUrl);
        }
    }

    public void setTitle(String title) {
        this.title = title;
        mWebToolBar.setVisibility(View.VISIBLE);
        mWebToolBar.setMidTitle(title);
    }

    @Override
    public void initData() {
        mWebToolBar = findViewById(R.id.web_tool_bar);
        mWebProgress = findViewById(R.id.web_progress);
        mWebView = findViewById(R.id.web_view);
    }

    @Override
    public void doBusiness() {
        EventBus.getDefault().register(this);
        mWebSetting = mWebView.getSettings();
        userAgentString = mWebSetting.getUserAgentString();
        mWebView.setDefaultHandler(new DefaultHandler());
        mWebView.setWebViewClient(new BridgeWebViewClient(mWebView) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    url = URLDecoder.decode(url, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Logout.log(url);
                if (url.startsWith("tel")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else if (url.startsWith("http")) {
                    mWebView.loadUrl(url);
                }
                if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
                    mWebView.handlerReturnData(url);
                } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
                    mWebView.flushMessageQueue();
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showLoading();
                Log.d("samuel-tag", "onPageStarted" + System.currentTimeMillis() + "");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideLoading();
                loadFinish = true;
                Log.d("samuel-tag", "onPageFinished" + System.currentTimeMillis() + "");
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.stopLoading();
                view.clearView();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                view.stopLoading();
                view.clearView();
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                ToastUtil.toastShort(message);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Logout.log("newProgress="+newProgress);
                if (newProgress == 100) {
                    mWebProgress.setVisibility(View.GONE);
                } else {
                    mWebProgress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        setUa();
        mWebView.loadUrl(loadUrl);
    }

    @Subscribe
    public void onEventMainThread(EventCenter event) {
        ToastUtil.toastShort(event.gsonParams.toString());
        event.callBack.onCallBack("原生调h5");
//        ToastUtil.toastShort("开始调用h5,方法名="+event);
//        String js = "javascript:" + event + "('" + "原生调用h5" + "')";//当返回载体是字符串的时候
//        mHomeWebView.loadUrl(js);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        hide = hidden;
        if (hidden) {
            EventBus.getDefault().unregister(this);
        } else {
            EventBus.getDefault().register(this);
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        hideLoading();
    }

    public void setUa() {
        Map<String, String> ua = new HashMap<>();
        ua.put("platId", "android");
        Gson gson = new Gson();
        if (null == mWebSetting || null == userAgentString) {
            return;
        }
        String s = userAgentString + "--[" + gson.toJson(ua) + "]--";
        Logout.log(s);
        mWebSetting.setUserAgentString(s);
    }
}
