package com.example.jiemian.UI;

import android.media.MediaPlayer;
import android.widget.MediaController;
import android.widget.Toast;

import com.example.jiemian.R;
import com.example.jiemian.base.BaseActivity;
import com.example.jiemian.view.CustomerVideoView;

import butterknife.BindView;

/**
 * @author admin
 */
public class VideoActivity extends BaseActivity {
    @BindView(R.id.video)
    CustomerVideoView videoView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void init() {
        String path = getIntent().getStringExtra("detail");
        //设置视频控制器
        videoView.setMediaController(new MediaController(this));

        //播放完成回调
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());
        //设置视频路径
        System.out.println(path);
        videoView.setVideoPath(path);
        //开始播放视频
        videoView.start();
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( VideoActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }
}
