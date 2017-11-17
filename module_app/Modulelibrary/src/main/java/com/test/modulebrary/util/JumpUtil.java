package com.test.modulebrary.util;

import android.app.Activity;
import android.content.Context;

import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.Routers;
import com.test.modulebrary.BuildConfig;

import java.util.HashMap;

/**
 * Created by meijunqiang on 2017/11/3  19:02.
 * 描述：JumpUtil 路由跳转中转
 */

public class JumpUtil {

    public static final String MAIN = "main";
    public static final String LOGIN = "login";
    public static final String ORDER_CONFIRM = "order_confirm";

    public static void open(Context context, String url) {
        Routers.open(context, BuildConfig.SCHEME + "://" + url);
    }

    public static void open(Context context, String url, RouterCallback callback) {
        Routers.open(context, BuildConfig.SCHEME + "://" + url, callback);
    }

    public static void open(Context context, String url, HashMap<String, Object> map) {
        Routers.open(context, BuildConfig.SCHEME + "://" + url + getUrlParamsByMap(map));
    }

    public static void openForResult(Context context, String url, HashMap<String, Object> map, int requestCode) {
        Routers.openForResult((Activity) context, BuildConfig.SCHEME + "://" + url + getUrlParamsByMap(map), requestCode);
    }

    /**
     * 将map转换成url
     *
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(HashMap<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("?");
        for (HashMap.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
