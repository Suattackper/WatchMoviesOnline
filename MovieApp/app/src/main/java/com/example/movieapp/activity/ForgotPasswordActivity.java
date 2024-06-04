package com.example.movieapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.databinding.ActivityForgotPasswordBinding;
import com.example.movieapp.model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setMessage("Đang xử lý..."); // Thiết lập thông điệp
        progressDialog.setCancelable(false); // Không thể hủy bỏ hộp thoại bằng cách nhấn ngoài hoặc nút back


        addEvents();
    }

    private void addEvents() {
//        binding.imvBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                finish();
//            }
//        });
        binding.txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                finishAffinity();
            }
        });
        binding.btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editEmail.getText().toString().trim();
                if(email.equals("") || email==null){
                    Toast.makeText(ForgotPasswordActivity.this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$").matcher(email).matches()){
                    Toast.makeText(ForgotPasswordActivity.this, "Email không đúng!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.show();

                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Account");
                Query query = myRef.orderByChild("email").equalTo(email);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            //email tồn tại
                            DataSnapshot firstSnapshot = dataSnapshot.getChildren().iterator().next();
                            Account account = firstSnapshot.getValue(Account.class);

                            FirebaseAuth mAuth = FirebaseAuth.getInstance();

                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Account/" + account.getIndex());
                            myRef.setValue(account);
                            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressDialog.setMessage("Vui lòng kiểm tra email của bạn!");
                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.dismiss();
                                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                finishAffinity();
                                            }
                                        }, 5000); // Tạm dừng 2 giây
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(ForgotPasswordActivity.this, "Xảy ra lỗi gửi reset password!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(ForgotPasswordActivity.this, "Email chưa được đăng kí!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, "Lấy dữ liệu email từ firebase thất bại!",
                                Toast.LENGTH_SHORT).show();
                    }
                });












            }
        });
    }
}