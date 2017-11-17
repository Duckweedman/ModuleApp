package com.test.shopcart.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.test.shopcart.CartConstant;
import com.test.modulebrary.base.GsonBaseBean;

import java.util.List;

/**
 * Created by meijunqiang on 2017/11/14  10:58.
 * 描述：ShopCartBean
 */

public class ShopCartBean implements MultiItemEntity, GsonBaseBean {
    public boolean isCheck = false;//是否被选中
    /**
     * memberId : 1
     * totalSalePrice : 82.5
     * discountAmount : 4.5
     * paidAmount : 78.0
     * shoppingCartItemVOsMap : {}
     * shoppingCartItemVOs : [{"memberId":1,"pkuId":4,"pkuName":"测试商品4","quantity":1,"unitSalePrice":8,"unitDiscountAmount":0,"unitPaidAmount":8,"totalSalePrice":8,"discountAmount":0,"paidAmount":8,"shopId":4,"shopName":"店铺4","shareMemberId":0,"version":1,"addTime":1510827966524,"pkuImgUrl":"http://img12.360buyimg.com/mobilecms/s250x250_jfs/t5788/136/5192222029/307348/ed016353/595ca9f5N934a0c69.jpg","checked":true,"pkuStatusActive":true},{"memberId":1,"pkuId":5,"pkuName":"测试商品5","quantity":1,"unitSalePrice":8,"unitDiscountAmount":0,"unitPaidAmount":8,"totalSalePrice":8,"discountAmount":0,"paidAmount":8,"shopId":4,"shopName":"店铺4","shareMemberId":0,"version":1,"addTime":1510827971738,"pkuImgUrl":"http://img12.360buyimg.com/mobilecms/s250x250_jfs/t5788/136/5192222029/307348/ed016353/595ca9f5N934a0c69.jpg","checked":true,"pkuStatusActive":true},{"memberId":1,"pkuId":2,"pkuName":"测试商品2","quantity":2,"unitSalePrice":8,"unitDiscountAmount":0,"unitPaidAmount":8,"totalSalePrice":16,"discountAmount":0,"paidAmount":16,"shopId":4,"shopName":"店铺4","shareMemberId":0,"version":1,"addTime":1510827855143,"pkuImgUrl":"http://img12.360buyimg.com/mobilecms/s250x250_jfs/t5788/136/5192222029/307348/ed016353/595ca9f5N934a0c69.jpg","checked":true,"pkuStatusActive":true},{"memberId":1,"pkuId":3,"pkuName":"测试商品3","quantity":4,"unitSalePrice":8,"unitDiscountAmount":0,"unitPaidAmount":8,"totalSalePrice":32,"discountAmount":0,"paidAmount":32,"shopId":4,"shopName":"店铺4","shareMemberId":0,"version":1,"addTime":1510827909953,"pkuImgUrl":"http://img12.360buyimg.com/mobilecms/s250x250_jfs/t5788/136/5192222029/307348/ed016353/595ca9f5N934a0c69.jpg","checked":true,"pkuStatusActive":true},{"memberId":1,"pkuId":6,"pkuName":"测试商品6","quantity":1,"unitSalePrice":8,"unitDiscountAmount":0,"unitPaidAmount":8,"totalSalePrice":8,"discountAmount":0,"paidAmount":8,"shopId":4,"shopName":"店铺4","shareMemberId":0,"version":1,"addTime":1510827978614,"pkuImgUrl":"http://img12.360buyimg.com/mobilecms/s250x250_jfs/t5788/136/5192222029/307348/ed016353/595ca9f5N934a0c69.jpg","checked":true,"pkuStatusActive":true},{"memberId":1,"pkuId":1,"pkuName":"测试商品1","quantity":3,"unitSalePrice":3.5,"unitDiscountAmount":1.5,"unitPaidAmount":2,"totalSalePrice":10.5,"discountAmount":4.5,"paidAmount":6,"shopId":4,"shopName":"店铺4","shareMemberId":0,"version":1,"addTime":1510827923636,"pkuImgUrl":"http://img12.360buyimg.com/mobilecms/s250x250_jfs/t5788/136/5192222029/307348/ed016353/595ca9f5N934a0c69.jpg","checked":true,"pkuStatusActive":true}]
     * checkedCartItemNum : 6
     * checkedPkuQuantity : 12
     * totalCartItemNum : 6
     * totalPkuQuantity : 12
     * checkedTotalSalePrice : 82.5
     * checkedDiscountAmount : 4.5
     * checkedPaidAmount : 78.0
     * version : 1
     */

    public long memberId;
    public double totalSalePrice;
    public double discountAmount;
    public double paidAmount;
    public ShoppingCartItemVOsMapBean shoppingCartItemVOsMap;
    public int checkedCartItemNum;
    public int checkedPkuQuantity;
    public int totalCartItemNum;
    public int totalPkuQuantity;
    public double checkedTotalSalePrice;
    public double checkedDiscountAmount;
    public double checkedPaidAmount;
    public int version;
    public List<ShoppingCartItemVOsBean> shoppingCartItemVOs;

    @Override
    public int getItemType() {
        return CartConstant.SHOP;
    }

    public static class ShoppingCartItemVOsMapBean {
    }

    public static class ShoppingCartItemVOsBean {
        /**
         * memberId : 1
         * pkuId : 4
         * pkuName : 测试商品4
         * quantity : 1
         * unitSalePrice : 8
         * unitDiscountAmount : 0
         * unitPaidAmount : 8
         * totalSalePrice : 8
         * discountAmount : 0
         * paidAmount : 8
         * shopId : 4
         * shopName : 店铺4
         * shareMemberId : 0
         * version : 1
         * addTime : 1510827966524
         * pkuImgUrl : http://img12.360buyimg.com/mobilecms/s250x250_jfs/t5788/136/5192222029/307348/ed016353/595ca9f5N934a0c69.jpg
         * checked : true
         * pkuStatusActive : true
         */

        public long memberId;
        public long pkuId;
        public String pkuName;
        public int quantity;
        public double unitSalePrice;
        public double unitDiscountAmount;
        public double unitPaidAmount;
        public double totalSalePrice;
        public double discountAmount;
        public double paidAmount;
        public long shopId;
        public String shopName;
        public long  shareMemberId;
        public int version;
        public long addTime;
        public String pkuImgUrl;
        public boolean checked;
        public boolean pkuStatusActive;
    }
}
