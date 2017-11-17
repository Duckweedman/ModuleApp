package com.test.modulebrary.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.test.modulebrary.util.DialogHelp;
import com.test.modulebrary.util.UtilsDisplayMetrics;
import com.test.modulebrary.view.StatusRootLinearLayout;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

/**
 * Created by meijunqiang on 2017/11/3  09:05.
 * 描述：BaseActivity 公共基类
 */
public abstract class BaseActivity<T> extends RxFragmentActivity implements BaseViewCommonService {
    protected boolean mIsFullScreen = false;    // 界面是否全屏遮住状态栏
    protected boolean isStatusBarTranslate = false;    // 状态栏是否透明
    protected T mPresent;
    protected boolean _isVisiable;//对话框是否可见
    private ProgressDialog _waitDialog;//进度对话框
    private boolean onResumeIsRun = false;//处理startForResult返回时后走onResume的情况,true标识手动调用onResume
    protected boolean isFragmentActivity = false;//子类是否有子Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initStatusBar();
        int layoutId = setLayoutId();
        if (layoutId != 0) {
            setContentView(setLayoutId());
        }
        _isVisiable = true;
        initPresent();
        initData();
        doBusiness();

    }

    /**
     * 覆盖的resume
     */
    protected void resume() {

    }

    /**
     * 子类如果使用到onActivityResult,应当使用resume的回调
     */
    @Override
    protected void onResume() {
        super.onResume();
        setSyncStatus();
        if (onResumeIsRun) {
            onResumeIsRun = !onResumeIsRun;
        } else {
            resume();
        }
    }

    private void setSyncStatus() {
        try {
            if (isFragmentActivity) {
                return;
            }
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            ViewGroup rootView = (ViewGroup) decorView.getChildAt(0);
            //TOD:meiyizhi 全局状态栏统一处理，对5.0以下的机型，调用setStatusBarHeight方法填充状态栏高度
            if (!(rootView instanceof StatusRootLinearLayout)) {
                View view = new View(this);
                setStatusBarHeight(view);
                view.setBackgroundColor(Color.TRANSPARENT);
                decorView.removeView(rootView);
                StatusRootLinearLayout rootLinearLayout = new StatusRootLinearLayout(this);
                rootLinearLayout.setOrientation(LinearLayout.VERTICAL);
                ViewGroup rootGroup = (ViewGroup) rootView.getChildAt(1);
                ViewGroup bootView = (ViewGroup) rootGroup.getChildAt(0);
                View firstView = bootView.getChildAt(0);
                //状态栏同步色：根布局第一个控件背景色，如果没有，考虑根布局背景色,如果没有，取系统色
                if (null != firstView.getBackground() && firstView.getBackground() instanceof ColorDrawable) {
                    view.setBackgroundColor(((ColorDrawable) firstView.getBackground()).getColor());
                } else if (null != bootView.getBackground() && bootView.getBackground() instanceof ColorDrawable) {
                    view.setBackgroundColor(((ColorDrawable) bootView.getBackground()).getColor());
                }
                rootLinearLayout.addView(view);
                rootLinearLayout.addView(rootView);
                decorView.addView(rootLinearLayout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.onResume();
        onResumeIsRun = true;
    }

    /**
     * 设置状态栏背景色
     *
     * @param view
     * @param colorId
     * @author ray
     * @date 2017/09/20
     */
    protected void setStatusView(View view, int colorId) {
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, UtilsDisplayMetrics.getStatusBarHeight(this)));
        view.setBackgroundResource(colorId);
    }

    /**
     * 初始化状态栏相关，
     * PS: 设置全屏需要在调用super.onCreate(arg0);之前设置setIsFullScreen(true);否则在Android 6.0下非全屏的activity会出错;
     * SDK19：可以设置状态栏透明，但是半透明的SYSTEM_BAR_BACKGROUNDS会不好看；
     * SDK21：可以设置状态栏颜色，并且可以清除SYSTEM_BAR_BACKGROUNDS，但是不能设置状态栏字体颜色（默认的白色字体在浅色背景下看不清楚）；
     * SDK23：可以设置状态栏为浅色（SYSTEM_UI_FLAG_LIGHT_STATUS_BAR），字体就回反转为黑色。
     * 为兼容目前效果，仅在SDK23才显示沉浸式。
     */
    private void initStatusBar() {
        Window win = getWindow();
        if (mIsFullScreen) {
            win.requestFeature(Window.FEATURE_NO_TITLE);
            win.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);    // 去掉信息栏
            win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);    // 保持屏幕高亮
        } else {
            // KITKAT也能满足，只是SYSTEM_UI_FLAG_LIGHT_STATUS_BAR（状态栏字体颜色反转）只有在6.0才有效
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);    // 透明状态栏
                // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加
                win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                // 部分机型的statusbar会有半透明的黑色背景
                win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                win.setStatusBarColor(Color.TRANSPARENT);    // SDK21

                isStatusBarTranslate = true;
            } else {
                win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION     // 占用底部导航栏
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                isStatusBarTranslate = true;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                win.setStatusBarColor(Color.TRANSPARENT);    // 透明状态栏
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresent != null && mPresent instanceof BasePresent) {
            ((BasePresent) mPresent).onDestory();
        }
        _isVisiable = false;
    }

    public ProgressDialog showWaitDialog(int resId) {
        return showWaitDialog(getString(resId));
    }

    public ProgressDialog showWaitDialog(String message) {
        if (_isVisiable) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelp.getWaitDialog(this, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    public ProgressDialog showWaitDialog() {
        String message = "正在加载中...";
        if (_isVisiable) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelp.getWaitDialog(this, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    public void hideWaitDialog() {
        if (_isVisiable && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 设置title顶部状态栏高度
     *
     * @param statusView 单个需要改变的view
     */
    public void setStatusBarHeight(View statusView) {
        int statusHeight = UtilsDisplayMetrics.getStatusBarHeight(this);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusHeight);
        statusView.setLayoutParams(param);
    }

    public void setStatusBarHeightRL(View statusView) {
        int statusHeight = UtilsDisplayMetrics.getStatusBarHeight(this);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusHeight);
        statusView.setLayoutParams(param);
    }

}
