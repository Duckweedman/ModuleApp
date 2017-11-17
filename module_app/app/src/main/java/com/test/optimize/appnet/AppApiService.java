package com.test.optimize.appnet;

import com.test.modulebrary.base.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by meijunqiang on 2017/11/17  10:25.
 * 描述：AppApiService 购物车接口
 */

public interface AppApiService {
    /**
     * 测试
     *
     * @return
     */
    @GET("cms/page/getAppHomePage.do")
    Observable<BaseBean<Object>> getAppHomePage();
}
