package com.example.movieapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.databinding.ActivityRegisterBinding;
import com.example.movieapp.model.Account;
import com.example.movieapp.model.AccountList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_register);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Đang tạo tài khoản..."); // Thiết lập thông điệp
        progressDialog.setCancelable(false); // Không thể hủy bỏ hộp thoại bằng cách nhấn ngoài hoặc nút back

        addEvents();
    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WelcomeScreenActivity.class));
                finish();
            }
        });
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editEmail.getText().toString().trim();
                String name = binding.editUserName.getText().toString().trim();
                String pass = binding.editPassword.getText().toString().trim();
                String passcf = binding.editPasswordConfirm.getText().toString().trim();

                if(email.equals("") || email==null || name.equals("") || name==null || pass.equals("") || pass==null || passcf.equals("") || passcf==null){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$").matcher(email).matches()){
                    Toast.makeText(RegisterActivity.this, "Email không đúng!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra độ dài của mật khẩu (ít nhất 6 ký tự)
                if (pass.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra xem mật khẩu có chứa ít nhất một chữ cái viết hoa
                if (!pass.matches(".*[A-Z].*")) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải chứa ít nhất một chữ cái viết hoa!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra xem mật khẩu có chứa ít nhất một chữ cái viết thường
                if (!pass.matches(".*[a-z].*")) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải chứa ít nhất một chữ cái viết thường!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kiểm tra xem mật khẩu có chứa ít nhất một ký tự đặc biệt
                if (!pass.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải chứa ít nhất một ký tự đặc biệt!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!pass.equals(passcf)){
                    Toast.makeText(RegisterActivity.this, "Mật khẩu xác nhận khác mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>(){
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            if (!result.getSignInMethods().isEmpty()) {
                                progressDialog.dismiss();
                                // Có tài khoản nào được đăng ký với địa chỉ email này
                                Toast.makeText(RegisterActivity.this, "Email đã tồn tại!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>(){
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            //mAuth.signOut();

                                            FirebaseUser user = mAuth.getCurrentUser();

                                            Account account = new Account();
                                            account.setAccountCode(user.getUid().toString());
                                            account.setName(name);
                                            account.setEmail(email);
                                            account.setPassword(pass);
                                            account.setStatus(false);
                                            account.setImage("https://firebasestorage.googleapis.com/v0/b/movieapp-2f052.appspot.com/o/ImageUsers%2Fprofile_1.png?alt=media&token=d74a20ea-f921-4822-bb95-480b824db3d0");

                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Account");
                                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    List<Account> accounts = new ArrayList<>();
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        Account account = snapshot.getValue(Account.class);
                                                        accounts.add(account);
                                                    }
                                                    account.setIndex(accounts.size());
                                                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Account/" + accounts.size());
                                                    myRef.setValue(account);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    Toast.makeText(RegisterActivity.this, "Lấy index từ firebase thất bại!", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            if (user != null) {
                                                // Cập nhật thông tin người dùng
                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(name) // Thay đổi tên hiển thị
                                                        .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/movieapp-2f052.appspot.com/o/ImageUsers%2Fprofile_1.png?alt=media&token=d74a20ea-f921-4822-bb95-480b824db3d0")) // Thay đổi URL của ảnh đại diện
                                                        .build();

                                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            progressDialog.dismiss();
                                                            // Đăng ký và cập nhật thành công
                                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        } else {
                                                            progressDialog.dismiss();
                                                            // Cập nhật thông tin người dùng thất bại
                                                            Toast.makeText(RegisterActivity.this, "Cập nhật thông tin người dùng thất bại!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                        else {
                                            progressDialog.dismiss();
                                            // Đăng kí thất bại
                                            Toast.makeText(RegisterActivity.this, "Đăng kí thất bại!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            progressDialog.dismiss();
                            // Xảy ra lỗi trong quá trình kiểm tra
                            Toast.makeText(RegisterActivity.this, "Xảy ra lỗi trong quá trình kiểm tra email đã tồn tại chưa!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        binding.txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }
}