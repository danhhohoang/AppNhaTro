package com.example.appnhatro;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.TenantModel;

public class TenantSettingProfile extends AppCompatActivity {
    EditText txtHoten_TSP, txtNgaysinh_TSP, txtEmail_TSP, txtSDT_TSP, txtGioithieu_TSP;
    Button btnLuu_TSP;
    private List<TenantModel> mListUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_setting_profile);
        setControl();
        mListUser = new ArrayList<>();
        onRead();
        setEvent();

    }
    private void setEvent(){
        btnLuu_TSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLuuData();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onRead();
                    }
                }, 5000);
            }
        });
    }
    private void onRead() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TenantModel tenantModel = dataSnapshot.getValue(TenantModel.class);
                    mListUser.add(tenantModel);
                }
                txtHoten_TSP.setText(mListUser.getClass().getName());
                txtNgaysinh_TSP.setText(mListUser.get(0).getCmnd());
                txtEmail_TSP.setText(mListUser.get(0).getEmail());
                txtSDT_TSP.setText(mListUser.get(0).getSdt());
                txtGioithieu_TSP.setText(mListUser.get(0).getPassword());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TenantSettingProfile.this, "Get list user faild", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void onClickLuuData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/K01");
        TenantModel tenantModel = new TenantModel(
                "heheh.png",
                txtNgaysinh_TSP.getText().toString(),
                txtEmail_TSP.getText().toString(),
                "KD01",
                txtHoten_TSP.getText().toString(),
                txtGioithieu_TSP.getText().toString(),
                txtSDT_TSP.getText().toString());
        userRef.setValue(tenantModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(TenantSettingProfile.this, "Update data successful", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setControl() {
        txtHoten_TSP = findViewById(R.id.txtHoten_TSP);
        txtNgaysinh_TSP = findViewById(R.id.txtNgaysinh_TSP);
        txtSDT_TSP = findViewById(R.id.txtSDT_TSP);
        txtEmail_TSP = findViewById(R.id.txtEmail_TSP);
        txtGioithieu_TSP = findViewById(R.id.txtGioithieu_TSP);
        txtGioithieu_TSP = findViewById(R.id.txtGioithieu_TSP);
        btnLuu_TSP = findViewById(R.id.btnLuu_TSP);
    }

}
