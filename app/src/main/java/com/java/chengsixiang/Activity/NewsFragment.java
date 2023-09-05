package com.java.chengsixiang.Activity;

import static com.java.chengsixiang.Utils.QueryHelper.getTimeBefore;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.java.chengsixiang.R;
import com.java.chengsixiang.Adapter.NewsAdapter;
import com.java.chengsixiang.Utils.NewsItem;
import com.java.chengsixiang.Adapter.PagerAdapter;
import com.java.chengsixiang.Utils.QueryHelper;
import com.java.chengsixiang.Utils.ScrollListener;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private String categoryName;
    private String endDate;
    private boolean loaded;
    private View rootView;
    private ProgressBar progressBar;
    private NewsAdapter newsAdapter;
    private ScrollListener newsScrollListener;

    public static NewsFragment newInstance(int categoryPosition) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", categoryPosition);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_fragment, container, false);
        categoryName = PagerAdapter.CATEGORY_NAMES.get(requireArguments().getInt("position"));
        progressBar = rootView.findViewById(R.id.progress_bar);
        setRecyclerView();
        setSwipeRefreshView();
        initNewsForCategory();
        return rootView;
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = rootView.findViewById(R.id.tab_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        newsAdapter = new NewsAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(newsAdapter);
        newsScrollListener = new ScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (!loaded) {
                    progressBar.setVisibility(View.VISIBLE);
                    loadNewsForCategory();
                    new Handler().postDelayed(
                        () -> progressBar.setVisibility(View.GONE), 600
                    );
                }
            }
        };
        recyclerView.addOnScrollListener(newsScrollListener);
    }

    private void setSwipeRefreshView() {
        SwipeRefreshLayout refreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(() -> {
            initNewsForCategory();
            refreshLayout.setRefreshing(false);
        });
    }

    private void initNewsForCategory() {
        loaded = false;
        QueryHelper queryHelper = new QueryHelper();
        queryHelper.queryNews("", "", "", "", categoryName, new QueryHelper.NewsQueryCallback() {
            @Override
            public void onSuccess(List<NewsItem> newsItems) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    endDate = getTimeBefore(newsItems.get(newsItems.size() - 1).getDate());
                    newsAdapter.setNewsItems(newsItems);
                    newsScrollListener.resetLoadState();
                });
            }
            @Override
            public void onFailure(String errorMessage) {
                new Handler(Looper.getMainLooper()).post(
                    () -> Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    private void loadNewsForCategory() {
        QueryHelper queryHelper = new QueryHelper();
        queryHelper.queryNews("", "", endDate, "", categoryName, new QueryHelper.NewsQueryCallback() {
            @Override
            public void onSuccess(List<NewsItem> newsItems) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (newsItems.size() == 0)
                        loaded = true;
                    else {
                        endDate = getTimeBefore(newsItems.get(newsItems.size() - 1).getDate());
                        newsAdapter.addNewsItems(newsItems);
                    }
                });
            }
            @Override
            public void onFailure(String errorMessage) {
                new Handler(Looper.getMainLooper()).post(
                    () -> Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

}
