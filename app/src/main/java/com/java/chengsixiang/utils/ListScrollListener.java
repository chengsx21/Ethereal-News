package com.java.chengsixiang.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ListScrollListener extends RecyclerView.OnScrollListener{
    private final LinearLayoutManager mLinearLayoutManager;
    private int currentPage = 0; // 当前页
    private int previousTotal = 0; // 上一个 totalItemCount
    private boolean loading = true; // 是否上拉

    public ListScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 可见 Item 数
        int visibleItemCount = recyclerView.getChildCount();
        // 已加载 Item 数
        int totalItemCount = mLinearLayoutManager.getItemCount();
        // 首个可见 Item
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading){
            if (totalItemCount > previousTotal){
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount <= firstVisibleItem)){
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    public void resetLoadState() {
        currentPage = 0;
        previousTotal = 0;
        loading = true;
    }

    public abstract void onLoadMore(int currentPage);
}
