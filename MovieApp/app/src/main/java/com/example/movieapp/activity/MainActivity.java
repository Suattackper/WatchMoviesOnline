package com.example.movieapp.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.movieapp.R;
import com.example.movieapp.databinding.ActivityMainBinding;
import com.example.movieapp.fragment.DownloadFragment;
import com.example.movieapp.fragment.EditAccountInfoFragment;
import com.example.movieapp.fragment.HomeFragment;
import com.example.movieapp.fragment.MoreFragment;
import com.example.movieapp.fragment.SearchFragment;
import com.example.movieapp.fragment.WatchListFragment;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        replaceFragmentUI(new HomeFragment());
        addEventsOnClick();

    }

    private void addEventsOnClick() {
        binding.botNav.setOnItemSelectedListener(item -> {
            int itemID = item.getItemId();

            if (itemID == R.id.btnHome) {
                replaceFragmentUI(new HomeFragment());
            }
            else if (itemID == R.id.btnSearch) {
                replaceFragmentUI(new SearchFragment());
            }
            else if (itemID == R.id.btnDownload) {
                replaceFragmentUI(new WatchListFragment());
            }
            else if (itemID == R.id.btnMore) {
                replaceFragmentUI(new MoreFragment());
            }
            return true;
        });

    }

    public void replaceFragmentUI (Fragment fragment) {
//        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}