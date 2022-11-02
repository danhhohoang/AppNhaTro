package com.example.appnhatro;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TenantAccount extends AppCompatActivity {
    Button btnPhongdaluu_TA,btnLienhe_TA,btnDangxuat_TA,btnCaidat_TA,btnDieuKhoan_TA,btnBaidang_TA;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_account);
        setControl();
    }
    public void setEvent(){

    }
    public void setControl(){
        btnPhongdaluu_TA = findViewById(R.id.btnPhongdaluu_TA);
        btnDangxuat_TA = findViewById(R.id.btnDangxuat_TA);
        btnLienhe_TA = findViewById(R.id.btnLienhe_TA);
        btnCaidat_TA = findViewById(R.id.btnCaidat_TA);
        btnDieuKhoan_TA = findViewById(R.id.btnDieukhoan_TA);
        btnBaidang_TA = findViewById(R.id.btnBaidang_TA);

    }
}
