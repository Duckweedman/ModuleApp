package com.test.modulebrary.network.cache;

import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import com.google.gson.Gson;
import com.test.modulebrary.base.BaseBean;

import java.io.UnsupportedEncodingException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by meijunqiang on 2017/11/21  15:13.
 * 描述：MemoryCache 内存拿缓存数据
 */

public class MemoryCache implements ICache {
    private LruCache<String, String> mCache;

    public MemoryCache() {
        //获取系统分配给单个应用的最大内存数（32M）
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int mCacheSize = maxMemory / 8;
        //给LruCache缓存池分配maxMemory/8=4M
        mCache = new LruCache<String, String>(mCacheSize) {
            @Override
            protected int sizeOf(String key, String value) {
                try {
                    return value.getBytes("UTF-8").length;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return value.getBytes().length;
                }
            }
        };
    }

    @Override
    public <T> Observable<BaseBean<T>> get(final String key, final Class<T> cls) {
        return Observable.create(new ObservableOnSubscribe<BaseBean<T>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<BaseBean<T>> subscriber) throws Exception {
                Log.d("cache", "load from memory: " + key);
                String result = mCache.get(key);
                if (subscriber.isDisposed()) {
                    return;
                }
                if (TextUtils.isEmpty(result)) {
                    subscriber.onNext(new BaseBean<T>());
                } else {
                    BaseBean baseBean = new BaseBean();
                    BaseBean bean = new Gson().fromJson(result, baseBean.getClass());
                    if (null != bean.data) {
                        bean.data = new Gson().fromJson(new Gson().toJson(bean.data), cls);
                    }
                    subscriber.onNext(bean);
                }
                subscriber.onComplete();
            }
        });
    }

    @Override
    public <T> void put(String key, T t) {
        if (null != t) {
            Log.d("cache", "save to memory: " + key);
            mCache.put(key, new Gson().toJson(t));
        }
    }
}
