package com.test.optimize.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.networkbench.agent.impl.NBSAppAgent;
import com.test.optimize.R;
import com.test.optimize.appnet.AppCall;
import com.test.optimize.home.adapter.FragmentAdapter;
import com.test.optimize.home.fragments.GroupFragment;
import com.test.optimize.home.fragments.MineFragment;
import com.test.optimize.home.fragments.MyStoreFragment;
import com.test.optimize.home.fragments.SortBarWebFragment;
import com.test.shopcart.CartFragment;
import com.test.modulebrary.BaseApplication;
import com.test.modulebrary.base.BaseActivity;
import com.test.modulebrary.base.BaseBean;
import com.test.modulebrary.jsbridge.WebFragment;
import com.test.modulebrary.network.BaseApiSubscriber;
import com.test.modulebrary.util.JumpUtil;
import com.test.modulebrary.util.ToastUtil;

import java.util.ArrayList;

@Router(JumpUtil.MAIN)
public class MainActivity extends BaseActivity implements View.OnClickListener {
    public ViewPager mMainViewPager;
    private TabLayout mMainTableLayout;
    private String[] titles = {"首页", "拼团", "我的店鋪", "购物车", "我的"};
    private int[] images = {R.drawable.logo2, R.drawable.logo2, R.drawable.logo2, R.drawable.logo2, R.drawable.logo2};
    private WebFragment mHomeFragment;//首页
    private GroupFragment mGroupFragment;//团购
    private MyStoreFragment mMyStoreFragment;//我的店铺
    private CartFragment mCartFragment;//购物车
    private MineFragment mMineFragment;//我的（个人中心)
    private long mBackTime;

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresent() {

    }

    @Override
    public void initData() {
        NBSAppAgent.setLicenseKey("一个key").withLocationServiceEnabled(true).start(this.getApplicationContext());
        isFragmentActivity = true;
        mMainViewPager = findViewById(R.id.main_view_pager);
        mMainTableLayout = findViewById(R.id.main_table_layout);
        //首页分页面列表
        ArrayList<Fragment> fragments = new ArrayList<>();
        mHomeFragment = new SortBarWebFragment();
        mHomeFragment.setUrl("http://www.baidu.com");
        mGroupFragment = new GroupFragment();
        mMyStoreFragment = new MyStoreFragment();
        mCartFragment = new CartFragment();
        mMineFragment = new MineFragment();
        fragments.add(mHomeFragment);
        fragments.add(mGroupFragment);
        fragments.add(mMyStoreFragment);
        fragments.add(mCartFragment);
        fragments.add(mMineFragment);
        mMainViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments, titles));
        mMainTableLayout.setupWithViewPager(mMainViewPager);
        mMainTableLayout.setSelectedTabIndicatorHeight(0);
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = mMainTableLayout.getTabAt(i);
            if (null != tab) {
                tab.setCustomView(R.layout.tab_view);
                ImageView tabImageView = tab.getCustomView().findViewById(R.id.tab_iv);
                TextView tabTextView = tab.getCustomView().findViewById(R.id.tab_tv);
                tabImageView.setImageResource(images[i]);
                tabTextView.setText(titles[i]);
                //默认显示首页信息（0角标)
                if (i == 0) {
                    tab.getCustomView().setSelected(true);
                }
            }
        }
        AppCall.obtain().mService.getAppHomePage()
                .compose(BaseApplication.getApplication().<BaseBean<Object>>bindToLifecycle())
                .subscribe(new BaseApiSubscriber<Object>() {
                    @Override
                    protected void onHandleSuccess(Object s) {
                        super.onHandleSuccess(s);
                        ToastUtil.toastShort(s.toString());
                    }
                });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void doBusiness() {

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK://用户首页返回键
                if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
                    //2秒内连续两次退出应用程序
                    if (System.currentTimeMillis() - mBackTime < 2000) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        return true;
                    } else {
                        mBackTime = System.currentTimeMillis();
                        ToastUtil.toastShort(getResources().getString(R.string.main_exit_app_next));
                        return false;
                    }
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
