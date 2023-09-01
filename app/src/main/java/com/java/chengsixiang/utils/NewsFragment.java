package com.java.chengsixiang.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.java.chengsixiang.R;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private static final String[] CATEGORY_NAMES = {"全部", "娱乐", "军事", "教育", "文化", "健康", "财经", "体育", "汽车", "科技", "社会"};

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
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        int categoryPosition = getArguments().getInt("position");

        RecyclerView recyclerView = rootView.findViewById(R.id.tab_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(newsAdapter);

        SwipeRefreshLayout refreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(() -> {
            Log.d("NewsFragment", "refresh");
            loadNewsForCategory(categoryPosition, newsAdapter);
            refreshLayout.setRefreshing(false);
        });

        loadNewsForCategory(categoryPosition, newsAdapter);
        return rootView;
    }

    private void loadNewsForCategory(int categoryPosition, NewsAdapter newsAdapter) {
        String categoryName = CATEGORY_NAMES[categoryPosition];

        NewsQueryHelper newsQueryHelper = new NewsQueryHelper();
        newsQueryHelper.queryNews("", "", "", "", categoryName, new NewsQueryHelper.NewsQueryCallback() {
            @Override
            public void onSuccess(List<NewsItem> newsItems) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    newsAdapter.setNewsItems(newsItems);
                    newsAdapter.notifyDataSetChanged();
                });
            }
            @Override
            public void onFailure(String errorMessage) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

}
