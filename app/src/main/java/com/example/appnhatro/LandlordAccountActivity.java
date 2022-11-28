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

import com.example.appnhatro.Activity.StatisticalAdminActivity;
import com.example.appnhatro.Activity.StatisticalLandLordActivity;
import com.example.appnhatro.Models.TransactionModel;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class LandlordAccountActivity extends AppCompatActivity {
    Button btnThonke_LA,btnPhongdaluu_LA, btnLienhe_LA, btnDangxuat_LA, btnDieuKhoan_LA, btnBaidang_LA,btnCapnhattt_LA, btnThaydoimk_LA;
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
    int fee1,fee2,fee3,fee4,fee5,fee6,fee7,fee8,fee9,fee10,fee11,fee12;
    public static final String AVATAR = "AVATAR";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_account);
        setControl();
        data();
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
        btnThonke_LA.setOnClickListener(click -> {
            Intent intent = new Intent(this, StatisticalAdminActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("fee1",fee1);
            bundle.putInt("fee2",fee2);
            bundle.putInt("fee3",fee3);
            bundle.putInt("fee4",fee4);
            bundle.putInt("fee5",fee5);
            bundle.putInt("fee6",fee6);
            bundle.putInt("fee7",fee7);
            bundle.putInt("fee8",fee8);
            bundle.putInt("fee9",fee9);
            bundle.putInt("fee10",fee10);
            bundle.putInt("fee11",fee11);
            bundle.putInt("fee12",fee12);
            intent.putExtra("month",bundle);
            startActivity(intent);
        });
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
        btnThonke_LA = findViewById(R.id.btnThongke_LA);
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

    private void data(){
        DatabaseReference databaseReference1;
        DatabaseReference databaseReference2;
        databaseReference1 = FirebaseDatabase.getInstance().getReference("HistoryTransaction");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Post");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TransactionModel transactionModel = dataSnapshot.getValue(TransactionModel.class);
                        String[] parse = transactionModel.getDate().split("-");
                        String month;
                        month = parse[1];
                        if (month.equals("1")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m1 = 0;
                            m1 += price;
                            fee1 = m1;
                        }
                        if (month.equals("2")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m2 = 0;
                            m2 += price;
                            fee2 = m2;
                        }
                        if (month.equals("3")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m3 = 0;
                            m3 += price;
                            fee3 = m3;
                        }
                        if (month.equals("4")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m4 = 0;
                            m4 += price;
                            fee4 = m4;
                        }
                        if (month.equals("5")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m5 = 0;
                            m5 += price;
                            fee5 = m5;
                        }
                        if (month.equals("6")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m6 = 0;
                            m6 += price;
                            fee6 = m6;
                        }
                        if (month.equals("7")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m7 = 0;
                            m7 += price;
                            fee7 = m7;
                        }
                        if (month.equals("8")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m8 = 0;
                            m8 += price;
                            fee8 = m8;
                        }
                        if (month.equals("9")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m9 = 0;
                            m9 += price;
                            fee9 = m9;
                        }
                        if (month.equals("10")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m10 = 0;
                            m10 += price;
                            fee10 = m10;
                        }
                        if (month.equals("11")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m11 = 0;
                            m11 += price;
                            fee11 = m11;
                        }
                        if (month.equals("12")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m12 = 0;
                            m12 += price;
                            fee12 = m12;
                        }

                }
                Log.d("TAG", "onDataChange: " + fee11);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
