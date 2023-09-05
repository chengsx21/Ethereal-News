package com.java.chengsixiang;

import static com.java.chengsixiang.utils.QueryHelper.getTimeBefore;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.chengsixiang.utils.NewsAdapter;
import com.java.chengsixiang.utils.NewsItem;
import com.java.chengsixiang.utils.QueryHelper;
import com.java.chengsixiang.utils.ListScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {
    private String words;
    private String startDate;
    private String endDate;
    private String categories;
    private String mEndDate;
    private Context mContext;
    private LinearLayoutManager layoutManager;
    private NewsAdapter newsAdapter;
    private final QueryHelper queryHelper = new QueryHelper();
    private boolean mFullLoaded = false;

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
        mContext = this;
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
        newsAdapter = new NewsAdapter(mContext, new ArrayList<>());
        recyclerView.setAdapter(newsAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new ListScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (!mFullLoaded)
                    loadNewsForCategory(categories, newsAdapter);
            }
        });
    }

    private void initNewsForCategory(String categoryName, NewsAdapter newsAdapter) {
        queryHelper.queryNews("", startDate, endDate, words, categoryName, new QueryHelper.NewsQueryCallback() {
            @Override
            public void onSuccess(List<NewsItem> newsItems) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (newsItems.size() == 0)
                        mFullLoaded = true;
                    else {
                        mEndDate = getTimeBefore(newsItems.get(newsItems.size() - 1).getDate());
                        newsAdapter.setNewsItems(newsItems);
                    }
                });
            }
            @Override
            public void onFailure(String errorMessage) {
                new Handler(Looper.getMainLooper()).post(
                    () -> Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    private void loadNewsForCategory(String categoryName, NewsAdapter newsAdapter) {
        queryHelper.queryNews("", startDate, mEndDate, words, categoryName, new QueryHelper.NewsQueryCallback() {
            @Override
            public void onSuccess(List<NewsItem> newsItems) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (newsItems.size() == 0)
                        mFullLoaded = true;
                    else {
                        mEndDate = getTimeBefore(newsItems.get(newsItems.size() - 1).getDate());
                        newsAdapter.addNewsItems(newsItems);
                    }
                });
            }
            @Override
            public void onFailure(String errorMessage) {
                new Handler(Looper.getMainLooper()).post(
                    () -> Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}
