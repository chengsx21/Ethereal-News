package com.java.chengsixiang.utils;

import android.support.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.java.chengsixiang.HomeFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private static int NUM_PAGES = 11;
    private static String[] CATEGORY_NAMES = {"全部", "娱乐", "军事", "教育", "文化", "健康", "财经", "体育", "汽车", "科技", "社会"};

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return HomeFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CATEGORY_NAMES[position];
    }
}
