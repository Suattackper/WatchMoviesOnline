package com.example.movieapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.movieapp.CategoryFragment;
import com.example.movieapp.PlayMovieActivity;
import com.example.movieapp.R;
import com.example.movieapp.adapter.MovieHomeAdapter;
import com.example.movieapp.adapter.MovieHomeCategoryAdapter;
import com.example.movieapp.adapter.MovieSearchItemAdapter;
import com.example.movieapp.api.ApiService;
import com.example.movieapp.databinding.FragmentHomeBinding;
import com.example.movieapp.databinding.FragmentSearchBinding;
import com.example.movieapp.model.Account;
import com.example.movieapp.model.AccountList;
import com.example.movieapp.model.Category;
import com.example.movieapp.model.Item;
import com.example.movieapp.model.Movie;
import com.example.movieapp.model.MovieDetail;
import com.example.movieapp.model.MovieList;
import com.example.movieapp.model.MovieSearch;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    Item item = new Item();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        //return inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        addEvents();
//        addEvents();
        getData();

        //DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Account");
        //Query query = myRef.orderByChild("AccountCode").equalTo(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Lắng nghe sự kiện khi dữ liệu thay đổi
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Account");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Account> accounts = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Account account = snapshot.getValue(Account.class);
                            accounts.add(account);
                        }
                        // Xủ lý dữ liệu sau khi lấy được danh sách
                        Account account = new Account();
                        int i = 0;
                        for (Account item:accounts
                        ) {
                            if (item.getAccountCode()==0){
                                account=item;
                                break;
                            }
                            i++;
                        }

                        //set image bằng picasso
                        String imageUrl = account.getImage();
                        Picasso.get().load(imageUrl).into(binding.imvUserAvatar);
                        // Lưu trữ giá trị
                        Gson gson = new Gson();
                        String json = gson.toJson(account);

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear(); // Xóa tất cả dữ liệu trong SharedPreferences
                        editor.putString("jsonaccount", json);
                        editor.putString("index", String.valueOf(i));
                        editor.apply();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Xử lý nếu có lỗi xảy ra
                    }
                });
            }
        }).start();

        return view;
    }

    private void addEvents() {
        binding.btnPhimBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phimBo();
            }
        });
        binding.btbtvPhimBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phimBo();
            }
        });
        binding.btnPhimLe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phimLe();
            }
        });
        binding.btbtvPhimLe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phimLe();
            }
        });
        binding.btnTVShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvShows();
            }
        });
        binding.btbtvTVShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvShows();
            }
        });
        binding.btnAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anime();
            }
        });
        binding.btbtvAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anime();
            }
        });
        binding.imvUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi tạo Fragment
                MoreFragment fragment = new MoreFragment();

                // Truy cập FragmentManager từ FragmentActivity hoặc AppCompatActivity và thực hiện giao dịch
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment); // frameLayout là id của container cho Fragment
                transaction.addToBackStack(null); // (Optional) Đưa Fragment vào Stack để quay lại
                transaction.commit();
            }
        });
        binding.btnMyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi tạo Fragment
                WatchListFragment fragment = new WatchListFragment();

                // Truy cập FragmentManager từ FragmentActivity hoặc AppCompatActivity và thực hiện giao dịch
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment); // frameLayout là id của container cho Fragment
                transaction.addToBackStack(null); // (Optional) Đưa Fragment vào Stack để quay lại
                transaction.commit();
            }
        });
        binding.btnInfoMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getSlug()==null) {
                    Toast.makeText(getContext(), "Vui lòng đợi tải dữ liệu hoàn thành!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(item.getSlug().equals("")) {
                    Toast.makeText(getContext(), "Dữ liệu có vấn đề!", Toast.LENGTH_SHORT).show();
                    return;
                }
                MovieDetailFragment fragment = new MovieDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("slug", item.getSlug());
                fragment.setArguments(bundle);

                // Truy cập FragmentManager từ FragmentActivity hoặc AppCompatActivity và thực hiện giao dịch
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment); // frameLayout là id của container cho Fragment
                transaction.addToBackStack(null); // (Optional) Đưa Fragment vào Stack để quay lại
                transaction.commit();
            }
        });
        binding.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getSlug()==null) {
                    Toast.makeText(getContext(), "Vui lòng đợi tải dữ liệu hoàn thành!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(item.getSlug().equals("")) {
                    Toast.makeText(getContext(), "Dữ liệu có vấn đề!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ApiService.apiService.getMovieBySlug(item.getSlug()).enqueue(new Callback<MovieDetail>() {
                    @Override
                    public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                        if (response.isSuccessful()) {
                            MovieDetail moviedetail = response.body();
                            Intent intent = new Intent(getActivity(), PlayMovieActivity.class);
                            intent.putExtra("m3u8", moviedetail.getEpisodes().get(0).getServer_data().get(0).getLink_m3u8());
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Có lỗi xảy raz", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDetail> call, Throwable throwable) {
                        Toast.makeText(getContext(), "Có lỗi xảy razz", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                });
            }
        });
    }

    private void anime() {
        // Khởi tạo Fragment và truyền dữ liệu nếu cần
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", "Anime");
        bundle.putString("danhsach", "hoat-hinh");
        fragment.setArguments(bundle);

        // Truy cập FragmentManager từ FragmentActivity hoặc AppCompatActivity và thực hiện giao dịch
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment); // frameLayout là id của container cho Fragment
        transaction.addToBackStack(null); // (Optional) Đưa Fragment vào Stack để quay lại
        transaction.commit();
    }

    private void tvShows() {
        // Khởi tạo Fragment và truyền dữ liệu nếu cần
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", "TVShows");
        bundle.putString("danhsach", "tv-shows");
        fragment.setArguments(bundle);

        // Truy cập FragmentManager từ FragmentActivity hoặc AppCompatActivity và thực hiện giao dịch
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment); // frameLayout là id của container cho Fragment
        transaction.addToBackStack(null); // (Optional) Đưa Fragment vào Stack để quay lại
        transaction.commit();
    }

    private void phimLe() {
        // Khởi tạo Fragment và truyền dữ liệu nếu cần
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", "Phim lẻ");
        bundle.putString("danhsach", "phim-le");
        fragment.setArguments(bundle);

        // Truy cập FragmentManager từ FragmentActivity hoặc AppCompatActivity và thực hiện giao dịch
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment); // frameLayout là id của container cho Fragment
        transaction.addToBackStack(null); // (Optional) Đưa Fragment vào Stack để quay lại
        transaction.commit();
    }

    private void phimBo() {
        // Khởi tạo Fragment và truyền dữ liệu nếu cần
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", "Phim bộ");
        bundle.putString("danhsach", "phim-bo");
        fragment.setArguments(bundle);

        // Truy cập FragmentManager từ FragmentActivity hoặc AppCompatActivity và thực hiện giao dịch
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment); // frameLayout là id của container cho Fragment
        transaction.addToBackStack(null); // (Optional) Đưa Fragment vào Stack để quay lại
        transaction.commit();
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getlistnewupdate(binding.rcvPhimMoiCatNhat);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getlistcategory("phim-le",binding.rcvPhimLe);
            }
        }).start();new Thread(new Runnable() {
            @Override
            public void run() {
                getlistcategory("phim-bo",binding.rcvPhimBo);
            }
        }).start();new Thread(new Runnable() {
            @Override
            public void run() {
                getlistcategory("tv-shows",binding.rcvTVShow);
            }
        }).start();new Thread(new Runnable() {
            @Override
            public void run() {
                getlistcategory("hoat-hinh",binding.rcvAnime);
            }
        }).start();
