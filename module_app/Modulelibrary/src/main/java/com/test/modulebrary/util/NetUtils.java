package com.test.modulebrary.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.test.modulebrary.BaseApplication;

/**
 * Created by meijunqiang on 2017/11/3 0008.
 * 描述：网络管理类相关
 */

public class NetUtils {
    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }
}
