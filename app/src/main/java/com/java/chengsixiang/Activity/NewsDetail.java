package com.java.chengsixiang.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.java.chengsixiang.R;
import com.java.chengsixiang.Utils.GlideApp;
import com.java.chengsixiang.Utils.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class NewsDetail extends Activity {
    private TextView mTitle;
    private String title;
    private TextView mAuthor;
    private String author;
    private TextView mDate;
    private String date;
    private TextView mContent;
    private String content;
    private ImageView mImage;
    private String imageUrl;
    private VideoView mVideo;
    private String videoUrl;
    private String newsID;
    private boolean isFavorite;
    private ImageButton mFavoriteButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        bindView();
        setView();
        setFavoriteButton();
        setBackButton();
    }

    private void bindView() {
        Bundle bundle = this.getIntent().getExtras();
        mTitle = findViewById(R.id.news_detail_title);
        title = Objects.requireNonNull(bundle).getString("title");
        mAuthor = findViewById(R.id.news_detail_author);
        author = bundle.getString("author");
        mDate = findViewById(R.id.news_detail_date);
        date = bundle.getString("date");
        mContent = findViewById(R.id.news_detail_content);
        content = bundle.getString("content");
        mImage = findViewById(R.id.news_detail_image);
        imageUrl = bundle.getString("imageUrl");
        mVideo = findViewById(R.id.news_detail_video);
        videoUrl = bundle.getString("videoUrl");
        newsID = bundle.getString("newsID");
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            isFavorite = dbHelper.isNewsIDExistsInFavorite(newsID);
            dbHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setView() {
        mTitle.setText(title);
        mAuthor.setText(author);
        mDate.setText(date);
        mContent.setText(content);
        bindImageAndVideo(imageUrl, videoUrl);
        updateHistoryRecords();
    }

    private void bindImageAndVideo(String imageUrl, String videoUrl) {
        if (!videoUrl.equals("")) {
            mVideo.setVisibility(View.VISIBLE);
            mVideo.setVideoPath(videoUrl);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(mVideo);
            mVideo.setMediaController(mediaController);
            mVideo.requestFocus();
        } else {
            mVideo.setVisibility(View.GONE);
            if (imageUrl.equals(""))
                mImage.setVisibility(View.GONE);
            else {
                mImage.setVisibility(View.VISIBLE);
                GlideApp.with(this)
                        .load(imageUrl)
                        .centerCrop() // 缩放类型
                        .into(mImage);
            }
        }
    }

    private void setBackButton() {
        ImageButton mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(view -> finish());
    }

    private void setFavoriteButton() {
        mFavoriteButton = findViewById(R.id.favorite_button);
        setFavoriteIcon();
        mFavoriteButton.setOnClickListener(view -> {
            isFavorite = !isFavorite;
            setFavoriteIcon();
            updateFavoriteRecords(isFavorite);
        });
    }

    private void setFavoriteIcon() {
        if (isFavorite)
            mFavoriteButton.setImageResource(R.drawable.ic_favorite_full);
        else
            mFavoriteButton.setImageResource(R.drawable.ic_favorite);
    }

    private void updateHistoryRecords() {
        String readDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        long rowId = dbHelper.insertHistoryRecord(title, author, date, readDate, content, newsID, imageUrl, videoUrl);
        dbHelper.close();
        if (rowId == -1)
            Toast.makeText(this, "保存历史记录失败", Toast.LENGTH_SHORT).show();
    }

    private void updateFavoriteRecords(boolean isFavorite) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        if (isFavorite) {
            String starDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            long rowId = dbHelper.insertFavoriteRecord(title, author, date, starDate, content, newsID, imageUrl, videoUrl);
            dbHelper.close();
            if (rowId != -1)
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
        } else {
            long rowId = dbHelper.deleteFavoriteRecord(newsID);
            dbHelper.close();
            if (rowId != -1)
                Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
        }
        dbHelper.close();
    }
}
