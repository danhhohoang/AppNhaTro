package com.example.appnhatro.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.DatLichModels;
import com.example.appnhatro.Models.Notificationbooking;
import com.example.appnhatro.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Detail_Notification_Landlor extends AppCompatActivity {
    EditText TenNgDatP, SDT, Time, Date, Notes;
    Button Huy, DongY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlor_detail_notification_layout);
        control();
        String name = getIntent().getStringExtra("Ten nguoi dat phong");
        String Phone = getIntent().getStringExtra("Số Điện thoai");
        String time = getIntent().getStringExtra("Thời gian đến xem phòng");
        String date = getIntent().getStringExtra("ngày đến xem phòng");
        String note = getIntent().getStringExtra("ghi chu");
        String idUser = getIntent().getStringExtra("idUSer");
        String id = getIntent().getStringExtra("id");
        TenNgDatP.setText(name);
        SDT.setText(Phone);
        Time.setText(time);
        Date.setText(date);
        Notes.setText(note);

        Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notificationbooking notificationLandlor = new Notificationbooking(idUser, name, Phone, time, date, note, id, "TB_04", "Từ chối đặt lịch");
                addToFavorite(notificationLandlor);
            }
        });
        DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notificationbooking notificationLandlor = new Notificationbooking(idUser, name, Phone, time, date, note, id, "TB_04", "Đồng ý đặt lịch");
                addToFavorite(notificationLandlor);
            }
        });
    }
    public void control(){
        TenNgDatP = findViewById(R.id.edt_name);
        SDT = findViewById(R.id.edt_phone);
        Time = findViewById(R.id.edt_time);
        Date = findViewById(R.id.edt_date);
        Notes = findViewById(R.id.edt_notes);
        Huy = findViewById(R.id.btn_huy);
        DongY = findViewById(R.id.btn_dY);
    }
    public void setData(DatLichModels datLichModels, Bitmap bitmap){
        TenNgDatP.setText(datLichModels.getName());
        SDT.setText(datLichModels.getPhone());
        Time.setText(datLichModels.getTime());
        Date.setText(datLichModels.getDate());
        Notes.setText(datLichModels.getNotes());
    }
    private void addToFavorite(Notificationbooking post) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Notification");
        databaseReference.child(post.getIdNotifi()).setValue(post);
    }
}
