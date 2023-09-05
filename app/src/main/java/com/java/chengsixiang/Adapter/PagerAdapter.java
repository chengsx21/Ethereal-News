package com.java.chengsixiang.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.java.chengsixiang.Activity.NewsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {
    public static int NUM_PAGES;
    public static List<String> CATEGORY_NAMES;

    public PagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        SharedPreferences sharedCategories = context.getSharedPreferences("categories", MODE_PRIVATE);
        String categoryString = sharedCategories.getString("selected", "");
        if (categoryString.isEmpty())
            CATEGORY_NAMES = new ArrayList<>(Arrays.asList("全部", "娱乐", "军事", "教育", "文化", "健康", "财经", "体育", "汽车", "科技", "社会"));
        else
            CATEGORY_NAMES = new ArrayList<>(Arrays.asList(categoryString.split(",")));
        NUM_PAGES = CATEGORY_NAMES.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return NewsFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CATEGORY_NAMES.get(position);
    }
}
