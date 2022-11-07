package com.example.appnhatro.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.ReportModel;
import com.example.appnhatro.R;

import java.util.ArrayList;

public class landlord_notification_report_activity extends AppCompatActivity {
    ImageView Imgavt;
    EditText Name, MaReport, NoiDung, MaPhong;
    String name, maReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_notification_report);
        control();
        setIntent();
        Event();
    }
    public void control(){
        Imgavt = findViewById(R.id.imageAVT);
        Name = findViewById(R.id.edtname);
        MaReport = findViewById(R.id.edtIDreport);
        NoiDung = findViewById(R.id.edtNoiDung);
        MaPhong = findViewById(R.id.edtMaPhong);
    }
    public void Event(){
        Name.setText(name);
        MaReport.setText(maReport);
    }
    private void setIntent(){
        Intent intent = this.getIntent();
        name = intent.getStringExtra("Ten nguoi gui");
        maReport = intent.getStringExtra("IdPost");
    }

}
