package com.java.chengsixiang;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.java.chengsixiang.utils.GlideApp;
import com.java.chengsixiang.utils.NewsAdapter;

import java.util.Objects;

public class DetailActivity extends Activity {
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mDate;
    private TextView mContent;
    private ImageView mImage;
    private VideoView mVideo;

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
        mVideo = findViewById(R.id.news_detail_video);
    }

    public void setView() {
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        mTitle.setText(bundle.getString("title"));
        mAuthor.setText(bundle.getString("author"));
        mDate.setText(bundle.getString("date"));
        mContent.setText(bundle.getString("content"));
        String imageUrl = bundle.getString("imageUrl");
        String videoUrl = bundle.getString("videoUrl");
        if (!videoUrl.equals("")) {
            mVideo.setVisibility(View.VISIBLE);
            mVideo.setVideoPath(videoUrl);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(mVideo);
            mVideo.setMediaController(mediaController);
            mVideo.requestFocus();
        } else {
            mVideo.setVisibility(View.GONE);
            if (imageUrl.equals("")) {
                mImage.setVisibility(View.GONE);
            } else {
                mImage.setVisibility(View.VISIBLE);
                GlideApp.with(this)
                        .load(bundle.getString("imageUrl"))
                        .centerCrop() // 缩放类型
                        .into(mImage);
            }
        }
    }

    public void setBackButton() {
        ImageButton mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(view -> finish());
    }
}
