package com.java.chengsixiang.Activity;

import static com.java.chengsixiang.Utils.QueryHelper.getTimeBefore;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.chengsixiang.R;
import com.java.chengsixiang.Adapter.NewsAdapter;
import com.java.chengsixiang.Utils.NewsItem;
import com.java.chengsixiang.Utils.QueryHelper;
import com.java.chengsixiang.Utils.ScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchResult extends AppCompatActivity {
    private String words;
    private String startDate;
    private String endDate;
    private String categories;
    private String realEndDate;
    private Context context;
    private LinearLayoutManager layoutManager;
    private NewsAdapter newsAdapter;
    private TextView newsCount;
    private ProgressBar loadingBar;
    private final QueryHelper queryHelper = new QueryHelper();
    private boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);
        initParams();
        setRecycleView();
        setBackButton();
        initNewsForCategory(categories, newsAdapter);
    }

    private void initParams() {
        context = this;
        loadingBar = findViewById(R.id.loading_bar);
        loadingBar.setVisibility(ProgressBar.VISIBLE);
        newsCount = findViewById(R.id.news_count);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.news_result_toolbar);
        Bundle bundle = this.getIntent().getExtras();
        words = Objects.requireNonNull(bundle).getString("words");
        startDate = bundle.getString("startDate");
        endDate = bundle.getString("endDate");
        categories = bundle.getString("categories");
        if (Objects.equals(startDate, "默认向前检索所有新闻"))
            startDate = "";
        if (Objects.equals(endDate, "默认为当前时间"))
            endDate = "";
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
                    loadNewsForCategory(categories, newsAdapter);
            }
        });
    }

    private void initNewsForCategory(String categoryName, NewsAdapter newsAdapter) {
        queryHelper.queryNews("", startDate, endDate, words, categoryName, new QueryHelper.NewsQueryCallback() {
            @Override
            public void onSuccess(List<NewsItem> newsItems, int newsCount) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (newsItems.size() == 0)
                        loaded = true;
                    else {
                        realEndDate = getTimeBefore(newsItems.get(newsItems.size() - 1).getDate());
                        newsAdapter.setNewsItems(newsItems);
                    }
                    SearchResult.this.newsCount.setVisibility(TextView.VISIBLE);
                    SearchResult.this.newsCount.setText(String.format("为您检索到了%d条新闻:", newsCount));
                    loadingBar.setVisibility(ProgressBar.GONE);
                });
            }
            @Override
            public void onFailure(String errorMessage) {
                new Handler(Looper.getMainLooper()).post(
                    () -> Toast.makeText(context, "请检查网络连接", Toast.LENGTH_SHORT).show()
                );
                loadingBar.setVisibility(ProgressBar.GONE);
            }
        });
    }

    private void loadNewsForCategory(String categoryName, NewsAdapter newsAdapter) {
        queryHelper.queryNews("", startDate, realEndDate, words, categoryName, new QueryHelper.NewsQueryCallback() {
            @Override
            public void onSuccess(List<NewsItem> newsItems, int newsCount) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (newsItems.size() == 0)
                        loaded = true;
                    else {
                        realEndDate = getTimeBefore(newsItems.get(newsItems.size() - 1).getDate());
                        newsAdapter.addNewsItems(newsItems);
                    }
                });
            }
            @Override
            public void onFailure(String errorMessage) {
                new Handler(Looper.getMainLooper()).post(
                    () -> Toast.makeText(context, "请检查网络连接", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}
