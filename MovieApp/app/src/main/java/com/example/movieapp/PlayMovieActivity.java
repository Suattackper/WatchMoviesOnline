package com.example.movieapp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movieapp.databinding.ActivityPlayMovieBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

public class PlayMovieActivity extends AppCompatActivity {
    ActivityPlayMovieBinding binding;
    private ExoPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_movie);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding = ActivityPlayMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ẩn thanh điều hướng
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Lấy dữ liệu extra
        String data = getIntent().getStringExtra("m3u8");
        //Bundle bundle = getArguments();
        if (data != null) {
            String m3u8 = data;
            try{
                // Tìm và tham chiếu đến PlayerView trong layout
//                playerView = findViewById(R.id.pvVideoPlaying);
//                binding.pvVideoPlaying

                // Tạo một ExoPlayer instance
                player = new SimpleExoPlayer.Builder(this).build();

                // Thiết lập Player cho PlayerView
                binding.pvVideoPlaying.setPlayer(player);

                // Tạo một MediaItem từ URL của video
                MediaItem mediaItem = MediaItem.fromUri(m3u8);

                // Đặt MediaItem cho ExoPlayer instance
                player.setMediaItem(mediaItem);

                // Chuẩn bị ExoPlayer
                player.prepare();

                // Bắt đầu phát video
                player.play();
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            }
            catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ExoPlayer", e.getMessage());
            }
        }

    }
    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
}