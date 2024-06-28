package com.example.jiemian.UI;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jiemian.R;
import com.example.jiemian.adapter.CommentAdapter;
import com.example.jiemian.network.RetrofitClient;
import com.example.jiemian.network.VideoApiService;
import com.example.jiemian.network.VideoResponse;
import com.example.jiemian.sqlite.CommentDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VideoDetailActivity extends AppCompatActivity {
    private VideoView videoView;
    private RecyclerView commentSection;
    private EditText commentInput;
    private Button commentSubmitButton;
    private Button playPauseButton;

    private CommentAdapter commentAdapter;
    private List<String> comments;

    private CommentDatabaseHelper dbHelper;
    private String videoPath;
    private boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        videoView = findViewById(R.id.videoView);
        commentSection = findViewById(R.id.commentSection);
        commentInput = findViewById(R.id.commentInput);
        commentSubmitButton = findViewById(R.id.commentSubmitButton);
        playPauseButton = findViewById(R.id.playPauseButton);

        Intent intent = getIntent();
        String videoId = intent.getStringExtra("detail");

        if (videoId == null || videoId.isEmpty()) {
            Log.e("VideoDetailActivity", "Video ID is null or empty");
            return;
        }

        fetchVideoInfo(videoId);

        // Initialize the comment list and adapter
        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(comments);
        commentSection.setLayoutManager(new LinearLayoutManager(this));
        commentSection.setAdapter(commentAdapter);

        // Initialize the database helper
        dbHelper = new CommentDatabaseHelper(this);

        // Load existing comments from the database
        loadCommentsFromDatabase(videoId);

        // Set click listener for the submit button
        commentSubmitButton.setOnClickListener(v -> {
            String newComment = commentInput.getText().toString();
            if (!newComment.isEmpty()) {
                // Add the new comment to the database and update the adapter
                saveCommentToDatabase(newComment, videoId);
                comments.add(newComment);
                commentAdapter.notifyItemInserted(comments.size() - 1);
                commentInput.setText(""); // Clear the input field
            }
        });

        // Set click listener for the play/pause button
        playPauseButton.setOnClickListener(v -> {
            if (isPlaying) {
                videoView.pause();
                playPauseButton.setText("播放");
            } else {
                videoView.start();
                playPauseButton.setText("暂停");
            }
            isPlaying = !isPlaying;
        });
    }

    private void fetchVideoInfo(String videoId) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        VideoApiService apiService = retrofit.create(VideoApiService.class);
        Call<VideoResponse> call = apiService.getVideoInfo(videoId);

        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    VideoResponse videoResponse = response.body();
                    videoPath = videoResponse.getVideoUrl();
                    videoView.setVideoPath(videoPath);
                    videoView.start();
                } else {
                    Log.e("VideoDetailActivity", "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.e("VideoDetailActivity", "Failed to fetch video info", t);
            }
        });
    }

    private void loadCommentsFromDatabase(String videoId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                CommentDatabaseHelper.TABLE_COMMENTS,
                new String[]{CommentDatabaseHelper.COLUMN_COMMENT},
                CommentDatabaseHelper.COLUMN_VIDEO_PATH + "=?",
                new String[]{videoId},
                null, null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String comment = cursor.getString(
                        cursor.getColumnIndexOrThrow(CommentDatabaseHelper.COLUMN_COMMENT));
                comments.add(comment);
            }
            cursor.close();
        }
    }

    private void saveCommentToDatabase(String comment, String videoId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CommentDatabaseHelper.COLUMN_VIDEO_PATH, videoId);
        values.put(CommentDatabaseHelper.COLUMN_COMMENT, comment);
        db.insert(CommentDatabaseHelper.TABLE_COMMENTS, null, values);
    }
}
