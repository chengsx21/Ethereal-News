package com.java.chengsixiang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.java.chengsixiang.utils.NewsQueryHelper;

import java.util.Objects;

public class SearchResultActivity extends AppCompatActivity {
    private final String defaultSize = "20";
    private String words;
    private String startDate;
    private String endDate;
    private String categories;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);

        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Search Results");
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        words = bundle.getString("words");
        startDate = bundle.getString("startDate");
        endDate = bundle.getString("endDate");
        categories = bundle.getString("categories");
        if (Objects.equals(startDate, "默认向前检索所有新闻")) {
            startDate = "";
        }
        if (Objects.equals(endDate, "默认为当前时间")) {
            endDate = "";
        }
        NewsQueryHelper newsHelper = new NewsQueryHelper();
//        new Thread(() -> newsHelper.queryNews(defaultSize, startDate, endDate, words, categories)).start();
    }
}
