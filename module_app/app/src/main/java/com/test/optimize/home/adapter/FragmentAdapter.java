package com.test.optimize.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by meijunqiang on 2017/11/6  10:59.
 * 描述：FragmentAdapter 首页tab适配器
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;
    private String[] titles;

    public FragmentAdapter(FragmentManager supportFragmentManager, ArrayList<Fragment> fragments, String[] titles) {
        super(supportFragmentManager);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
