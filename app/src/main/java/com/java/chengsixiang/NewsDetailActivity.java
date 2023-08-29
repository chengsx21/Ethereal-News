package com.java.chengsixiang;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.chengsixiang.models.NewsItem;
import com.java.chengsixiang.models.GlideApp;

public class NewsDetailActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail_activity);

        Bundle bundle = this.getIntent().getExtras();
        String mTitle = bundle.getString("title");
        String mContent = bundle.getString("content");
        String mDate = bundle.getString("date");
        String mAuthor = bundle.getString("author");
        String mUrl = bundle.getString("url");

        TextView title = findViewById(R.id.news_detail_title);
        TextView author = findViewById(R.id.news_detail_author);
        TextView date = findViewById(R.id.news_detail_date);
        TextView content = findViewById(R.id.news_detail_content);
        ImageView image = findViewById(R.id.news_detail_image);

        title.setText(mTitle);
        content.setText(mContent);
        date.setText(mDate);
        author.setText(mAuthor);
        GlideApp.with(this).load(mUrl).into(image);
    }
}
