package com.java.chengsixiang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.java.chengsixiang.utils.NewsAdapter;
import com.java.chengsixiang.utils.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity {
    private List<NewsItem> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_activity);
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        ImageButton mSearchButton = findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(view -> {
            Intent intent = new Intent(IndexActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        initData();
        NewsAdapter mNewsAdapter = new NewsAdapter(this, mList);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    private void initData(){
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            mList.add(new NewsItem());
        }
    }

}