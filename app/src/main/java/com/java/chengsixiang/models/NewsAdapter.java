package com.java.chengsixiang.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.java.chengsixiang.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 获取从 activity 中传递过来每个 item 的数据集合
    private Context mContext;
    private List<NewsItem> mNews;

    // 构造函数
    public NewsAdapter(Context context, List<NewsItem> list){
        this.mContext = context;
        this.mNews = list;
    }

    // 创建View
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsHolder) {
            ((NewsHolder) holder).tv_title.setText(mNews.get(position).title);
            ((NewsHolder) holder).tv_author.setText(mNews.get(position).content);
            GlideApp.with(mContext).load(mNews.get(position).url).into(((NewsHolder)holder).iv);
        }
        return;
    }

    // 加载 RecyclerView 中的每个 item 的布局
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
