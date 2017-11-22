package com.test.modulebrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.test.modulebrary.util.NetUtils;
import com.test.modulebrary.util.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by meijunqiang on 2017/11/3 0015 15:09.
 * 描述：项目启动类
 */

public class BaseApplication extends Application {
    private static Context sContext;
    private static BaseApplication sApplication;    // 全局Application对象
    private WeakReference<RxFragmentActivity> topActivity;    // 正在运行Activity的弱引用
    private static String sROOT_PATH;

    /**
     * 单例方法
     *
     * @return
     */
    public static Context getAppContext() {
        return sContext;
    }

    public static BaseApplication getApplication() {
        return sApplication;
    }

    public WeakReference<RxFragmentActivity> getTopActivity() {
        return topActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        sApplication = this;
        sROOT_PATH = getDir("test", MODE_PRIVATE).getAbsolutePath();
        if (sROOT_PATH.endsWith("/")) {
            sROOT_PATH += "/";
        }
        registerActivityCallBack();
    }

    public static String getRootPath() {
        return sROOT_PATH;
    }

    /**
     * 注册并监听程序所有Activity生命周期回调，可以判断应用运行状态和当前展示Activity实例
     */
    private void registerActivityCallBack() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                topActivity = null;
                if (activity instanceof RxFragmentActivity) {
                    topActivity = new WeakReference<>((RxFragmentActivity) activity);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                //注意，当有回显数据时，执行在当前Activity的onResume之前的方法，由于当前的topActivity未被实例化，因此onResume之前的方法对于
                //MyApplication中的topActivity的操作都会失效!!!特别是当做FragmentActivity请求网络的时候
                if (!(activity instanceof RxFragmentActivity)) {
                    return;
                }
                if (topActivity == null || topActivity.get() != activity) {
                    topActivity = new WeakReference<>((RxFragmentActivity) activity);
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (topActivity != null && topActivity.get() != null && activity == topActivity.get()) {
                    topActivity = null;
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    /**
     * 绑定当前界面相关组件
     *
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<? super T, ? extends T> bindToLifecycle() {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (!NetUtils.isNetworkAvailable()) {
                                    ToastUtil.toastShort("网络未连接!!");
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(BaseApplication.this.<T>getComponentLiftcycle());
            }
        };
    }

    /**
     * 判定当前组件类型：如果遇到Fragment嵌套，周期将放弃，但不影响使用
     *
     * @param <T>
     * @return
     */
    private <T> ObservableTransformer getComponentLiftcycle() {
        List<Fragment> fragments = topActivity.get().getSupportFragmentManager().getFragments();
//        Logout.log(topActivity.get().getClass().getSimpleName());
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return ((RxFragment) fragment).<T>bindToLifecycle();
            }
        }
        return topActivity.get().<T>bindToLifecycle();
    }
}
