package com.example.movieapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieapp.R;
import com.example.movieapp.databinding.FragmentMovieDetailBinding;
import com.example.movieapp.databinding.FragmentMoviePlayingBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviePlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviePlayingFragment extends Fragment {
    FragmentMoviePlayingBinding binding;
    private ExoPlayer player;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoviePlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoviePlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviePlayingFragment newInstance(String param1, String param2) {
        MoviePlayingFragment fragment = new MoviePlayingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMoviePlayingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Lấy dữ liệu từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String m3u8 = bundle.getString("m3u8");
            try{
                // Tìm và tham chiếu đến PlayerView trong layout
//                playerView = findViewById(R.id.pvVideoPlaying);
//                binding.pvVideoPlaying
                //ẩn tavs vụ và điều hướdddieneienj thoại
                binding.pvVideoPlaying.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
                // Tạo một ExoPlayer instance
                player = new SimpleExoPlayer.Builder(requireContext()).build();

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
            }
            catch (Exception e){
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ExoPlayer", e.getMessage());
            }
        }
        return view;
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