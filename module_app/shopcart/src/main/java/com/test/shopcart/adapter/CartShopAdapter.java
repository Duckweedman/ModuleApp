package com.test.shopcart.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.shopcart.CartConstant;
import com.test.shopcart.R;
import com.test.shopcart.bean.ShopCartBean;
import com.test.modulebrary.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by meijunqiang on 2017/11/14  10:49.
 * 描述：CartShopAdapter 购物车商品列表
 */

public class CartShopAdapter extends BaseMultiItemQuickAdapter<ShopCartBean, BaseViewHolder> {
    public boolean isEdit = false;//是否处于编辑状态

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CartShopAdapter(ArrayList<ShopCartBean> data) {
        super(data);
        addItemType(CartConstant.SHOP, R.layout.item_cart_shop);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ShopCartBean cartBean) {
        View editProductView = helper.getView(R.id.linear_editproduct_num);
        final ImageView checkBox = helper.getView(R.id.checkbox_button);
        //是否处于编辑状态
        if (isEdit) {
            editProductView.setVisibility(View.VISIBLE);
        } else {
            editProductView.setVisibility(View.GONE);
        }
        if (cartBean.isCheck) {
            checkBox.setImageResource(R.drawable.selected_true);
        } else {
            checkBox.setImageResource(R.drawable.selected_false);
        }
        //是否选中
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调接口，通过接口的方式回显按钮状态，保证和服务端同步
                //TODO:meiyizhi 测试效果,实际调接口，需要删掉
                cartBean.isCheck = !cartBean.isCheck;
                if (cartBean.isCheck) {
                    checkBox.setImageResource(R.drawable.selected_true);
                } else {
                    checkBox.setImageResource(R.drawable.selected_false);
                }
            }
        });
        helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.toastShort("删除当前下标" + helper.getPosition());
            }
        });
    }
}
