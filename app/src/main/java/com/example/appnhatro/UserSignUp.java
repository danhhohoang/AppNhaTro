package com.example.appnhatro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.user;

public class UserSignUp extends AppCompatActivity {
    EditText name,email,password,phone;
    Button signup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_signup);

        name = findViewById(R.id.edt_suName);
        email = findViewById(R.id.edt_suEmail);
        password = findViewById(R.id.edt_suPass);
        phone = findViewById(R.id.edt_suSDT);
        signup = findViewById(R.id.btn_suSignUp);
        DAOUser dao = new DAOUser();

        signup.setOnClickListener(v-> {
            user um =new user("US01",name.getText().toString(),email.getText().toString(),phone.getText().toString(),password.getText().toString(),"test","test");
            dao.add(um).addOnSuccessListener(suc->{
                Toast.makeText(this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,TenantPostFavourite.class);
                startActivity(intent);
            }).addOnFailureListener(er->{
                Toast.makeText(this,"Đăng ký không thành công",Toast.LENGTH_SHORT).show();
            });
        });
    }
}
