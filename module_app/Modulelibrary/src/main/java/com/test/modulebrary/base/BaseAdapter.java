package com.test.modulebrary.base;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.modulebrary.BaseApplication;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meijunqiang on 2017/11/4 0021 15:01.
 * 描述：RecyclerView使用的简单Adapter简单基类
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    protected WeakReference<RxFragmentActivity> mContext;
    protected List<T> mData = new ArrayList<>();

    public void addData(T t) {
        if (null != t) {
            mData.add(t);
            notifyItemChanged(mData.size());
        }
    }

    public void addData(List<T> addList) {
        if (addList.size() > 0) {
            mData.addAll(addList);
            notifyItemRangeChanged(mData.size() - addList.size(), addList.size());
        }
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setDatas(List<T> data) {
        if (null == data) {
            data = new ArrayList<>();
        }
        mData.clear();
        mData = data;
        notifyDataSetChanged();
    }

    public BaseAdapter(List<T> data) {
        mContext = BaseApplication.getApplication().getTopActivity();
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext.get()).inflate(setViewLayoutId(), parent, false));
    }

    protected abstract int setViewLayoutId();

    protected int setViewLayoutId(int viewType) {
        return 0;
    }

    ;

    protected abstract void onBindViewHolder(ViewHolder holder, T t);

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        onBindViewHolder(holder, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        SparseArray<View> views = new SparseArray<>();

        public <L extends View> L getView(@IdRes int resId) {
            View view = views.get(resId);
            if (null == view) {
                view = itemView.findViewById(resId);
                views.put(resId, view);
            }
            return (L) view;
        }

        public void setText(@IdRes int resId, CharSequence text) {
            View view = views.get(resId);
            if (null == view) {
                view = itemView.findViewById(resId);
                views.put(resId, view);
            }
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
