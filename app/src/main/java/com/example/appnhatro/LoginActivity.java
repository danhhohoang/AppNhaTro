package com.example.appnhatro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail;
    EditText txtPassword;
    TextView tvQuenMatKhau;
    TextView tvDangKyTaiKhoan;
    TextView tvDieuKhoan;
    Button btnSignIn;
    String formatEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_login);
        event();
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
                            if(User.getEmail().equals(email)&&User.getPassword().equals(pass)){
                                Intent intent = new Intent(LoginActivity.this,HomeTenantActivity.class);
                                startActivity(intent);
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
    }
}