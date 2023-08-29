package com.java.chengsixiang.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.chengsixiang.NewsDetailActivity;
import com.java.chengsixiang.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<NewsItem> mNews;
    private FragmentManager fragmentManager;

    public NewsAdapter(Context context, List<NewsItem> list, FragmentManager fragmentManager){
        this.mContext = context;
        this.mNews = list;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsItem newsItem = mNews.get(position);
        ((NewsHolder) holder).tv_title.setText(newsItem.title);
        ((NewsHolder) holder).tv_author.setText(newsItem.author);
        GlideApp.with(mContext).load(newsItem.url).into(((NewsHolder) holder).iv);
        holder.itemView.setClickable(true);

        ((NewsHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("title", newsItem.title);
                bundle.putString("content", newsItem.content);
                bundle.putString("date", newsItem.date);
                bundle.putString("author", newsItem.author);
                bundle.putString("url", newsItem.url);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_author;
        ImageView iv;

        public NewsHolder(View itemView) {
            super(itemView);
            tv_title = (TextView)itemView.findViewById(R.id.news_item_title);
            tv_author = (TextView)itemView.findViewById(R.id.news_item_author);
            iv = (ImageView)itemView.findViewById(R.id.news_item_image);
        }
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }
}
