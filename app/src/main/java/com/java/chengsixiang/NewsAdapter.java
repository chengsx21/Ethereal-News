package com.java.chengsixiang;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //获取从Activity中传递过来每个item的数据集合
    private Context mContext;
    private List<News> mNews;

    //构造函数
    public NewsAdapter(Context context, List<News> list){
        this.mContext = context;
        this.mNews = list;
    }

    //创建View
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsHolder) {
            ((NewsHolder) holder).tv_title.setText(mNews.get(position).title);
            ((NewsHolder) holder).tv_content.setText(mNews.get(position).content);
            GlideApp.with(mContext).load(mNews.get(position).url).into(((NewsHolder)holder).iv);
        }
        return;
    }

    //在这里面加载ListView中的每个item的布局
    class NewsHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_content;
        ImageView iv;

        public NewsHolder(View itemView) {
            super(itemView);
            tv_title = (TextView)itemView.findViewById(R.id.news_title);
            tv_content = (TextView)itemView.findViewById(R.id.news_desc);
            iv = (ImageView)itemView.findViewById(R.id.news_image);
        }
    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        return mNews.size();
    }
}
