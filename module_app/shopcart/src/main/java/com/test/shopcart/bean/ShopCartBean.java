package com.test.shopcart.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.test.modulebrary.base.GsonBaseBean;
import com.test.shopcart.CartConstant;

/**
 * Created by meijunqiang on 2017/11/14  10:58.
 * 描述：ShopCartBean
 */

public class ShopCartBean implements MultiItemEntity, GsonBaseBean {
    public boolean isCheck = false;//是否被选中

    @Override
    public int getItemType() {
        return CartConstant.SHOP;
    }
}
