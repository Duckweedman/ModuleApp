package com.test.modulebrary.network;

import com.test.modulebrary.base.BaseBean;
import com.test.modulebrary.base.GsonBaseBean;
import com.test.modulebrary.network.cache.DiskCache;
import com.test.modulebrary.network.cache.ICache;
import com.test.modulebrary.network.cache.MemoryCache;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by meijunqiang on 2017/11/2 0007.
 * 描述：对访问的最上层封装，过滤掉访问配置项，对外暴露统一接口调用
 * 重要：调用者应当是Present，从而回调View进行数据展示
 */

public abstract class BaseCall<T> {

    public T mService;
    protected ICache mMemoryCache;//内存
    protected ICache mDiskCache;//磁盘

    public BaseCall() {
        mService = Zygote.getZygote().getApiService(getApiServiceClass());
        if (null == mService) {
            throw new RuntimeException("模块中AppCall的getApiServiceClass方法必须被复写并返回非空类型!");
        }
        mMemoryCache = new MemoryCache();
        mDiskCache = new DiskCache();
    }

    /**
     * 针对某个接口需要做的三级缓存，当没有网络时候，读取缓存数据
     *
     * @param key
     * @param cls
     * @param observable
     * @param <T>
     * @return
     */
    protected <T extends GsonBaseBean> Observable loadForCache(final String key, Class<T> cls, final Call<BaseBean<T>> observable) {
        return Observable.concat(Observable.create(new ObservableOnSubscribe<BaseBean<T>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<BaseBean<T>> e) throws Exception {
                //必须采用call的方式，自己实现请求链!
                Response<BaseBean<T>> execute = null;
                BaseBean<T> body = null;
                try {
                    execute = observable.execute();
                    body = execute.body();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if (null == body) {
                    body = new BaseBean<T>();
                }
                e.onNext(body);
                e.onComplete();
            }

//            @Override
//            public void subscribe(@NonNull ObservableEmitter<ObservableSource> e) throws Exception {
//                Response<BaseBean<T>> execute = observable.execute();
//                BaseBean<T> body = execute.body();
//                e.onNext();
//                e.onComplete();
//            }
        }).doOnNext(new Consumer<BaseBean<T>>() {
            @Override
            public void accept(BaseBean<T> tBaseBean) throws Exception {
                if (null != tBaseBean && tBaseBean.isSuccess()) {
                    mDiskCache.put(key, tBaseBean);
                    mMemoryCache.put(key, tBaseBean);
                }
            }
        }), mMemoryCache.get(key, cls), mDiskCache.get(key, cls)).filter(new Predicate<BaseBean<T>>() {
            @Override
            public boolean test(@NonNull BaseBean<T> bean) throws Exception {
                //设定自己的过滤规则
                return bean.isSuccess();
            }
        }).firstElement().toObservable();
    }

    /**
     * 必须被子类复写，从而得到class对象
     *
     * @return
     */
    protected abstract Class<T> getApiServiceClass();
}
