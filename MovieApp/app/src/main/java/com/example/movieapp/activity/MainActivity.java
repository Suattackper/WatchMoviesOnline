package com.example.movieapp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.movieapp.PlayMovieActivity;
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
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


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
        //xóa fragment back trở về reong backstack
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

        //chỉ còn một fragment trong back stack
        if (backStackCount == 1) {
            if (!doubleBackToExitPressedOnce) {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Nhấn back một lần nữa để thoát", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000); // Thiết lập thời gian đợi là 2 giây
            } else {
                finishAffinity(); // Thoát khỏi ứng dụng
            }
        } else {
            super.onBackPressed();
        }
    }
}