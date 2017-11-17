package com.test.modulebrary.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by meijunqiang on 2017/11/7  09:05.
 * 描述：StatusRootLinearLayout 为了替换整个window的容器
 */

public class StatusRootLinearLayout extends LinearLayout {
    public StatusRootLinearLayout(Context context) {
        this(context,null);
    }

    public StatusRootLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StatusRootLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
