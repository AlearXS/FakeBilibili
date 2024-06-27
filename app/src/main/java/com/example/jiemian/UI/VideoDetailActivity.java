package com.example.jiemian.UI;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jiemian.R;
import com.example.jiemian.adapter.CommentAdapter;
import com.example.jiemian.sqlite.CommentDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

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
    private boolean isFullscreen = false;

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
        videoPath = intent.getStringExtra("detail");

        // Set the video path to the VideoView
        videoView.setVideoPath(videoPath);
        videoView.start();

        // Initialize the comment list and adapter
        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(comments);
        commentSection.setLayoutManager(new LinearLayoutManager(this));
        commentSection.setAdapter(commentAdapter);

        // Initialize the database helper
        dbHelper = new CommentDatabaseHelper(this);

        // Load existing comments from the database
        loadCommentsFromDatabase();

        // Set click listener for the submit button
        commentSubmitButton.setOnClickListener(v -> {
            String newComment = commentInput.getText().toString();
            if (!newComment.isEmpty()) {
                // Add the new comment to the database and update the adapter
                saveCommentToDatabase(newComment);
                comments.add(newComment);
                commentAdapter.notifyItemInserted(comments.size() - 1);
                commentInput.setText(""); // Clear the input field
            }
        });

        // Set click listener for the play/pause button
        playPauseButton.setOnClickListener(v -> {
            if (isPlaying) {
                videoView.pause();
                playPauseButton.setText("Play");
            } else {
                videoView.start();
                playPauseButton.setText("Pause");
            }
            isPlaying = !isPlaying;
        });


    }

    private void loadCommentsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                CommentDatabaseHelper.TABLE_COMMENTS,
                new String[]{CommentDatabaseHelper.COLUMN_COMMENT},
                CommentDatabaseHelper.COLUMN_VIDEO_PATH + "=?",
                new String[]{videoPath},
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

    private void saveCommentToDatabase(String comment) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CommentDatabaseHelper.COLUMN_VIDEO_PATH, videoPath);
        values.put(CommentDatabaseHelper.COLUMN_COMMENT, comment);
        db.insert(CommentDatabaseHelper.TABLE_COMMENTS, null, values);
    }

    private void enterFullscreen() {
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ViewGroup.LayoutParams params = videoView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        videoView.setLayoutParams(params);
    }

    private void exitFullscreen() {
        getSupportActionBar().show();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewGroup.LayoutParams params = videoView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        videoView.setLayoutParams(params);
    }
}