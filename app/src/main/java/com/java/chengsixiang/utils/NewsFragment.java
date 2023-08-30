package com.java.chengsixiang.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        int categoryPosition = getArguments().getInt("position");
        loadNewsForCategory(categoryPosition, rootView);
        return rootView;
    }

    private void loadNewsForCategory(int categoryPosition, View rootView) {
        List<NewsItem> newsItems = fetchNewsItems(CATEGORY_NAMES[categoryPosition]);

        RecyclerView recyclerView = rootView.findViewById(R.id.tab_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        NewsAdapter newsAdapter = new NewsAdapter(getContext(), newsItems);
        recyclerView.setAdapter(newsAdapter);
    }

    private List<NewsItem> fetchNewsItems(String categoryName) {
        List<NewsItem> newsItems = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            newsItems.add(new NewsItem());
        }
        return newsItems;
    }

}