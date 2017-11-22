package com.test.modulebrary.network.cache;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.test.modulebrary.BaseApplication;
import com.test.modulebrary.base.BaseBean;
import com.test.modulebrary.util.FileHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by meijunqiang on 2017/11/21  15:42.
 * 描述：DiskCache 硬盘拿缓存数据
 */

public class DiskCache implements ICache {
    private String CACHE_DISK_PATH;

    public DiskCache() {
        CACHE_DISK_PATH = BaseApplication.getRootPath() + "cache/";
    }

    @Override
    public <T> Observable<BaseBean<T>> get(final String key, final Class<T> cls) {
        return Observable.create(new ObservableOnSubscribe<BaseBean<T>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<BaseBean<T>> subscriber) throws Exception {
                Log.d("cache", "load from disk: " + key);
                String fileName = CACHE_DISK_PATH + key;
                String fileContent = FileHelper.readTextFromSDcard(fileName);
                if (subscriber.isDisposed()) {
                    return;
                }
                if (TextUtils.isEmpty(fileContent)) {

                    subscriber.onNext(null);
                } else {
                    BaseBean baseBean = new BaseBean();
                    BaseBean bean = new Gson().fromJson(fileContent, baseBean.getClass());
                    if (null != bean.data) {
                        bean.data = new Gson().fromJson(new Gson().toJson(bean.data), cls);
                    }
                    subscriber.onNext(bean);
                }
                subscriber.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public <T> void put(final String key, final T t) {
        Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> subscriber) throws Exception {
                Log.v("cache", "save to disk: " + key);
                if (null == t) {
                    return;
                }
                String fileContent = new Gson().toJson(t);
                FileHelper.saveText2Sdcard(CACHE_DISK_PATH + key, fileContent);
                if (!subscriber.isDisposed()) {
                    subscriber.onNext(t);
                    subscriber.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}
