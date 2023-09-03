package com.java.chengsixiang;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.chengsixiang.utils.NewsAdapter;
import com.java.chengsixiang.utils.NewsItem;
import com.java.chengsixiang.utils.DatabaseHelper;
import com.java.chengsixiang.utils.ListScrollListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private final int newsPerPage = 10;
    private int mPage = 0;
    private Context mContext;
    private List<NewsItem> newsItems;
    private LinearLayoutManager layoutManager;
    private NewsAdapter newsAdapter;
    private boolean mFullLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);
        initParams();
        setRecycleView();
        setBackButton();
        loadNewsForCategory(mPage, newsAdapter);
    }

    private void initParams() {
        mContext = this;
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.news_history_toolbar);
        newsItems = new DatabaseHelper(mContext).getHistoryRecord();
    }

    private void setBackButton() {
        ImageButton mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(view -> finish());
    }

    private void setRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        newsAdapter = new NewsAdapter(mContext, new ArrayList<>());
        recyclerView.setAdapter(newsAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new ListScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (!mFullLoaded) {
                    loadNewsForCategory(mPage, newsAdapter);
                }
            }
        });
    }

    private void loadNewsForCategory(int page, NewsAdapter newsAdapter) {
        int fromIndex = page * newsPerPage;
        int toIndex = Math.min((page + 1) * newsPerPage, newsItems.size());
        if (fromIndex < toIndex) {
            List<NewsItem> loadItems = newsItems.subList(fromIndex, toIndex);
            mPage++;
            newsAdapter.addNewsItems(loadItems);
        } else {
            mFullLoaded = true;
        }
    }
}
