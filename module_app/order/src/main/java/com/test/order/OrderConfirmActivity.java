package com.test.order;

import com.github.mzule.activityrouter.annotation.Router;
import com.test.modulebrary.base.BaseActivity;
import com.test.modulebrary.util.JumpUtil;

/**
 * Created by meijunqiang on 2017/11/15  09:49.
 * 描述：OrderConfirmActivity
 */
@Router(JumpUtil.ORDER_CONFIRM)
public class OrderConfirmActivity extends BaseActivity {
    @Override
    public int setLayoutId() {
        return R.layout.activity_order_confirm;
    }

    @Override
    public void initPresent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void doBusiness() {

    }
}
