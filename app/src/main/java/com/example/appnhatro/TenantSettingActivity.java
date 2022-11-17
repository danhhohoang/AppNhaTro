package com.example.appnhatro;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TenantSettingActivity extends AppCompatActivity {
    ImageView ivBack_TS;
    private TextView tvHoten_TS, tvIdaccount_TS;
    Button btnCapnhattt_TS, btnThaydoimk_TS;
    private user mUsers;
    public static final String ID = "ID";
    public static final String AVATAR = "AVATAR";
    public static final String BUNDLE = "BUNDLE";
    private String hoten, id, avatar;
    CircleImageView ivAccount_TS;
    Dialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_settings);
        setControl();
        getBundle();
        setEvent();
        onRead();

    }
    private void  onRead() {
        TenantAccountActivity tenantAccountActivity = new TenantAccountActivity();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading....");
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/" + id);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers = snapshot.getValue(user.class);
                avatar = mUsers.getAvatar();
                tenantAccountActivity.setImage(ivAccount_TS, avatar);
                do {
                    progressDialog.dismiss();

                } while (ivAccount_TS.getDrawable().toString().equals(mUsers.getAvatar()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TenantSettingActivity.this, "Get list user faild", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setEvent() {
        ivBack_TS.setOnClickListener(click -> {
            finish();
        });
        btnCapnhattt_TS.setOnClickListener(click -> {
            Intent intent = new Intent(TenantSettingActivity.this, TenantSettingProfileActivity.class);
            intent.putExtra(BUNDLE, setBundle(id,avatar));
            startActivity(intent);
//            finish();
        });
        btnThaydoimk_TS.setOnClickListener(click -> {
            Intent intent = new Intent(TenantSettingActivity.this, TenantPasswordChangeActivity.class);
            intent.putExtra(BUNDLE, setBundle(id,avatar));
            startActivity(intent);
        });

    }


    private void setControl() {
        ivBack_TS = findViewById(R.id.ivBack_TS);
        btnCapnhattt_TS = findViewById(R.id.btnCapnhattt_TS);
        btnThaydoimk_TS = findViewById(R.id.btnThaydoimk_ST);
        tvHoten_TS = findViewById(R.id.tvHoten_TS);
        tvIdaccount_TS = findViewById(R.id.tvIdaccount_TS);
        ivAccount_TS = findViewById(R.id.ivAccount_TS);
    }

    public void getBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(TenantAccountActivity.BUNDLE);
        hoten = bundle.getString(TenantAccountActivity.NAME);
        id = bundle.getString(TenantAccountActivity.ID);
        tvHoten_TS.setText(hoten);
        tvIdaccount_TS.setText(id);
    }

    public Bundle setBundle(String id,String avatar) {
        Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        bundle.putString(AVATAR, avatar);
        return bundle;
    }
}
