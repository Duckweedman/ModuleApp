package com.test.optimize.home.fragments;

import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

import com.test.optimize.R;
import com.test.modulebrary.base.BaseFragment;
import com.test.modulebrary.base.BasePresent;
import com.test.modulebrary.event.EventCenter;
import com.test.modulebrary.jsbridge.BridgeWebView;
import com.test.modulebrary.jsbridge.CallBackFunction;
import com.test.modulebrary.jsbridge.DefaultHandler;
import com.test.modulebrary.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by meijunqiang on 2017/11/6  11:06.
 * 描述：HomeFragment 首页(暂时使用WebFragment)
 */

public class HomeFragment extends BaseFragment<BasePresent> {
    private BridgeWebView mHomeWebView;
    private EditText mEt;
    private String mEvent;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresent() {

    }

    @Subscribe
    public void onEventMainThread(EventCenter event) {
        ToastUtil.toastShort(event.gsonParams.toString());
        event.callBack.onCallBack("false");
//        ToastUtil.toastShort("开始调用h5,方法名="+event);
//        String js = "javascript:" + event + "('" + "原生调用h5" + "')";//当返回载体是字符串的时候
//        mHomeWebView.loadUrl(js);
    }

    @Override
    public void initData() {
        findViewById(R.id.h5tojava).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //原生方式
//                ToastUtil.toastShort("开始调用h5,方法名=" + mEvent);
//                String js = "javascript:" + mEvent + "('" + "原生调用h5" + "')";//当返回载体是字符串的时候
//                mHomeWebView.loadUrl(js);

                mHomeWebView.callHandler("functionInJs", "来自原生的信息", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        ToastUtil.toastShort(data);
                    }
                });
            }
        });
        findViewById(R.id.h5tojava2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.toastShort("开始调用h5,方法名=qwer");
                mHomeWebView.loadUrl("javascript:qwer()");
            }
        });
        EventBus.getDefault().register(this);
        mHomeWebView = findViewById(R.id.home_web_view);
//        mHomeWebView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    ((MainActivity) mActivity).mMainViewPager.requestDisallowInterceptTouchEvent(false);
//                } else {
//                    ((MainActivity) mActivity).mMainViewPager.requestDisallowInterceptTouchEvent(true);
//                }
//                return false;
//            }
//        });
        mHomeWebView.setDefaultHandler(new DefaultHandler());
        mHomeWebView.loadUrl("http://192.168.1.127:8080?platId=1");
//        mHomeWebView.setWebViewClient(new WebViewClient() {
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {                 // Handle the error
//
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed(); // 接受证书
//            }
//
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
        mHomeWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                ToastUtil.toastShort(message);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
//                titleList.add(title);
//                if (!StringUtils.isEmpty(title) && tv_title != null) {
//                    tv_title.setText(title);
//                    tv_title.setVisibility(View.VISIBLE);
//                }
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
//                    progressBar.setVisibility(View.GONE);
                } else {
//                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        mEt = findViewById(R.id.et);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHomeWebView.loadUrl(mEt.getText().toString().trim());
            }
        });
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
