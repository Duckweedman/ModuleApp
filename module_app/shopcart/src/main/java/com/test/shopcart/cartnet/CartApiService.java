package com.test.shopcart.cartnet;

import com.test.modulebrary.base.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by meijunqiang on 2017/11/17  10:25.
 * 描述：CartApiService 购物车接口
 */

public interface CartApiService {
    @POST("api/v1/shoppingcart/displayShoppingCart")
    Observable<BaseBean<Object>> displayShoppingCart(@Body String param);
}
