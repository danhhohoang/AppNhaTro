package com.example.appnhatro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.R;

public class fillter extends AppCompatActivity {

    Spinner KhuVuc, Tu, Den, DienTich, NoiThat, TinhTrang;
    Button Loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fillter_layout);
        KhuVuc = findViewById(R.id.spnKhuVuc);
        Tu = findViewById(R.id.spntu);
        Den = findViewById(R.id.spnden);
        DienTich = findViewById(R.id.spnDientich);
        NoiThat = findViewById(R.id.spnNoiThat);
        TinhTrang = findViewById(R.id.spntinhtrang);
        Loc = findViewById(R.id.btnloc);
        setSpinner();

        Loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transmissionData();
            }
        });
    }
    private void transmissionData() {
        String khuvuc = KhuVuc.getSelectedItem().toString();
        String tu = Tu.getSelectedItem().toString();
        String den = Den.getSelectedItem().toString();
        String S = DienTich.getSelectedItem().toString();
        String noithat = NoiThat.getSelectedItem().toString();
        String tinhtrang = TinhTrang.getSelectedItem().toString();
        Intent intent = new Intent(fillter.this, filler2.class);
        intent.putExtra("Quan2", khuvuc);
        startActivity(intent);
    }
    private void setSpinner() {
        ArrayAdapter spnKhuVuc = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_spn_KhuVuc));

        ArrayAdapter spnTu = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_spn_Tu));

        ArrayAdapter spnDen = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_spn_Den));

        ArrayAdapter spnDienTich = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_spn_area));

        ArrayAdapter spnNoiThat = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_spn_NoiThat));

        ArrayAdapter spnTinhTrang = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.string_spn_TinhTrang));

        KhuVuc.setAdapter(spnKhuVuc);
        Tu.setAdapter(spnTu);
        Den.setAdapter(spnDen);
        DienTich.setAdapter(spnDienTich);
        NoiThat.setAdapter(spnNoiThat);
        TinhTrang.setAdapter(spnTinhTrang);

        spnKhuVuc.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnTu.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnDen.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnDienTich.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spnNoiThat.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);


        KhuVuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(fillter.this, KhuVuc.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
