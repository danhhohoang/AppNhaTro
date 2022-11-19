package com.example.appnhatro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnhatro.Activity.LandLordHomeActivity;
import com.example.appnhatro.Activity.TermAndSerciveActivity;
import com.example.appnhatro.Activity.VertifyPhoneNumberActivity;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.USER_ROLE;
import com.example.appnhatro.Models.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText txtEmail;
    EditText txtPassword;
    TextView tvQuenMatKhau,tvDangKyTaiKhoan,tvDieuKhoan;
    Button btnSignIn;
    String formatEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_login);
        event();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

    }
    public void event(){
        txtEmail=findViewById(R.id.txtTenDangNhap);
        txtPassword=findViewById(R.id.txtMatKhauLogin);
        btnSignIn=findViewById(R.id.btnDangNhap);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);
        tvDangKyTaiKhoan=findViewById(R.id.tvDangKy);
        tvDieuKhoan=findViewById(R.id.tvDieuKhoanVaChinhSach_Login);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String pass = txtPassword.getText().toString();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();
                databaseReference.child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            user User = dataSnapshot.getValue(user.class);
                            if(User.getEmail().equals(email)&&User.getPassword().equals(pass)&&User.getStatus().equals("0")){
                                DatabaseReference databaseReference = firebaseDatabase.getReference();
                                databaseReference.child("User_Role").child(User.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        USER_ROLE userRole = snapshot.getValue(USER_ROLE.class);
                                        if(userRole.getId_role().equals("1")){
                                            Intent intent = new Intent(LoginActivity.this,HomeTenantActivity.class);
                                            startActivity(intent);
                                        }else if(userRole.getId_role().equals("2")){
                                            Intent intent = new Intent(LoginActivity.this, LandLordHomeActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(LoginActivity.this, "Admin", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }else{
                                if (email.isEmpty()) {
                                    txtEmail.setError("Email không được bỏ trống");
                                } if (!email.matches(formatEmail)) {
                                    txtEmail.setError("Email không đúng định dạng");
                                } else if(pass.isEmpty()) {
                                    txtPassword.setError("Password không được để trống");
                                }else {
                                    txtEmail.setError("Email hoặc password không đúng");
                                    txtPassword.setError("Email hoặc password không đúng");
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtEmail.getText().toString())){
                    txtEmail.setError("Cần nhập tên đăng nhập");
                    return;
                }
                Intent intent = new Intent(LoginActivity.this, VertifyPhoneNumberActivity.class);
                intent.putExtra("email_check",txtEmail.getText().toString());
                startActivity(intent);
            }
        });

        tvDangKyTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, UserSignUp.class);
                startActivity(intent);
            }
        });

        tvDieuKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, TermAndSerciveActivity.class);
                startActivity(intent);
            }
        });
    }
}