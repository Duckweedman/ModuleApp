package com.test.modulebrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.modulebrary.BaseApplication;
import com.test.modulebrary.R;

/**
 * Created by meijunqiang on 2017/11/9 0019 15:42.
 * 描述：适用于通用的返回键加标题的头部，特殊头部另做处理
 */

public class CommonToolBar extends LinearLayout {

    private TextView mTitleView;
    private TextView mRightTitleView;
    private ImageView mCommonIvBack;
    private ImageView mCommonRightImg;

    public CommonToolBar(Context context) {
        this(context, null);
    }

    public CommonToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * 初始化控件
     *
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs) {
        Drawable background = getBackground();
        if (background == null) {
            setBackgroundColor(Color.parseColor("#424c55"));
        }
        LayoutInflater.from(context).inflate(R.layout.view_common_toolbar, this, true);
        mTitleView = findViewById(R.id.common_text_title);
        mRightTitleView = findViewById(R.id.common_title_right_tv);
        mCommonIvBack = findViewById(R.id.common_iv_back);
        mCommonRightImg = findViewById(R.id.common_title_right_img);
        mCommonIvBack.setOnClickListener(mTitleLeftListener);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonToolBar);
            String title = typedArray.getString(R.styleable.CommonToolBar_title);
            String right_title = typedArray.getString(R.styleable.CommonToolBar_right_title);
            int theme = typedArray.getInt(R.styleable.CommonToolBar_bgTheme, 1);
            int rightImgId = typedArray.getResourceId(R.styleable.CommonToolBar_right_img, 0);
            if (rightImgId != 0) {
                mCommonRightImg.setVisibility(View.VISIBLE);
                mCommonRightImg.setImageResource(rightImgId);
            }
            if (theme == 0) {//黑色主题
                mTitleView.setTextColor(getResources().getColor(R.color.main_title_color));
                mRightTitleView.setTextColor(getResources().getColor(R.color.main_title_color));
                mCommonIvBack.setImageResource(R.drawable.public_title_back_black);
            } else {//白色主题
                mTitleView.setTextColor(getResources().getColor(R.color.white));
                mRightTitleView.setTextColor(getResources().getColor(R.color.white));
                mCommonIvBack.setImageResource(R.drawable.ic_back);
            }
            if (!TextUtils.isEmpty(title)) {
                mTitleView.setText(title);
            }
            if (!TextUtils.isEmpty(right_title)) {
                mRightTitleView.setText(right_title);
                mRightTitleView.setVisibility(View.VISIBLE);
            }
        }

        //设置title顶部状态栏高度
//        View statusView = findViewById(R.id.common_status_bar_view);
//        int statusHeight = UtilsDisplayMetrics.getStatusBarHeight(context);
//        LayoutParams param = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusHeight);
//        statusView.setLayoutParams(param);

    }

    public void setMidTitle(String titleStr) {
        mTitleView.setText(titleStr);
    }

    public void setLeftImg(int imgId) {
        mCommonIvBack.setImageResource(imgId);
    }

    public void hideLeftImg() {
        mCommonIvBack.setVisibility(View.GONE);
    }

    public void setLeftListener(OnClickListener listener) {
        mCommonIvBack.setOnClickListener(listener);
    }

    private OnClickListener mTitleLeftListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            BaseApplication.getApplication().getTopActivity().get().finish();
        }
    };

    public void setRightListener(OnClickListener listener) {
        mRightTitleView.setOnClickListener(listener);
        mCommonRightImg.setOnClickListener(listener);
    }

    public void setRightDrawable(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mRightTitleView.setCompoundDrawables(drawable, null, null, null);
        mRightTitleView.setCompoundDrawablePadding(5);
    }
}
