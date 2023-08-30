package com.java.chengsixiang.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.chengsixiang.DetailActivity;
import com.java.chengsixiang.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final List<NewsItem> mNews;

    public NewsAdapter(Context context, List<NewsItem> list){
        this.mContext = context;
        this.mNews = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsItem newsItem = mNews.get(position);
        ((NewsHolder) holder).tv_title.setText(newsItem.getTitle());
        ((NewsHolder) holder).tv_author.setText(newsItem.getAuthor());
        GlideApp.with(mContext).load(newsItem.getUrl()).into(((NewsHolder) holder).iv);
        holder.itemView.setClickable(true);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, DetailActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("title", newsItem.getTitle());
            bundle.putString("content", newsItem.getContent());
            bundle.putString("date", newsItem.getDate());
            bundle.putString("author", newsItem.getAuthor());
            bundle.putString("url", newsItem.getUrl());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }

    static class NewsHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_author;
        ImageView iv;

        public NewsHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.news_item_title);
            tv_author = itemView.findViewById(R.id.news_item_author);
            iv = itemView.findViewById(R.id.news_item_image);
        }
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }
}
