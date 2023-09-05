package com.java.chengsixiang.Adapter;

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

import com.java.chengsixiang.Activity.NewsDetail;
import com.java.chengsixiang.R;
import com.java.chengsixiang.Utils.DatabaseHelper;
import com.java.chengsixiang.Utils.GlideApp;
import com.java.chengsixiang.Utils.NewsItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<NewsItem> newsList;

    public NewsAdapter(Context context, List<NewsItem> newsList){
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsItem newsItem = newsList.get(position);
        ((NewsHolder) holder).title.setText(newsItem.getTitle());
        ((NewsHolder) holder).title.setTextColor(context.getResources().getColor(R.color.newsTitleBackground));
        ((NewsHolder) holder).author.setText(newsItem.getAuthor());
        ((NewsHolder) holder).date.setText(newsItem.getDate());
        String imageUrl = newsItem.getImageUrl();
        String videoUrl = newsItem.getVideoUrl();

        setNewsTitle((NewsHolder) holder, newsItem.getNewsID());
        setImageAndVideo((NewsHolder) holder, imageUrl, videoUrl);
        setClickItem((NewsHolder) holder, newsItem);
    }

    private void setNewsTitle(NewsHolder holder, String newsID) {
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            if (dbHelper.isNewsIDExistsInHistory(newsID))
                holder.title.setTextColor(context.getResources().getColor(R.color.newsReadTitleBackground));
            else
                holder.title.setTextColor(context.getResources().getColor(R.color.newsTitleBackground));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setImageAndVideo(NewsHolder holder, String imageUrl, String videoUrl) {
        if (!videoUrl.equals("")) {
            holder.videoView.setVisibility(View.VISIBLE);
            holder.videoView.setVideoPath(videoUrl);
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(holder.videoView);
            holder.videoView.setMediaController(mediaController);
            holder.videoView.requestFocus();
        } else {
            holder.videoView.setVisibility(View.GONE);
            if (imageUrl.equals(""))
                holder.imageView.setVisibility(View.GONE);
            else {
                holder.imageView.setVisibility(View.VISIBLE);
                GlideApp.with(context)
                        .load(imageUrl)
                        .centerCrop() // 缩放类型
                        .into(holder.imageView);
            }
        }
    }

    private void setClickItem(NewsHolder holder, NewsItem newsItem) {
        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, NewsDetail.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", newsItem.getTitle());
            bundle.putString("content", newsItem.getContent());
            bundle.putString("date", newsItem.getDate());
            bundle.putString("author", newsItem.getAuthor());
            bundle.putString("imageUrl", newsItem.getImageUrl());
            bundle.putString("videoUrl", newsItem.getVideoUrl());
            bundle.putString("newsID", newsItem.getNewsID());
            intent.putExtras(bundle);
            holder.title.setTextColor(context.getResources().getColor(R.color.newsReadTitleBackground));
            context.startActivity(intent);
        });
    }

    public void setNewsItems(List<NewsItem> newNewsItems) {
        newsList.clear();
        newsList.addAll(newNewsItems);
        notifyDataSetChanged();
    }

    public void addNewsItems(List<NewsItem> newNewsItems) {
        newsList.addAll(newNewsItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView author;
        TextView date;
        ImageView imageView;
        VideoView videoView;

        public NewsHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_item_title);
            author = itemView.findViewById(R.id.news_item_author);
            date = itemView.findViewById(R.id.news_item_date);
            imageView = itemView.findViewById(R.id.news_item_image);
            videoView = itemView.findViewById(R.id.news_item_video);
        }
    }
}
