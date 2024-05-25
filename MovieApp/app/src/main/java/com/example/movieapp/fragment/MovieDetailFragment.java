package com.example.movieapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.example.movieapp.PlayMovieActivity;
import com.example.movieapp.R;
import com.example.movieapp.adapter.MovieEpisodesAdapter;
import com.example.movieapp.adapter.MovieSearchItemAdapter;
import com.example.movieapp.api.ApiService;
import com.example.movieapp.databinding.FragmentMovieDetailBinding;
import com.example.movieapp.databinding.FragmentSearchBinding;
import com.example.movieapp.model.Account;
import com.example.movieapp.model.AccountList;
import com.example.movieapp.model.Category;
import com.example.movieapp.model.MovieDetail;
import com.example.movieapp.model.MovieSearch;
import com.example.movieapp.model.ServerData;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {
    FragmentMovieDetailBinding binding;
    String m3u8 = "";
    List<ServerData> serverData;
    MovieDetail moviedetail = new MovieDetail();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(String param1, String param2) {
        MovieDetailFragment fragment = new MovieDetailFragment();
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
        //return inflater.inflate(R.layout.fragment_movie_detail, container, false);
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Lấy dữ liệu từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            String slug = bundle.getString("slug");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    getDataMovie(slug);
                }
            }).start();

//            getDataMovie(slug);
            addEvent();
        }
        return  view;
    }

    private void addEvent() {
        binding.imvBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareContent();
            }
        });
        binding.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi tạo Fragment và truyền dữ liệu nếu cần
