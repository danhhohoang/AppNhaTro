package com.example.appnhatro.Activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.DatLichModels;
import com.example.appnhatro.Models.Notificationbooking;
import com.example.appnhatro.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Detail_Notification_Landlor extends AppCompatActivity {
    EditText TenNgDatP, SDT, Time, Date, Notes;
    private FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    Button Huy, DongY;
    String getID = "";
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
                AlertDialog.Builder a = new AlertDialog.Builder(Detail_Notification_Landlor.this);
                a.setTitle("THÔNG BÁO");
                a.setMessage("Bạn có chắc từ chối đặt lịch");
                a.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Notificationbooking notificationLandlor = new Notificationbooking(idUser, "KH01", name, Phone, time, date, note, id, getID, "Từ chối đặt lịch");
                        addToFavorite(notificationLandlor);
                        finish();
                    }
                });
               a.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       finish();
                   }
               });
               AlertDialog al = a.create();
               al.show();
            }
        });
        DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(Detail_Notification_Landlor.this);
                b.setTitle("THÔNG BÁO");
                b.setMessage("Bạn có chắc đồng ý đặt lịch");
                b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Notificationbooking notificationLandlor = new Notificationbooking(idUser, "KH01", name, Phone, time, date, note, id, getID, "Đồng ý đặt lịch");
                        addToFavorite(notificationLandlor);
                        finish();
                    }
                });
                b.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog al = b.create();
                al.show();
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
    @Override
    protected void onResume(){
        super.onResume();
        fireBaseThueTro.IdNotificationLandlor(Detail_Notification_Landlor.this);
    }
    public void IdNotification (String id){
        getID = id;
    }
}
