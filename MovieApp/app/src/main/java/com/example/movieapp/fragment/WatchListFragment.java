package com.example.movieapp.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.movieapp.adapter.ListMovieAdapter;
import com.example.movieapp.adapter.MovieImageCategoryAdapter;
import com.example.movieapp.databinding.FragmentWatchListBinding;
import com.example.movieapp.model.Account;
import com.example.movieapp.model.AccountList;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class WatchListFragment extends Fragment {

    FragmentWatchListBinding binding;
    MovieImageCategoryAdapter movieImageCategoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentWatchListBinding.inflate(inflater, container,false);

        initUI();

       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initUI() {

        // Nhận chuỗi JSON từ SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String json = sharedPreferences.getString("jsonaccount", "");
        String index = sharedPreferences.getString("index", "");
        // Chuyển đổi chuỗi JSON thành object
        Gson gson = new Gson();
        Account account = gson.fromJson(json, Account.class);

        if(account.getAccountList()!=null){
            List<AccountList> accountLists = new ArrayList<>();
            for (AccountList item: account.getAccountList()) {
                accountLists.add(item);
            }
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            ListMovieAdapter adapter = new ListMovieAdapter(accountLists, getContext(),account,index);
            binding.rcvWatchList.setAdapter(adapter);
            binding.rcvWatchList.setLayoutManager(manager);
        }
        else {
            binding.tvWatchList.setText("Watch List chưa có phim!");
        }
    }
}