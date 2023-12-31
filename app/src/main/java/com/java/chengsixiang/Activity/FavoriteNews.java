package com.java.chengsixiang.Activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.chengsixiang.R;
import com.java.chengsixiang.Adapter.NewsAdapter;
import com.java.chengsixiang.Utils.NewsItem;
import com.java.chengsixiang.Utils.DatabaseHelper;
import com.java.chengsixiang.Utils.ScrollListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteNews extends AppCompatActivity {
    private int page = 0;
    private Context context;
    private List<NewsItem> newsItems;
    private LinearLayoutManager layoutManager;
    private NewsAdapter newsAdapter;
    private boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);
        initParams();
        setRecycleView();
        setBackButton();
        loadNewsForCategory(page, newsAdapter);
    }

    private void initParams() {
        context = this;
        TextView newsCount = findViewById(R.id.news_count);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.news_favorite_toolbar);
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            newsItems = dbHelper.getFavoriteRecord();
            newsCount.setVisibility(TextView.VISIBLE);
            newsCount.setText(getString(R.string.favorite_result) + newsItems.size() + getString(R.string.news_count));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "读取本地数据库失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void setBackButton() {
        ImageButton mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(view -> finish());
    }

    private void setRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        newsAdapter = new NewsAdapter(context, new ArrayList<>());
        recyclerView.setAdapter(newsAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new ScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (!loaded)
                    loadNewsForCategory(page, newsAdapter);
            }
        });
    }

    private void loadNewsForCategory(int page, NewsAdapter newsAdapter) {
        int newsPerPage = 10;
        int fromIndex = page * newsPerPage;
        int toIndex = Math.min((page + 1) * newsPerPage, newsItems.size());
        if (fromIndex < toIndex) {
            List<NewsItem> loadItems = newsItems.subList(fromIndex, toIndex);
            this.page++;
            newsAdapter.addNewsItems(loadItems);
        }
        else
            loaded = true;
    }
}