//        getlistnewupdate(binding.rcvPhimMoiCatNhat);
//        getlistcategory("phim-le",binding.rcvPhimLe);
//        getlistcategory("phim-bo",binding.rcvPhimBo);
//        getlistcategory("tv-shows",binding.rcvTVShow);
//        getlistcategory("hoat-hinh",binding.rcvAnime);
    }

    private void getlistnewupdate(RecyclerView rcv) {
        ApiService.apiService.getMovieList(1).enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    try {
                        MovieList movielist = response.body();
                        item = movielist.getItems().get(0);

                        String imageUrl = item.getThumb_url();
                        Picasso.get().load(imageUrl).into(binding.imvBanner);

                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

                        MovieHomeAdapter adapter = new MovieHomeAdapter(movielist.getItems(), getContext());
                        rcv.setLayoutManager(manager);
                        rcv.setAdapter(adapter);

                    }
                    catch (Exception e){
                        Log.e("HomeFragment", e.getMessage());
                    }
                } else {
                    Toast.makeText(getContext(), "Có lỗi xảy rafff", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MovieList> call, Throwable throwable) {
                Toast.makeText(getContext(), "Có lỗi xảy raafff", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getlistcategory(String s, RecyclerView rcv) {
        ApiService.apiService.getMovieListCategory(s,1).enqueue(new Callback<MovieSearch>() {
            @Override
            public void onResponse(Call<MovieSearch> call, Response<MovieSearch> response) {
                if (response.isSuccessful()) {
                    try {
                        MovieSearch movieSearch = response.body();

                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

                        MovieHomeCategoryAdapter adapter = new MovieHomeCategoryAdapter(movieSearch.getData().getItems(), getContext(),movieSearch.getData().getAPP_DOMAIN_CDN_IMAGE());

                        rcv.setLayoutManager(manager);
                        rcv.setAdapter(adapter);

                    }
                    catch (Exception e){
                        Log.e("HomeFragment", e.getMessage());
                    }
                } else {
                    Toast.makeText(getContext(), "Có lỗi xảy ra1", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MovieSearch> call, Throwable throwable) {
                Toast.makeText(getContext(), "Có lỗi xảy raa1", Toast.LENGTH_SHORT).show();
            }
        });
    }
}