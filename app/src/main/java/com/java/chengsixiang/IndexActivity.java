package com.java.chengsixiang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.java.chengsixiang.utils.NewsPagerAdapter;

public class IndexActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_activity);
        setTabLayout();
        setSearchButton();
    }

    private void setTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        NewsPagerAdapter pagerAdapter = new NewsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setSearchButton() {
        ImageButton mSearchButton = findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(view -> {
            Intent intent = new Intent(IndexActivity.this, SearchActivity.class);
            startActivity(intent);
        });
    }
}

    /*
    private List<NewsItem> mList;
    RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
    LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLinearLayoutManager);
    mList = new ArrayList<>();
    for (int i = 0; i < 20; i++){
        mList.add(new NewsItem());
    }
    NewsAdapter mNewsAdapter = new NewsAdapter(this, mList);
    mRecyclerView.setAdapter(mNewsAdapter);
    */