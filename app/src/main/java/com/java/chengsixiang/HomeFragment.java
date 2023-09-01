package com.java.chengsixiang;

import static com.java.chengsixiang.utils.QueryHelper.getTimeBefore;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.java.chengsixiang.utils.NewsAdapter;
import com.java.chengsixiang.utils.NewsItem;
import com.java.chengsixiang.utils.QueryHelper;
import com.java.chengsixiang.utils.ListScrollListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String[] CATEGORY_NAMES = {"全部", "娱乐", "军事", "教育", "文化", "健康", "财经", "体育", "汽车", "科技", "社会"};
    private String mCategoryName;
    private String mEndDate;
    private boolean mFullLoaded;
    private View rootView;
    private NewsAdapter newsAdapter;
    private ListScrollListener newsScrollListener;

    public static HomeFragment newInstance(int categoryPosition) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", categoryPosition);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        mCategoryName = CATEGORY_NAMES[getArguments().getInt("position")];
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
        newsScrollListener = new ListScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if (!mFullLoaded) {
                    loadNewsForCategory();
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
        mFullLoaded = false;

        QueryHelper newsQueryHelper = new QueryHelper();
        newsQueryHelper.queryNews("", "", "", "", mCategoryName, new QueryHelper.NewsQueryCallback() {
            @Override
            public void onSuccess(List<NewsItem> newsItems) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    mEndDate = getTimeBefore(newsItems.get(newsItems.size() - 1).getDate());
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
        QueryHelper newsQueryHelper = new QueryHelper();
        newsQueryHelper.queryNews("", "", mEndDate, "", mCategoryName, new QueryHelper.NewsQueryCallback() {
            @Override
            public void onSuccess(List<NewsItem> newsItems) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (newsItems.size() == 0) {
                        mFullLoaded = true;
                    }
                    else {
                        mEndDate = getTimeBefore(newsItems.get(newsItems.size() - 1).getDate());
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
