package com.example.movieapp;

import static com.google.common.io.Files.getFileExtension;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieapp.activity.LoginActivity;
import com.example.movieapp.activity.MainActivity;
import com.example.movieapp.activity.RegisterActivity;
import com.example.movieapp.databinding.ActivityEditProfileAccountBinding;
import com.example.movieapp.model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class EditProfileAccountActivity extends AppCompatActivity {
    ActivityEditProfileAccountBinding binding;
    ProgressDialog progressDialog;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_edit_profile_account);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        binding = ActivityEditProfileAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(EditProfileAccountActivity.this);
        progressDialog.setMessage("Đang xử lý..."); // Thiết lập thông điệp
        progressDialog.setCancelable(false); // Không thể hủy bỏ hộp thoại bằng cách nhấn ngoài hoặc nút back

        getData();
        addEvents();

    }
    //set ảnh khi chọn tệp xong
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.imvUserAvatar.setImageURI(imageUri);
        }
    }

    private void getData() {
        // Nhận chuỗi JSON từ SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EditProfileAccountActivity.this);
        String json = sharedPreferences.getString("jsonaccount", "");

        // Chuyển đổi chuỗi JSON thành object
        Gson gson = new Gson();
        Account account = gson.fromJson(json, Account.class);

        //set image bằng picasso
        String imageUrl = account.getImageUrl();
        Picasso.get().load(imageUrl).into(binding.imvUserAvatar);

        binding.editEmail.setText(account.getEmail());
        binding.editUserName.setText(account.getName());
        binding.editPhone.setText(account.getPhoneNumber());
    }
    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.editImvUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");  // Chỉ định loại tệp là hình ảnh
                intent.setAction(Intent.ACTION_GET_CONTENT);  // Mở bộ chọn tệp để chọn nội dung
                startActivityForResult(intent, 1);  // Khởi động Activity để chọn ảnh, với mã yêu cầu là 1
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editUserName.getText().toString().trim();
                String phone = binding.editPhone.getText().toString().trim();

                if(name.equals("") || name == null){
                    Toast.makeText(EditProfileAccountActivity.this, "Tên không được trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!phone.matches("^(03|05|07|08|09)\\d{8}$") && !phone.equals("")) {
                    Toast.makeText(EditProfileAccountActivity.this, "Số điện thoại không đúng!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Nhận chuỗi JSON từ SharedPreferences
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EditProfileAccountActivity.this);
                String json = sharedPreferences.getString("jsonaccount", "");

                // Chuyển đổi chuỗi JSON thành object
                Gson gson = new Gson();
                Account account = gson.fromJson(json, Account.class);
                if (name.equals(account.getName()) && phone.equals(account.getPhoneNumber()) && imageUri == null) {
                    Toast.makeText(EditProfileAccountActivity.this, "Không có gì thay đổi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show();

                if (imageUri != null) {
                    // Lấy tham chiếu tới Firebase Storage
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();

                    //xóa ảnh cũ
                    if(!account.getImageName().equals("")){
                        StorageReference storageRefDel = storage.getReference().child(account.getImageName());
                        // Delete the file
                        storageRefDel.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // File deleted successfully
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Uh-oh, an error occurred!
                                Toast.makeText(EditProfileAccountActivity.this, "Xóa ảnh cũ thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    String pathString = "ImageUsers/" + System.currentTimeMillis()  + "." + getFileExtension(imageUri);

                    // Tạo tham chiếu tới tệp ảnh (có thể thay đổi đường dẫn và tên tệp)
                    StorageReference fileRef = storageRef.child(pathString);

                    // Tải ảnh lên
                    fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //tải ảnh lên storagefirebase thành công
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    if (user != null) {
                                        // Cập nhật thông tin người dùng
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name)
                                                .setPhotoUri(Uri.parse(uri.toString()))
                                                .build();

                                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    account.setName(name);
                                                    account.setPhoneNumber(phone);
                                                    account.setImageUrl(uri.toString());
                                                    account.setImageName(pathString);

                                                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Account/" + account.getIndex());
                                                    myRef.setValue(account);

                                                    Gson gson = new Gson();
                                                    String json = gson.toJson(account);

                                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EditProfileAccountActivity.this);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.clear(); // Xóa tất cả dữ liệu trong SharedPreferences
                                                    editor.putString("jsonaccount", json);
                                                    editor.apply();

                                                    progressDialog.dismiss();

                                                    // Đăng ký và cập nhật thành công
                                                    Toast.makeText(EditProfileAccountActivity.this, "Cập nhật thông tin người dùng thành công!", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(EditProfileAccountActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                } else {
                                                    progressDialog.dismiss();
                                                    // Cập nhật thông tin người dùng thất bại
                                                    Toast.makeText(EditProfileAccountActivity.this, "Cập nhật thông tin người dùng thất bại!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(EditProfileAccountActivity.this, "Lỗi khi lấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditProfileAccountActivity.this, "Tải ảnh vào storagefirebase thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = mAuth.getCurrentUser();

                    if (user != null) {
                        // Cập nhật thông tin người dùng
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/movieapp-2f052.appspot.com/o/ImageUsers%2Fprofile_1.png?alt=media&token=d74a20ea-f921-4822-bb95-480b824db3d0"))
                                .build();

                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    account.setName(name);
                                    account.setPhoneNumber(phone);
                                    account.setImageName("");

                                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Account/" + account.getIndex());
                                    myRef.setValue(account);

                                    Gson gson = new Gson();
                                    String json = gson.toJson(account);

                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EditProfileAccountActivity.this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear(); // Xóa tất cả dữ liệu trong SharedPreferences
                                    editor.putString("jsonaccount", json);
                                    editor.apply();
                                    Log.d("SharedPreferences", "SharedPreferences saved: " + json);

                                    progressDialog.dismiss();

                                    // Đăng ký và cập nhật thành công
                                    Toast.makeText(EditProfileAccountActivity.this, "Cập nhật thông tin người dùng thành công!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditProfileAccountActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {
                                    progressDialog.dismiss();
                                    // Cập nhật thông tin người dùng thất bại
                                    Toast.makeText(EditProfileAccountActivity.this, "Cập nhật thông tin người dùng  kathất bại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(EditProfileAccountActivity.this, "Lỗi khi lấy thông tin người dùng ka!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}