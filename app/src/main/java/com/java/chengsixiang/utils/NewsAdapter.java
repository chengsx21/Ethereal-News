package com.java.chengsixiang.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsItem newsItem = mNews.get(position);
        ((NewsHolder) holder).tv_title.setText(newsItem.getTitle());
        ((NewsHolder) holder).tv_title.setTextColor(mContext.getResources().getColor(R.color.newsTitleBackground));
        ((NewsHolder) holder).tv_author.setText(newsItem.getAuthor());
        ((NewsHolder) holder).tv_date.setText(newsItem.getDate());
        String imageUrl = newsItem.getImageUrl();
        String videoUrl = newsItem.getVideoUrl();

        setNewsTitle((NewsHolder) holder, newsItem.getNewsID());
        setImageAndVideo((NewsHolder) holder, imageUrl, videoUrl);
        setClickItem((NewsHolder) holder, newsItem);
    }

    private void setNewsTitle(NewsHolder holder, String newsID) {
        try (DatabaseHelper dbHelper = new DatabaseHelper(mContext)) {
            if (dbHelper.isNewsIDExistsInHistory(newsID))
                holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.newsReadTitleBackground));
            else
                holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.newsTitleBackground));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setImageAndVideo(NewsHolder holder, String imageUrl, String videoUrl) {
        if (!videoUrl.equals("")) {
            holder.vv.setVisibility(View.VISIBLE);
            holder.vv.setVideoPath(videoUrl);
            MediaController mediaController = new MediaController(mContext);
            mediaController.setAnchorView(holder.vv);
            holder.vv.setMediaController(mediaController);
            holder.vv.requestFocus();
        } else {
            holder.vv.setVisibility(View.GONE);
            if (imageUrl.equals(""))
                holder.iv.setVisibility(View.GONE);
            else {
                holder.iv.setVisibility(View.VISIBLE);
                GlideApp.with(mContext)
                        .load(imageUrl)
                        .centerCrop() // 缩放类型
                        .into(holder.iv);
            }
        }
    }

    private void setClickItem(NewsHolder holder, NewsItem newsItem) {
        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", newsItem.getTitle());
            bundle.putString("content", newsItem.getContent());
            bundle.putString("date", newsItem.getDate());
            bundle.putString("author", newsItem.getAuthor());
            bundle.putString("imageUrl", newsItem.getImageUrl());
            bundle.putString("videoUrl", newsItem.getVideoUrl());
            bundle.putString("newsID", newsItem.getNewsID());
            intent.putExtras(bundle);
            holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.newsReadTitleBackground));
            mContext.startActivity(intent);
        });
    }

    public void setNewsItems(List<NewsItem> newNewsItems) {
        mNews.clear();
        mNews.addAll(newNewsItems);
        notifyDataSetChanged();
    }

    public void addNewsItems(List<NewsItem> newNewsItems) {
        mNews.addAll(newNewsItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    static class NewsHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_author;
        TextView tv_date;
        ImageView iv;
        VideoView vv;

        public NewsHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.news_item_title);
            tv_author = itemView.findViewById(R.id.news_item_author);
            tv_date = itemView.findViewById(R.id.news_item_date);
            iv = itemView.findViewById(R.id.news_item_image);
            vv = itemView.findViewById(R.id.news_item_video);
        }
    }
}
