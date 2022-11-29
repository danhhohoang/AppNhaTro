package com.example.appnhatro;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Activity.AdminHomeActivity;
import com.example.appnhatro.Activity.LandLordHomeActivity;
import com.example.appnhatro.Activity.TenantViewTermAndPolicyActivity;
import com.example.appnhatro.Activity.TermAndSerciveActivity;
import com.example.appnhatro.Activity.VertifyPhoneNumberActivity;
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
    TextView tvQuenMatKhau, tvDangKyTaiKhoan, tvDieuKhoan;
    Button btnSignIn;
    boolean passwordVisible;
    String formatEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedPreferences sharedPreferences;

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

    public void event() {
        txtEmail = findViewById(R.id.txtTenDangNhap);
        txtPassword = findViewById(R.id.txtMatKhauLogin);
        txtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=txtPassword.getRight()-txtPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = txtPassword.getSelectionEnd();
                        if (passwordVisible){
                            txtPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_hide_pass,0);
                            txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible=false;
                        }else {
                            txtPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_show_pass,0);
                            txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible=true;
                        }
                        txtPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        btnSignIn = findViewById(R.id.btnDangNhap);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);
        tvDangKyTaiKhoan = findViewById(R.id.tvDangKy);
        tvDieuKhoan = findViewById(R.id.tvDieuKhoanVaChinhSach_Login);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean tk = !txtEmail.getText().toString().isEmpty();
                boolean mk = !txtPassword.getText().toString().isEmpty();
                boolean format = txtEmail.getText().toString().matches(formatEmail);
                if (!format) {
                    txtEmail.setError("Email không đúng định dạng");
                }
                if (!tk) {
                    txtEmail.setError("Email không được bỏ trống");
                }
                if (!mk) {
                    txtPassword.setError("Password không được bỏ trống");
                }
                if (tk && mk && format) {
                    String email = txtEmail.getText().toString();
                    String pass = txtPassword.getText().toString();
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    databaseReference.child("user").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean check = false;
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                user User = dataSnapshot.getValue(user.class);
                                if (User.getEmail().equals(email) && User.getPassword().equals(pass) && User.getStatus().equals("0")) {
                                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                                    check = true;
                                    databaseReference.child("User_Role").child(User.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            USER_ROLE userRole = snapshot.getValue(USER_ROLE.class);
                                            if (userRole.getId_role().equals("1")) {
                                                sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("idUser", userRole.getId_user());
                                                editor.apply();
                                                Intent intent = new Intent(LoginActivity.this, HomeTenantActivity.class);
                                                startActivity(intent);
                                            } else if (userRole.getId_role().equals("2")) {
                                                sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("idUser", userRole.getId_user());
                                                editor.apply();
                                                Intent intent = new Intent(LoginActivity.this, LandLordHomeActivity.class);
                                                startActivity(intent);
                                            } else {
                                                sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("idUser", userRole.getId_user());
                                                editor.apply();
                                                Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    break;
                                }
                            }
                            if (!check) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setTitle("Thông báo");
                                builder.setMessage("Tài khoản hoặc mật khẩu không đúng");
                                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.show();
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, VertifyPhoneNumberActivity.class);
                txtEmail.getText().clear();
                txtPassword.getText().clear();
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
                Intent intent = new Intent(LoginActivity.this, TenantViewTermAndPolicyActivity.class);
                startActivity(intent);
            }
        });
    }
}