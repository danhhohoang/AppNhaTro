package com.example.appnhatro;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.user;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TenantAccountActivity extends AppCompatActivity {
    Button btnPhongdaluu_TA, btnLienhe_TA, btnDangxuat_TA, btnCaidat_TA, btnDieuKhoan_TA, btnBaidang_TA;
    private user mUsers;
    TextView tvHoten_TA, tvIdaccount_TA;
    ImageButton ivbtnBack_TA;
    public static final String NAME = "NAME";
    public static final String ID = "ID";
    public static final String BUNDLE = "BUNDLE";
    public static final String PASS = "PASS";
    Dialog progressDialog;
    CircleImageView ivAccount_TA;
    String id, pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_account);
        getPutExtra();
        setControl();
        setEvent();
        onRead();
    }

    private void onRead() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading....");
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/"+id);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers = snapshot.getValue(user.class);
                String avt = mUsers.getAvatar();
                setImage(ivAccount_TA, avt);
                do {
                    progressDialog.dismiss();
                    tvHoten_TA.setText(mUsers.getName());
                    tvIdaccount_TA.setText(mUsers.getId());

                } while (ivAccount_TA.getDrawable().toString().equals(mUsers.getAvatar()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TenantAccountActivity.this, "Get list user faild", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static final void setImage(CircleImageView imageView, String avatar) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/user/" + avatar);
        try {
            final File file = File.createTempFile("ảnh", ".jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Notify", "Load Image Fail");

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setEvent() {
        btnPhongdaluu_TA.setOnClickListener(click -> {
            Intent intent = new Intent(TenantAccountActivity.this, TenantPostFavourite.class);
            startActivity(intent);
        });
        btnLienhe_TA.setOnClickListener(click -> {
            Intent intent = new Intent(TenantAccountActivity.this, TenantContactActivity.class);
            startActivity(intent);
        });
        btnBaidang_TA.setOnClickListener(click -> {
            Intent intent = new Intent(TenantAccountActivity.this, TenantPostList.class);
            startActivity(intent);
        });
        btnCaidat_TA.setOnClickListener(click -> {
            Intent intent = new Intent(this, TenantSettingActivity.class);
            intent.putExtra(BUNDLE, byBundle());
            startActivity(intent);
        });
        btnDangxuat_TA.setOnClickListener(click -> {
            Intent intent = new Intent(TenantAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        btnDieuKhoan_TA.setOnClickListener(click -> {
        });
        ivbtnBack_TA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void makeText(){
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    }

    private void setControl() {
        btnPhongdaluu_TA = findViewById(R.id.btnPhongdaluu_TA);
        btnDangxuat_TA = findViewById(R.id.btnDangxuat_TA);
        btnLienhe_TA = findViewById(R.id.btnLienhe_TA);
        btnCaidat_TA = findViewById(R.id.btnCaidat_TA);
        btnDieuKhoan_TA = findViewById(R.id.btnDieukhoan_TA);
        btnBaidang_TA = findViewById(R.id.btnBaidang_TA);
        tvHoten_TA = findViewById(R.id.tvHoten_TA);
        tvIdaccount_TA = findViewById(R.id.tvIdaccount_TA);
        ivAccount_TA = findViewById(R.id.ivAccount_TA);
        ivbtnBack_TA = findViewById(R.id.ivbtnBack_TA);
    }

    public Bundle byBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(NAME, mUsers.getName());
        bundle.putString(ID, mUsers.getId());
        bundle.putString(PASS, mUsers.getPassword());
        return bundle;
    }
    private void getPutExtra(){
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
    }
}
