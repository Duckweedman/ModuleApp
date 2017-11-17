package com.test.modulebrary.base;

import android.support.annotation.LayoutRes;

/**
 * Created by meijunqiang on 2017/11/4 0011.
 * 描述：初始化页面的模板方法
 */

public interface BaseViewCommonService {
    /**
     * 组件显示界面
     * @return
     */
    @LayoutRes
    int setLayoutId();
    /**
     * 初始化Present层
     */
    void initPresent();

    /**
     * 初始化数据（进程间数据适配）或者注解控件的二次逻辑处理
     */
    void initData();

    /**
     * 处理事件和业务
     */
    void doBusiness();
}
