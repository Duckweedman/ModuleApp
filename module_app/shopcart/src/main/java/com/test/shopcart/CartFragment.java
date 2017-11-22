package com.test.shopcart;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.shopcart.adapter.CartShopAdapter;
import com.test.shopcart.bean.ShopCartBean;
import com.test.shopcart.cartnet.CartCall;
import com.test.modulebrary.base.BaseBean;
import com.test.modulebrary.base.BaseFragment;
import com.test.modulebrary.network.BaseApiSubscriber;
import com.test.modulebrary.util.JumpUtil;
import com.test.modulebrary.util.ToastUtil;
import com.test.modulebrary.view.SwipeItemLayout;

import java.util.ArrayList;

/**
 * Created by meijunqiang on 2017/11/6  11:06.
 * 描述：CartFragment 购物车
 */

public class CartFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout mManRelativelayout;
    private RelativeLayout mRlTitle;
    private TextView mTvShopcartEditbutton;
    private ImageView mImgShopcartEdit;
    private RecyclerView mShopRecycleview;
    private ImageView mImgTotopButton;
    private LinearLayout mLinearBottomBar;
    private ImageView mImgAllselect;
    private LinearLayout mLinearBottomTotal;
    private TextView mTvTotal;
    private TextView mTvShopcartZprice;
    private Button mBtnShopcartAccount;
    private ArrayList<ShopCartBean> mDatas;
    private CartShopAdapter mCartShopAdapter;
    private boolean isCheckAll = false;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initPresent() {

    }

    @Override
    public void initData() {
        mManRelativelayout = (LinearLayout) findViewById(R.id.man_relativelayout);
        mRlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        mTvShopcartEditbutton = (TextView) findViewById(R.id.tv_shopcart_editbutton);
        mImgShopcartEdit = (ImageView) findViewById(R.id.img_shopcart_edit);
        mShopRecycleview = (RecyclerView) findViewById(R.id.shop_recycleview);
        mImgTotopButton = (ImageView) findViewById(R.id.img_totop_button);
        mLinearBottomBar = (LinearLayout) findViewById(R.id.linear_bottom_bar);
        mImgAllselect = (ImageView) findViewById(R.id.img_allselect);
        mLinearBottomTotal = (LinearLayout) findViewById(R.id.linear_bottom_total);
        mTvTotal = (TextView) findViewById(R.id.tv_total);
        mTvShopcartZprice = (TextView) findViewById(R.id.tv_shopcart_Zprice);
        mBtnShopcartAccount = (Button) findViewById(R.id.btn_shopcart_account);

        mTvShopcartEditbutton.setOnClickListener(this);
        mImgAllselect.setOnClickListener(this);
        mBtnShopcartAccount.setOnClickListener(this);
    }

    @Override
    public void doBusiness() {
        mLinearBottomBar.setVisibility(View.VISIBLE);
        mShopRecycleview.setLayoutManager(new LinearLayoutManager(mActivity));
        mShopRecycleview.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        mDatas = new ArrayList<>();
        mCartShopAdapter = new CartShopAdapter(mDatas);
        mShopRecycleview.setAdapter(mCartShopAdapter);
        mShopRecycleview.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_shopcart_editbutton) {//购物车编辑模式
            mCartShopAdapter.isEdit = !mCartShopAdapter.isEdit;
            mCartShopAdapter.notifyDataSetChanged();
            if (mCartShopAdapter.isEdit) {
                mTvShopcartEditbutton.setText(R.string.finish);
                mBtnShopcartAccount.setText(R.string.delete);
            } else {
                mTvShopcartEditbutton.setText(R.string.editor);
                mBtnShopcartAccount.setText(R.string.account);
            }
            CartCall.obtain().displayShoppingCart("1", new BaseApiSubscriber<ShopCartBean>() {
                @Override
                protected void onHandleSuccess(BaseBean<ShopCartBean> baseBean) {
                    ToastUtil.toastShort("memberid");
                }
            });
        } else if (view.getId() == R.id.img_allselect) {//是否全选
            isCheckAll = !isCheckAll;
            if (isCheckAll) {
                mImgAllselect.setImageResource(R.drawable.selected_true);
            } else {
                mImgAllselect.setImageResource(R.drawable.selected_false);
            }
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).isCheck = isCheckAll;
            }
            mCartShopAdapter.notifyDataSetChanged();
        } else if (view.getId() == R.id.btn_shopcart_account) {
            if (mCartShopAdapter.isEdit) {
                //TODO:meiyizhi 执行批量删除
                ToastUtil.toastShort("批量删除成功");
            } else {
                //TODO:meiyizhi 去结算页（订单确认页）
                JumpUtil.open(mActivity, JumpUtil.ORDER_CONFIRM);
            }
        }
    }
}
