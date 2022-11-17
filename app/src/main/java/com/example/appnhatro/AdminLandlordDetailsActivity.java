package com.example.appnhatro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminLandlordDetailsActivity extends AppCompatActivity {
    EditText txt_id,txt_tenchutro,txt_email,txt_phone,txt_cmnd,txt_password;
    String avt,id,name,email,phone,cmnd,pass;
    CircleImageView civImage_ALD;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_landlord_details);
        setControl();
        getSetBundle();
        Log.d("Test",avt);
        TenantAccountActivity tenantAccountActivity = new TenantAccountActivity();
        tenantAccountActivity.setImage(civImage_ALD, avt);
    }
    private void setControl(){
        civImage_ALD = findViewById(R.id.civImage_ALD);
        txt_id = findViewById(R.id.txt_aldMachutro);
        txt_tenchutro = findViewById(R.id.txt_aldTenchutro);
        txt_email = findViewById(R.id.txt_aldEmail);
        txt_cmnd = findViewById(R.id.txt_aldCMND);
        txt_phone = findViewById(R.id.txt_aldSdt);
        txt_password = findViewById(R.id.txt_aldPassword);
    }
    private void getSetBundle(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(AdminLandlordListActivity.BUNDLE);
        id = bundle.getString(AdminLandlordListActivity.ID);
        avt = bundle.getString(AdminLandlordListActivity.AVATAR);
        name = bundle.getString(AdminLandlordListActivity.NAME);
        email = bundle.getString(AdminLandlordListActivity.EMAIL);
        phone = bundle.getString(AdminLandlordListActivity.PHONE);
        cmnd = bundle.getString(AdminLandlordListActivity.CMND);
        pass = bundle.getString(AdminLandlordListActivity.PASSWORD);

        txt_id.setText(id);
        txt_tenchutro.setText(name);
        txt_email.setText(email);
        txt_cmnd.setText(cmnd);
        txt_phone.setText(phone);
        txt_password.setText(pass);

    }
}
