package com.test.modulebrary.base;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.test.modulebrary.util.DialogHelp;
import com.test.modulebrary.util.UtilsDisplayMetrics;
import com.test.modulebrary.view.StatusRootLinearLayout;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Created by Administrator on 2017/11/4 0006.
 */
public abstract class BaseFragment<T> extends RxFragment implements BaseViewCommonService {

    protected BaseActivity mActivity;
    private View rootView;
    protected T mPresent;
    //进度对话框
    private ProgressDialog _waitDialog;
    //对话框是否可见
    protected boolean _isVisiable = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected <T extends View> T findViewById(@IdRes int resId) {
        return rootView.findViewById(resId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        if (rootView == null) {
            View view = createView();
            if (view != null) {
                rootView = view;
            } else {
                rootView = inflater.inflate(setLayoutId(), container, false);
            }
        }
        return getStatusView();
    }

    /**
     * 一些fragment不适用layout的情况，复写该View即可
     *
     * @return
     */
    protected View createView() {
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresent();
        initData();
        doBusiness();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresent != null && mPresent instanceof BasePresent) {
            ((BasePresent) mPresent).onDestory();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public ProgressDialog showLoading() {
        if (_isVisiable) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelp.getWaitDialog(mActivity, "加载中");
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage("加载中");
                try {
                    _waitDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return _waitDialog;
        }
        return null;
    }

    public void hideLoading() {
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
        int statusHeight = UtilsDisplayMetrics.getStatusBarHeight(getActivity());
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusHeight);
        statusView.setLayoutParams(param);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    /**
     * 添加一个适配的状态栏View
     *
     * @return
     */
    public View getStatusView() {
        View view = new View(mActivity);
        setStatusBarHeight(view);
        view.setBackgroundColor(Color.TRANSPARENT);
        StatusRootLinearLayout rootLinearLayout = new StatusRootLinearLayout(mActivity);
        rootLinearLayout.setOrientation(LinearLayout.VERTICAL);
        if (rootView instanceof ViewGroup) {
            ViewGroup rootGroup = (ViewGroup) rootView;
            View firstView = rootGroup.getChildAt(0);
            //状态栏同步色：根布局第一个控件背景色，如果没有，考虑根布局背景色,如果没有，取系统色
            if (null != firstView.getBackground() && firstView.getBackground() instanceof ColorDrawable) {
                view.setBackgroundColor(((ColorDrawable) firstView.getBackground()).getColor());
            } else if (null != rootGroup.getBackground() && rootGroup.getBackground() instanceof ColorDrawable) {
                view.setBackgroundColor(((ColorDrawable) rootGroup.getBackground()).getColor());
            }
        }
        rootLinearLayout.addView(view);
        rootLinearLayout.addView(rootView);
        return rootLinearLayout;
    }
}
