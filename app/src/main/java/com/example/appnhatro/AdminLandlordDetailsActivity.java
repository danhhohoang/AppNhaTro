package com.example.appnhatro;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLandlordDetailsActivity extends AppCompatActivity {
    EditText id,tenchutro,tentaikhoan,CMND,diachi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_landlord_details);

        String it_ald_id = getIntent().getStringExtra("it_ald_id");
        String it_ald_tenchutro = getIntent().getStringExtra("it_ald_tenchutro");
        String it_ald_tentaikhoan = getIntent().getStringExtra("it_ald_tentaikhoan");
        String it_ald_CMND = getIntent().getStringExtra("it_ald_CMND");
        String it_ald_diachi = getIntent().getStringExtra("it_ald_diachi");

        id = findViewById(R.id.txt_aldMachutro);
        tenchutro = findViewById(R.id.txt_aldTenchutro);
        tentaikhoan = findViewById(R.id.txt_aldTentaikhoan);
        CMND = findViewById(R.id.txt_aldCMND);
        diachi = findViewById(R.id.txt_aldDiachi);

        id.setText(it_ald_id);
        tenchutro.setText(it_ald_tenchutro);
        tentaikhoan.setText(it_ald_tentaikhoan);
        CMND.setText(it_ald_CMND);
        diachi.setText(it_ald_diachi);
    }
}
