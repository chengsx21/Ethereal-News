package com.java.chengsixiang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.java.chengsixiang.utils.PagerAdapter;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setTabLayout();
        setSettingButton();
        setSearchButton();
    }

    private void setTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setSettingButton() {
        ImageButton mSettingButton = findViewById(R.id.setting_button);
        mSettingButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
            startActivity(intent);
        });
    }

    private void setSearchButton() {
        ImageButton mSearchButton = findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(intent);
        });
    }
}
