package com.test.modulebrary.bridge;

import android.text.TextUtils;

import com.test.modulebrary.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by meijunqiang on 2017/11/8  08:59.
 * 描述：JavaLinkH5Api Js调原生接口
 */

public class JavaLinkH5Api {
    private int mTag;

    /**
     * @param tag 为调用类的唯一标识符，用于区别事件
     */
    public JavaLinkH5Api(int tag) {
        this.mTag = tag;
    }

    /**
     * 原生注入，Js统一调用入口
     *
     * @param jsonParams
     */
    @android.webkit.JavascriptInterface
    public void postMessage(String jsonParams) {
        if (!TextUtils.isEmpty(jsonParams)) {
            try {
                JSONObject json = new JSONObject(jsonParams);
                //解析h5传递信息
                String test = json.optString("test");
                ToastUtil.toastShort("test=" + test);
                EventBus.getDefault().post(json.optString("callback"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