//                MoviePlayingFragment fragment = new MoviePlayingFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("m3u8", m3u8);
//                fragment.setArguments(bundle);
//
//                // Truy cập FragmentManager từ FragmentActivity hoặc AppCompatActivity và thực hiện giao dịch
//                FragmentManager manager = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.frameLayout, fragment); // frameLayout là id của container cho Fragment
//                transaction.addToBackStack(null); // (Optional) Đưa Fragment vào Stack để quay lại
//                transaction.commit();

                Intent intent = new Intent(getActivity(), PlayMovieActivity.class);
                intent.putExtra("m3u8", m3u8);
                startActivity(intent);
            }
        });
        binding.tvXemTapPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regex kiêm tra chỗi có phải là số
                if(binding.edtTapPhim.getText().toString().matches("-?\\d+(\\.\\d+)?")){
                    int tap = Integer.valueOf(binding.edtTapPhim.getText().toString());
                    if(serverData.size()>=tap&&tap>0){
                        //chuyển sang màn hình khaác
                        Intent intent = new Intent(getActivity(), PlayMovieActivity.class);
                        //truyền dữ liệu
                        intent.putExtra("m3u8", serverData.get(tap-1).getLink_m3u8());
                        startActivity(intent);
                    }
                    else Toast.makeText(getContext(), "Tập phim không tồn tại", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getContext(), "Tập phim không tồn tại", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nhận chuỗi JSON từ SharedPreferences
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String json = sharedPreferences.getString("jsonaccount", "");
                String index = sharedPreferences.getString("index", "");

                // Chuyển đổi chuỗi JSON thành object
                Gson gson = new Gson();
                Account account = gson.fromJson(json, Account.class);

                boolean check = true;

                AccountList accountList = new AccountList(moviedetail.getMovie().getName(),moviedetail.getMovie().getSlug(),moviedetail.getMovie().getThumb_url());
                if(account.getAccountList()!=null){
                    for (AccountList item: account.getAccountList()
                    ) {
                        if(accountList.getSlug().equals(item.getSlug())) check=false;
                    }
                }
                else{
                    account.setAccountList(new ArrayList<>());
                }

                if(check){
                    account.getAccountList().add(accountList);
                    //Sửa dữ liệu vào firebase
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Account/" + index);
                    myRef.setValue(account);
                    Toast.makeText(getContext(), "Đã thêm vào Watch List!", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getContext(), "Phim đã có trong Watch List!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void shareContent() {
        // Code chia sẻ nội dung tương tự như trong Activity
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = binding.titleMovieName.getText().toString();
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Nội dung chia sẻ:");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Chọn ứng dụng để chia sẻ"));
    }

    private void getDataMovie(String slug) {
        //lấy dữ liệu phim từ slug
        ApiService.apiService.getMovieBySlug(slug).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                if (response.isSuccessful()) {
                    try {
                        moviedetail = response.body();

                        //set image bằng picasso
                        String imageUrl = moviedetail.getMovie().getThumb_url();
                        Picasso.get().load(imageUrl).into(binding.imvBackDrop);

                        serverData = moviedetail.getEpisodes().get(0).getServer_data();
                        String nam = "Năm phát hành: " + String.valueOf(moviedetail.getMovie().getYear()) + "\n";
                        if(moviedetail.getMovie().getStatus().equals("completed")){
                            nam = nam + "Đã hoàn thành";
                        }
                        else nam = nam + "Đang tiến hành";

                        if(moviedetail.getMovie().getType().equals("single")){
                            binding.layoutsingle.setVisibility(View.VISIBLE);
                            binding.rcvDanhSachTap.setVisibility(View.GONE);
                            binding.tvDanhSachTap.setVisibility(View.GONE);
                            m3u8 = moviedetail.getEpisodes().get(0).getServer_data().get(0).getLink_m3u8();
                            nam = nam + " - Phim lẻ";
                        }
                        else if (moviedetail.getMovie().getType().equals("series")) {
                            GetEpisode(moviedetail.getEpisodes().get(0).getServer_data());
                            nam = nam + " - Phim Bộ";
                        }
                        else if (moviedetail.getMovie().getType().equals("tvshows")) {
                            GetEpisode(moviedetail.getEpisodes().get(0).getServer_data());
                            nam = nam + " - TV Shows";
                        }
                        else{
                            nam = nam + " - Anime";
                            if(moviedetail.getMovie().getEpisode_total().equals("1")){
                                binding.layoutsingle.setVisibility(View.VISIBLE);
                                binding.rcvDanhSachTap.setVisibility(View.GONE);
                                binding.tvDanhSachTap.setVisibility(View.GONE);
                                m3u8 = moviedetail.getEpisodes().get(0).getServer_data().get(0).getLink_m3u8();
                            }
                            else GetEpisode(moviedetail.getEpisodes().get(0).getServer_data());
                        }
                        //m3u8 = moviedetail.getEpisodes().get(0).getServer_data().get(0).getLink_m3u8();

                        binding.tvTime.setText(nam);
                        binding.titleMovieName.setText(moviedetail.getMovie().getName());
                        String cate = "";
                        int size = moviedetail.getMovie().getCategory().size();
                        int j = 0;
                        for (Category item: moviedetail.getMovie().getCategory()) {
                            if (j < size - 1) {
                                cate = cate + item.getName() + " - ";
                            }
                            else{
                                cate = cate + item.getName();
                            }
                            j++;
                        }
                        binding.tvGenreMovie.setText(cate);
                        binding.tvOverViewMore.setText(moviedetail.getMovie().getContent());
                        String actor = "";
                        int size1 = moviedetail.getMovie().getActor().size();
                        int i = 0;
                        for (String item: moviedetail.getMovie().getActor()) {
                            if (i < size1 - 1) {
                                actor = actor + item + ", ";
                            }
                            else{
                                actor = actor + item;
                            }
                            i++;
                        }
                        binding.tvActor.setText(actor);
                        String director = "";
                        int size2 = moviedetail.getMovie().getDirector().size();
                        int k = 0;
                        for (String item: moviedetail.getMovie().getDirector()) {
                            if (k < size2 - 1) {
                                director = director + item + ", ";
                            }
                            else{
                                director = director + item;
                            }
                            k++;
                        }
                        binding.tvDirector.setText(director);

                        if(!moviedetail.getMovie().getTrailer_url().equals("")){
                            binding.tabLine.setVisibility(View.VISIBLE);
                            binding.tvTrailer.setVisibility(View.VISIBLE);
                            binding.ytbPlayer.setVisibility(View.VISIBLE);

                            getLifecycle().addObserver(binding.ytbPlayer);

                            //set trailer vào youtubeplayer
                            binding.ytbPlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                    String videoId = extractVideoId(moviedetail.getMovie().getTrailer_url());
                                    youTubePlayer.cueVideo(videoId, 0);
                                }
                            });
                        }
//                        if(moviedetail.getMovie().getType().equals("series")){
//                            binding.tabLine.setVisibility(View.VISIBLE);
//                            binding.tvSimilar.setVisibility(View.VISIBLE);
//                            binding.rcvSimilar.setVisibility(View.VISIBLE);
//
//                        }
                    }
                    catch (Exception e){
                        Log.e("SearchFragment", e.getMessage());
                    }
                } else {
                    Toast.makeText(getContext(), "Có lỗi xảy ral", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable throwable) {
                Toast.makeText(getContext(), "Có lỗi xảy rla", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
    }

    private void GetEpisode(List<ServerData> serverData) {
        binding.layoutsingle.setVisibility(View.GONE);
        binding.rcvDanhSachTap.setVisibility(View.VISIBLE);
        binding.tvDanhSachTap.setVisibility(View.VISIBLE);
        binding.layoutXemTapPhim.setVisibility(View.VISIBLE);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        MovieEpisodesAdapter adapter = new MovieEpisodesAdapter(serverData, getContext());

        binding.rcvDanhSachTap.setLayoutManager(manager);
        binding.rcvDanhSachTap.setAdapter(adapter);
    }

    public static String extractVideoId(String youtubeUrl) {
        String videoId = null;
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0) {
            String query = Uri.parse(youtubeUrl).getQuery();
            if (query != null) {
                String[] queryParams = query.split("&");
                for (String param : queryParams) {
                    String[] paramSplit = param.split("=");
                    if (paramSplit.length > 1 && paramSplit[0].equals("v")) {
                        videoId = paramSplit[1];
                        break;
                    }
                }
            }
            // If videoId still null, try to extract from last part of path
            if (videoId == null || videoId.isEmpty()) {
                String path = Uri.parse(youtubeUrl).getPath();
                if (path != null) {
                    String[] pathSegments = path.split("/");
                    if (pathSegments.length > 0) {
                        videoId = pathSegments[pathSegments.length - 1];
                    }
                }
            }
        }
        return videoId;
    }
}