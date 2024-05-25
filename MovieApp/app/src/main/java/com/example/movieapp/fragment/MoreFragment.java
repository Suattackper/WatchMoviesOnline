package com.example.movieapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.CategoryFragment;
import com.example.movieapp.R;

import com.example.movieapp.databinding.FragmentMoreBinding;
import com.example.movieapp.model.Account;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


public class MoreFragment extends Fragment {

    FragmentMoreBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(inflater, container, false);

        getData();
        addEvents();

        return binding.getRoot();
    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.lnlWatchList.setOnClickListener(new View.OnClickListener() {
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
    }

    private void getData() {
        // Nhận chuỗi JSON từ SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String json = sharedPreferences.getString("jsonaccount", "");

        // Chuyển đổi chuỗi JSON thành object
        Gson gson = new Gson();
        Account account = gson.fromJson(json, Account.class);

        //set image bằng picasso
        String imageUrl = account.getImage();
        Picasso.get().load(imageUrl).into(binding.imvAvatar);

        binding.tvUserName.setText(account.getName());

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}