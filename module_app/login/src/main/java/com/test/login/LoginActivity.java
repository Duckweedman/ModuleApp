package com.test.login;

import android.view.View;

import com.github.mzule.activityrouter.annotation.Router;
import com.test.modulebrary.base.BaseActivity;
import com.test.modulebrary.util.JumpUtil;
/**
 * Created by meijunqiang on 2017/11/3  15:49.
 * 描述：LoginActivity
 */
@Router(JumpUtil.LOGIN)
public class LoginActivity extends BaseActivity {
    @Override
    public int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresent() {

    }

    @Override
    public void initData() {
        findViewById(R.id.login_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpUtil.open(LoginActivity.this, JumpUtil.MAIN);
                finish();
            }
        });
    }
    @Override
    public void doBusiness() {

    }
}
