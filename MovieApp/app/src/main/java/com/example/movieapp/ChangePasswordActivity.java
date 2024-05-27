package com.example.movieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movieapp.activity.ForgotPasswordActivity;
import com.example.movieapp.activity.LoginActivity;
import com.example.movieapp.activity.MainActivity;
import com.example.movieapp.activity.RegisterActivity;
import com.example.movieapp.databinding.ActivityChangePasswordBinding;
import com.example.movieapp.model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class ChangePasswordActivity extends AppCompatActivity {
    ActivityChangePasswordBinding binding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_change_password);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(ChangePasswordActivity.this);
        progressDialog.setMessage("Đang xử lý..."); // Thiết lập thông điệp
        progressDialog.setCancelable(false); // Không thể hủy bỏ hộp thoại bằng cách nhấn ngoài hoặc nút back

        addEvents();

    }

    private void addEvents() {
        binding.txtCreateAcc.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
        });

        binding.txtForgotPass.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            finish();
        });

        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passnew = binding.editPasswordNew.getText().toString().trim();
                String passold = binding.editPasswordCur.getText().toString().trim();
                String passnewcf = binding.editPasswordNewConfirm.getText().toString().trim();

                if(passnew.equals("") || passnew==null || passold.equals("") || passold==null || passnewcf.equals("") || passnewcf==null){
                    Toast.makeText(ChangePasswordActivity.this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra độ dài của mật khẩu (ít nhất 6 ký tự)
                if (passnew.length() < 6 || passold.length() < 6 || passnewcf.length() < 6) {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra xem mật khẩu có chứa ít nhất một chữ cái viết hoa
                if (!passnewcf.matches(".*[A-Z].*") || !passnew.matches(".*[A-Z].*") || !passold.matches(".*[A-Z].*")) {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu phải chứa ít nhất một chữ cái viết hoa!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra xem mật khẩu có chứa ít nhất một chữ cái viết thường
                if (!passnewcf.matches(".*[a-z].*") || !passnew.matches(".*[a-z].*") || !passold.matches(".*[a-z].*")) {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu phải chứa ít nhất một chữ cái viết thường!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra xem mật khẩu có chứa ít nhất một ký tự đặc biệt
                if (!passnewcf.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*") || !passnew.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*") || !passold.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu phải chứa ít nhất một ký tự đặc biệt!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Nhận chuỗi JSON từ SharedPreferences
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChangePasswordActivity.this);
                String json = sharedPreferences.getString("jsonaccount", "");

                // Chuyển đổi chuỗi JSON thành object
                Gson gson = new Gson();
                Account account = gson.fromJson(json, Account.class);

                if(!passold.equals(account.getPassword())){
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu hiện tại không đúng!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passold.equals(passnew)){
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới phải khác mật khẩu hiện tại!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!passnew.equals(passnewcf)){
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới khác mật khẩu mới xác nhận" +
                            "!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(account.getEmail(), account.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.updatePassword(passnew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Mật khẩu đã được cập nhật thành công
                                        account.setPassword(passnew);
                                        Gson gson = new Gson();
                                        String json = gson.toJson(account);

                                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChangePasswordActivity.this);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.clear(); // Xóa tất cả dữ liệu trong SharedPreferences
                                        editor.putString("jsonaccount", json);
                                        editor.apply();

                                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Account/" + account.getIndex());
                                        myRef.setValue(account);

                                        progressDialog.dismiss();

                                        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    } else {
                                        progressDialog.dismiss();
                                        // Xảy ra lỗi khi cập nhật mật khẩu
                                        Toast.makeText(ChangePasswordActivity.this, "Có lỗi xảy ra khi cập nhật mật khẩu. Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            progressDialog.dismiss();
                            // Xảy ra lỗi khi cập nhật mật khẩu
                            Toast.makeText(ChangePasswordActivity.this, "Có lỗi khi set lại trạng thái đăng ngập!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}