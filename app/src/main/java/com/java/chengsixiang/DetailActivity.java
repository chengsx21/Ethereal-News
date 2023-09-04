package com.java.chengsixiang;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.java.chengsixiang.utils.GlideApp;
import com.java.chengsixiang.utils.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends Activity {
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
        mTitle = findViewById(R.id.news_detail_title);
        mAuthor = findViewById(R.id.news_detail_author);
        mDate = findViewById(R.id.news_detail_date);
        mContent = findViewById(R.id.news_detail_content);
        mImage = findViewById(R.id.news_detail_image);
        mVideo = findViewById(R.id.news_detail_video);
        Bundle bundle = this.getIntent().getExtras();
        title = bundle.getString("title");
        author = bundle.getString("author");
        date = bundle.getString("date");
        content = bundle.getString("content");
        newsID = bundle.getString("newsID");
        imageUrl = bundle.getString("imageUrl");
        videoUrl = bundle.getString("videoUrl");
        isFavorite = new DatabaseHelper(this).isNewsIDExistsInFavorite(newsID);
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
            if (imageUrl.equals("")) {
                mImage.setVisibility(View.GONE);
            } else {
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
        ImageButton mFavoriteButton = findViewById(R.id.favorite_button);
        if (isFavorite) {
            mFavoriteButton.setImageResource(R.drawable.ic_favorite_full);
        } else {
            mFavoriteButton.setImageResource(R.drawable.ic_favorite);
        }

        mFavoriteButton.setOnClickListener(view -> {
            isFavorite = !isFavorite;
            if (isFavorite) {
                mFavoriteButton.setImageResource(R.drawable.ic_favorite_full);
            } else {
                mFavoriteButton.setImageResource(R.drawable.ic_favorite);
            }
            updateFavoriteRecords(isFavorite);
        });
    }

    private void updateHistoryRecords() {
        String readDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        long rowId = dbHelper.insertHistoryRecord(title, author, date, readDate, content, newsID, imageUrl, videoUrl);
        dbHelper.close();
        if (rowId == -1) {
            Toast.makeText(this, "保存历史记录失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateFavoriteRecords(boolean isFavorite) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        if (isFavorite) {
            String starDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            long rowId = dbHelper.insertFavoriteRecord(title, author, date, starDate, content, newsID, imageUrl, videoUrl);
            dbHelper.close();
            if (rowId != -1) {
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
            }
        } else {
            dbHelper.deleteFavoriteRecord(newsID);
            dbHelper.close();
            Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
        }
        dbHelper.close();
    }
}
