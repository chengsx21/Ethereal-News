package com.java.chengsixiang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.java.chengsixiang.R;
import com.java.chengsixiang.Adapter.PagerAdapter;

public class HomePage extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setTabLayout();
        setSettingButton();
        setFavoriteButton();
        setHistoryButton();
        setSearchButton();
    }

    private void setTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setSettingButton() {
        ImageButton mSettingButton = findViewById(R.id.setting_button);
        mSettingButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, CategorySetting.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    private void setFavoriteButton() {
        ImageButton mFavoriteButton = findViewById(R.id.favorite_button);
        mFavoriteButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, FavoriteNews.class);
            startActivity(intent);
        });
    }

    private void setHistoryButton() {
        ImageButton mHistoryButton = findViewById(R.id.history_button);
        mHistoryButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, HistoryRecord.class);
            startActivity(intent);
        });
    }

    private void setSearchButton() {
        ImageButton mSearchButton = findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, SearchPage.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            setTabLayout();
        }
    }
}
