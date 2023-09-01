package com.java.chengsixiang;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.chengsixiang.utils.GlideApp;

import java.util.Objects;

public class DetailActivity extends Activity {
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mDate;
    private TextView mContent;
    private ImageView mImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        bindView();
        setView();
        setBackButton();
    }

    public void bindView() {
        mTitle = findViewById(R.id.news_detail_title);
        mAuthor = findViewById(R.id.news_detail_author);
        mDate = findViewById(R.id.news_detail_date);
        mContent = findViewById(R.id.news_detail_content);
        mImage = findViewById(R.id.news_detail_image);
    }

    public void setView() {
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        mTitle.setText(bundle.getString("title"));
        mAuthor.setText(bundle.getString("author"));
        mDate.setText(bundle.getString("date"));
        mContent.setText(bundle.getString("content"));
        String url = bundle.getString("url");
        if (Objects.requireNonNull(url).equals("")) {
            mImage.setVisibility(View.GONE);
        } else {
            mImage.setVisibility(View.VISIBLE);
            GlideApp.with(this).load(bundle.getString("url")).into(mImage);
        }
    }

    public void setBackButton() {
        ImageButton mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(view -> finish());
    }
}
