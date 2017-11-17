package com.test.optimize;

import com.test.modulebrary.base.BaseActivity;
import com.test.modulebrary.util.JumpUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by meijunqiang on 2017/11/13  16:14.
 * 描述：SplashActivity
 */

public class SplashActivity extends BaseActivity {
    private Disposable mDisposable;

    @Override
    public int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void doBusiness() {
        Observable.timer(2000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        JumpUtil.open(SplashActivity.this, JumpUtil.LOGIN);
                        finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
