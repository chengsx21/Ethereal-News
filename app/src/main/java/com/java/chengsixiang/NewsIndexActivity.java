package com.java.chengsixiang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.java.chengsixiang.models.NewsAdapter;
import com.java.chengsixiang.models.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsIndexActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private List<NewsItem> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_index_activity);

        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initData();
        mNewsAdapter = new NewsAdapter(this, mList);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    private void initData(){
        mList = new ArrayList<NewsItem>();
        for (int i = 0; i < 20; i++){
            mList.add(new NewsItem());
        }
    }

}