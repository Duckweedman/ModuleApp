package com.test.optimize;

import com.github.mzule.activityrouter.annotation.Modules;
import com.test.modulebrary.BaseApplication;
import com.test.modulebrary.util.Logout;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by meijunqiang on 2017/11/3  14:34.
 * 描述：MyApplication
 */
@Modules({"app","login","share","shopCart","order"})
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Logout.DEBUG = BuildConfig.DEBUG;
        //TOD:meiyizhi 正式包时设置为false
        //Jpush推送
        if (BuildConfig.DEBUG) {
            JPushInterface.setDebugMode(true);
        }
        JPushInterface.init(this);
        //友盟统计
        MobclickAgent.setDebugMode(true);
    }
}
