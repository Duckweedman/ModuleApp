package com.test.modulebrary.base;

import java.lang.ref.WeakReference;

/**
 * Created by meijunqiang on 2017/11/3 0011.
 * 描述：Present基类，确定销毁生命周期
 */

public class BasePresent<T> {
    private WeakReference<T> mView;

    public BasePresent(T t) {
        mView = new WeakReference<T>(t);
    }

    /**
     * 获取当前回调的视图
     *
     * @return 视图定义接口
     */
    protected T mView() {
        return mView.get();
    }

    /**
     * 应该主动跟组件绑定的生命周期方法
     */
    protected void onDestory() {
        mView = null;
    }
}
