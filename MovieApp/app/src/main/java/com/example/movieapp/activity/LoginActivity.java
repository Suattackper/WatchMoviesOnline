package com.example.movieapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.L;
import com.example.movieapp.databinding.ActivityLoginBinding;
import com.example.movieapp.model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Đang đăng nhập..."); // Thiết lập thông điệp
        progressDialog.setCancelable(false); // Không thể hủy bỏ hộp thoại bằng cách nhấn ngoài hoặc nút back

        addEvents();

    }

    private void addEvents() {

        binding.txtCreateAcc.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
//            finishAffinity();
        });

//        binding.imvBack.setOnClickListener(v -> {
//            startActivity(new Intent(getApplicationContext(), WelcomeScreenActivity.class));
//            finish();
//        });

        binding.txtForgotPass.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
//            finishAffinity();
        });
        binding.btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editEmail.getText().toString().trim();
                String pass = binding.editPassword.getText().toString().trim();

                if(email.equals("") || email==null|| pass.equals("") || pass==null){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$").matcher(email).matches()){
                    Toast.makeText(LoginActivity.this, "Email không đúng!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //đăng nhập thành công
                            FirebaseUser user = mAuth.getCurrentUser();

                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Account");
                            Query query = myRef.orderByChild("accountCode").equalTo(user.getUid());

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        DataSnapshot firstSnapshot = dataSnapshot.getChildren().iterator().next();
                                        Account account = firstSnapshot.getValue(Account.class);

                                        if (account != null) {
                                            // Lưu trữ giá trị
                                            Gson gson = new Gson();
                                            String json = gson.toJson(account);

                                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.clear(); // Xóa tất cả dữ liệu trong SharedPreferences
                                            editor.putString("jsonaccount", json);
                                            editor.apply();

                                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Account/" + account.getIndex());
                                            account.setStatus(true);
                                            myRef.setValue(account);

                                            progressDialog.dismiss();

                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finishAffinity();
                                        }
                                        else{
                                            progressDialog.dismiss();
                                        }
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Không tìm thấy dữ liệu tài khoản!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Lấy dữ liệu tài khoản từ firebase thất bại!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            progressDialog.dismiss();
                            // đăng nhập thất bại
                            Toast.makeText(LoginActivity.this, "Thông tin tài khoản hoặc mật khẩu không chính xác!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}