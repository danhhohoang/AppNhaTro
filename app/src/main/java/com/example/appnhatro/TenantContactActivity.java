package com.example.appnhatro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class TenantContactActivity extends AppCompatActivity {
    ImageView ivBack_TC;
    TenantSettingActivity  tenantSettingActivity;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_contact);
        ivBack_TC = findViewById(R.id.ivBack_TC);
        tenantSettingActivity = new TenantSettingActivity();
        ivBack_TC.setOnClickListener(click->{
            finish();
        });
    }
}
