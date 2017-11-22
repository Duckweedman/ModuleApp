package com.test.modulebrary.network.cache;


import com.test.modulebrary.base.BaseBean;

import io.reactivex.Observable;

/**
 * Created by meijunqiang on 2017/11/21  14:45.
 * 描述：ICache 网络三级缓存接口
 */

public interface ICache {

    <T> Observable<BaseBean<T>> get(String key, Class<T> cls);

    <T> void put(String key, T t);
}
