package com.test.modulebrary.network;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.test.modulebrary.BaseApplication;
import com.test.modulebrary.base.BaseActivity;
import com.test.modulebrary.base.BaseBean;
import com.test.modulebrary.base.BaseFragment;
import com.test.modulebrary.util.Logout;
import com.test.modulebrary.util.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by meijunqiang on 2017/11/2 0007.
 * 描述：Present层中访问的回调接口
 */

public class BaseApiSubscriber<T> implements Observer<BaseBean<T>> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    /**
     * @param tBaseBean 对访问是否成功code的统一处理
     */
    @Override
    public void onNext(@NonNull BaseBean<T> tBaseBean) {
        if (tBaseBean.isSuccess()) {
            onHandleSuccess(tBaseBean.data);
            onHandleSuccess(tBaseBean);
        } else {
            onHandleError(tBaseBean.message);
        }
    }

    protected void onHandleSuccess(T t) {

    }

    protected void onHandleSuccess(BaseBean<T> baseBean) {
    }

    /**
     * @param errorStr 请求失败（两种情况：code异常或访问异常，这里是code异常）
     */
    protected void onHandleError(String errorStr) {
        if (TextUtils.isEmpty(errorStr)) {
            errorStr = "当前无数据!";
        }
        ToastUtil.toastShort(errorStr);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Logout.log("onError:" + e);
    }

    @Override
    public void onComplete() {
        if (BaseApplication.getApplication().getTopActivity()==null) {
            return;
        }
        RxFragmentActivity topActivity = BaseApplication.getApplication().getTopActivity().get();
        if (topActivity instanceof BaseActivity) {
            ((BaseActivity) topActivity).hideWaitDialog();
            List<Fragment> fragments = topActivity.getSupportFragmentManager().getFragments();
            if (fragments != null && fragments.size() > 0) {
                for (Fragment fragment : fragments) {
                    if (fragment != null && fragment.isVisible() && fragment instanceof BaseFragment) {
                        ((BaseFragment) fragment).hideLoading();
                    }
                }
            }
        }

    }
}
