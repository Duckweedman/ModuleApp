package com.test.shopcart.cartnet;

import com.test.modulebrary.BaseApplication;
import com.test.modulebrary.base.BaseBean;
import com.test.modulebrary.network.BaseCall;
import com.test.modulebrary.network.BaseApiSubscriber;
import com.test.shopcart.bean.ShopCartBean;

import java.util.HashMap;

/**
 * Created by meijunqiang on 2017/11/17  10:26.
 * 描述：CartCall
 */

public class CartCall extends BaseCall<CartApiService> {
    private static CartCall mCall;

    /**
     * 获取请求模型
     *
     * @return
     */
    public static CartCall obtain() {
        synchronized (CartCall.class) {
            if (mCall == null) {
                mCall = new CartCall();
            }
        }
        return mCall;
    }

    @Override
    protected Class<CartApiService> getApiServiceClass() {
        return CartApiService.class;
    }

    /**
     * 浏览购物车
     *
     * @param memberId
     * @param subscriber
     */
    public void displayShoppingCart(String memberId, BaseApiSubscriber subscriber) {
        HashMap<String, String> params = new HashMap<>();
        params.put("memberId", memberId);
        loadForCache("shop_card", ShopCartBean.class,mService.displayShoppingCart2(params.toString()))
                .compose(BaseApplication.getApplication().<BaseBean<ShopCartBean>>bindToLifecycle())
                .subscribe(subscriber);
    }
}
