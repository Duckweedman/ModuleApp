package com.test.optimize.appnet;

import com.test.modulebrary.network.BaseCall;

/**
 * Created by meijunqiang on 2017/11/17  10:26.
 * 描述：BaseCall 主module请求模型
 */

public class AppCall extends BaseCall<AppApiService>{
    private static AppCall mCall;

    /**
     * 获取请求模型
     *
     * @return
     */
    public static AppCall obtain() {
        synchronized (AppCall.class) {
            if (mCall == null) {
                mCall = new AppCall();
            }
        }
        return mCall;
    }

    @Override
    protected Class<AppApiService> getApiServiceClass() {
        return AppApiService.class;
    }

}
