package com.example.appnhatro;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class LandlordAccountActivity extends AppCompatActivity {
    Button btnPhongdaluu_LA, btnLienhe_LA, btnDangxuat_LA, btnDieuKhoan_LA, btnBaidang_LA,btnCapnhattt_LA, btnThaydoimk_LA;
    private user mUsers;
    TextView tvHoten_LA, tvIdaccount_LA;
    ImageButton ivbtnBack_LA;
    public static final String NAME = "NAME";
    public static final String ID = "ID";
    public static final String BUNDLE = "BUNDLE";
    public static final String PASS = "PASS";
    Dialog progressDialog;
    CircleImageView ivAccount_LA;
    String id, pass;
    public static final String AVATAR = "AVATAR";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_account);
        setControl();
        setEvent();
        onRead();
    }

    private void onRead() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading....");
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/KH01");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers = snapshot.getValue(user.class);
                setImage(ivAccount_LA, mUsers.getAvatar());
                do {
                    progressDialog.dismiss();
                    tvHoten_LA.setText(mUsers.getName());
                    tvIdaccount_LA.setText(mUsers.getId());

                } while (ivAccount_LA.getDrawable().toString().equals(mUsers.getAvatar()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LandlordAccountActivity.this, "Get list user faild", Toast.LENGTH_LONG).show();
            }
        });
    }

    public final void setImage(CircleImageView imageView, String avatar) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/user/" + avatar);
        try {
            final File file = File.createTempFile("ảnh", "jpg");
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
        btnPhongdaluu_LA.setOnClickListener(click -> {
            Intent intent = new Intent(LandlordAccountActivity.this, TenantPostFavourite.class);
            startActivity(intent);
        });
        btnLienhe_LA.setOnClickListener(click -> {
            Intent intent = new Intent(LandlordAccountActivity.this, TenantContactActivity.class);
            startActivity(intent);
        });
        btnBaidang_LA.setOnClickListener(click -> {
            Intent intent = new Intent(LandlordAccountActivity.this, TenantPostList.class);
            startActivity(intent);
        });
//        btnCaidat_LA.setOnClickListener(click -> {
//            Intent intent = new Intent(this, TenantSettingActivity.class);
//            intent.putExtra(BUNDLE, byBundle());
//            startActivity(intent);
//        });
        btnCapnhattt_LA.setOnClickListener(click -> {
            Intent intent = new Intent(LandlordAccountActivity.this, LandlordSettingProfileActivity.class);
            intent.putExtra(BUNDLE, byBundle());
            startActivity(intent);
        });
        btnThaydoimk_LA.setOnClickListener(click -> {
            Intent intent = new Intent(LandlordAccountActivity.this, LandlordPasswordChangeActivity.class);
            intent.putExtra(BUNDLE, byBundle());
            startActivity(intent);
        });
        btnDangxuat_LA.setOnClickListener(click -> {
            Intent intent = new Intent(LandlordAccountActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        btnDieuKhoan_LA.setOnClickListener(click -> {
        });
        ivbtnBack_LA.setOnClickListener(click->{
            finish();
        });
    }

    private void setControl() {
        btnPhongdaluu_LA = findViewById(R.id.btnPhongdaluu_LA);
        btnDangxuat_LA = findViewById(R.id.btnDangxuat_LA);
        btnLienhe_LA = findViewById(R.id.btnLienhe_LA);
        btnDieuKhoan_LA = findViewById(R.id.btnDieukhoan_LA);
        btnBaidang_LA = findViewById(R.id.btnBaidang_LA);
        tvHoten_LA = findViewById(R.id.tvHoten_LA);
        tvIdaccount_LA = findViewById(R.id.tvIdaccount_LA);
        ivAccount_LA = findViewById(R.id.ivAccount_LA);
        btnCapnhattt_LA = findViewById(R.id.btnCapnhattt_LA);
        btnThaydoimk_LA = findViewById(R.id.btnThaydoimk_LA);
        ivbtnBack_LA = findViewById(R.id.ivbtnBack_LA);
    }

    public Bundle byBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(NAME, mUsers.getName());
        bundle.putString(ID, mUsers.getId());
        bundle.putString(PASS, mUsers.getPassword());
        bundle.putString(AVATAR, mUsers.getAvatar());
        return bundle;
    }
}
