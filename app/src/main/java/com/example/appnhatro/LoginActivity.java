package com.example.appnhatro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail;
    EditText txtPassword;
    TextView tvQuenMatKhau;
    TextView tvDangKyTaiKhoan;
    TextView tvDieuKhoan;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_login);
    }
    public void event(){
        txtEmail=findViewById(R.id.txtTenDangNhap);
        txtPassword=findViewById(R.id.txtMatKhauLogin);
        btnSignIn=findViewById(R.id.btnDangNhap);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);
        tvDangKyTaiKhoan=findViewById(R.id.tvDangKy);
        tvDieuKhoan=findViewById(R.id.tvDieuKhoanVaChinhSach_Login);
    }
}