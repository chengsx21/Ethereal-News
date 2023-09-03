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

    private void bindView() {
        mTitle = findViewById(R.id.news_detail_title);
        mAuthor = findViewById(R.id.news_detail_author);
        mDate = findViewById(R.id.news_detail_date);
        mContent = findViewById(R.id.news_detail_content);
        mImage = findViewById(R.id.news_detail_image);
        mVideo = findViewById(R.id.news_detail_video);
    }

    private void setView() {
        Bundle bundle = this.getIntent().getExtras();
        String title = bundle.getString("title");
        String author = bundle.getString("author");
        String date = bundle.getString("date");
        String content = bundle.getString("content");
        String newsID = bundle.getString("newsID");
        String imageUrl = bundle.getString("imageUrl");
        String videoUrl = bundle.getString("videoUrl");
        mTitle.setText(title);
        mAuthor.setText(author);
        mDate.setText(date);
        mContent.setText(content);
        bindImageAndVideo(imageUrl, videoUrl);

        String readDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        long rowId = dbHelper.insertHistoryRecord(title, author, date, readDate, content, newsID, imageUrl, videoUrl);
        dbHelper.close();
        if (rowId != -1) {
            Toast.makeText(this, "保存成功" + rowId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
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
}
