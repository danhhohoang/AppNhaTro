package com.example.appnhatro.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.ReportModels;
import com.example.appnhatro.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RepportActivity extends AppCompatActivity {
    EditText TenBaiDang, IDnguoiDang, TenNguoiGui, TieuDe, NoiDung;
    Button Huy, Gui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_layout);

        TenBaiDang = findViewById(R.id.txtTenBaiDang);
        IDnguoiDang = findViewById(R.id.txtidnguoidang);
        TenNguoiGui = findViewById(R.id.txtSenderIsName);
        TieuDe = findViewById(R.id.txtTitle);
        NoiDung =findViewById(R.id.txtContentReport);
        Gui = findViewById(R.id.btngui);

        //Tải dữ liệu lên firebase
        Gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportModels post = new ReportModels("RP04", NoiDung.getText().toString(),
                        "KH04", IDnguoiDang.getText().toString(), "4", NoiDung.getText().toString());
                addToFavorite(post);
            }
        });
    }
    private void addToFavorite(ReportModels post) {
        Log.d("text", post.getId());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Report");
        databaseReference.child(post.getId()).setValue(post);
    }
}
