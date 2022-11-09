package com.example.appnhatro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.user;
import com.example.appnhatro.databinding.ActivityMainBinding;

import java.net.URI;

public class UserSignUp extends AppCompatActivity {
    EditText name,email,password,phone;
    Button signup;
    ImageView image;

    Uri imageUri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_signup);

        openFolder();

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

    public void openFolder(){
        image = findViewById(R.id.iv_suImage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("images/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select image"),100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if(data != null){
                imageUri = data.getData();
                image.setImageURI(imageUri);
            }

        }
    }
}
