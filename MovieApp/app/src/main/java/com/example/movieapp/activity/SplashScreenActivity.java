package com.example.movieapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.PlayMovieActivity;
import com.example.movieapp.databinding.ActivitySplashScreenBinding;
import com.example.movieapp.model.Account;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class SplashScreenActivity extends AppCompatActivity {

    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_splash_screen);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Account");
            Query query = myRef.orderByChild("accountCode").equalTo(user.getUid());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        DataSnapshot firstSnapshot = dataSnapshot.getChildren().iterator().next();
                        Account account = firstSnapshot.getValue(Account.class);

                        if (account != null) {
                            if(account.getStatus()){
                                // Lưu trữ giá trị
                                Gson gson = new Gson();
                                String json = gson.toJson(account);

                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashScreenActivity.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear(); // Xóa tất cả dữ liệu trong SharedPreferences
                                editor.putString("jsonaccount", json);
                                editor.apply();

//                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                    finishAffinity();
//                                }, 4500);

                                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                            else{
                                Intent intent = new Intent(SplashScreenActivity.this, WelcomeScreenActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                        }
                    }
                    else{
                        Toast.makeText(SplashScreenActivity.this, "Không tìm thấy dữ liệu tài khoản!",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SplashScreenActivity.this, WelcomeScreenActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(SplashScreenActivity.this, "Lấy dữ liệu tài khoản từ firebase thất bại!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreenActivity.this, WelcomeScreenActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });
        }
        else{
            Intent intent = new Intent(SplashScreenActivity.this, WelcomeScreenActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    }
}