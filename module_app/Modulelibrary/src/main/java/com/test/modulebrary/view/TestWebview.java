package com.test.modulebrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.test.modulebrary.bridge.JavaLinkH5Api;

/**
 * Created by meijunqiang on 2017/11/8  09:16.
 * 描述：TestWebview
 */

public class TestWebview extends WebView {
    public TestWebview(Context context) {
        this(context,null);
    }

    public TestWebview(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initApi(context);
    }

    private void initApi(Context context) {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
        addJavascriptInterface(new JavaLinkH5Api(getContext().hashCode()),"JsCallJava");
    }
}
