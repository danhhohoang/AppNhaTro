package com.example.appnhatro.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.R;

public class filler2 extends AppCompatActivity {
    Spinner KhuVuc, Tu, Den, DienTich, NoiThat, TinhTrang;
    Button Loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fillter_layout2);
        KhuVuc = findViewById(R.id.spnKhuVuc);
        Tu = findViewById(R.id.spntu);
        Den = findViewById(R.id.spnden);
        DienTich = findViewById(R.id.spnDientich);
        NoiThat = findViewById(R.id.spnNoiThat);
        TinhTrang = findViewById(R.id.spntinhtrang);
        Loc = findViewById(R.id.btnloc);

        KhuVuc.setAdapter(getIntent().getParcelableExtra("Quan2"));

    }

}
