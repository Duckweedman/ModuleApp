package com.test.modulebrary.network;

/**
 * Created by meijunqiang on 2017/11/2 0007.
 * 描述：对访问的最上层封装，过滤掉访问配置项，对外暴露统一接口调用
 * 重要：调用者应当是Present，从而回调View进行数据展示
 */

public abstract class BaseCall<T> {

    public T mService;

    public BaseCall() {
        mService = Zygote.getZygote().getApiService(getApiServiceClass());
        if (null==mService) {
            throw new RuntimeException("模块中AppCall的getApiServiceClass方法必须被复写并返回非空类型!");
        }
    }

    /**
     * 必须被子类复写，从而得到class对象
     *
     * @return
     */
    protected abstract Class<T> getApiServiceClass();
}
