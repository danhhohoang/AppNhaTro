package com.example.appnhatro.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.DatLichModels;
import com.example.appnhatro.Models.Notificationbooking;
import com.example.appnhatro.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Detail_Notification_Landlor extends AppCompatActivity {
    EditText TenNgDatP, SDT, Time, Date, Notes, housename, idPost;
    private FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    Button Huy, DongY;
    private ArrayList<DatLichModels> datLichModels = new ArrayList<>();
    String getID = "";
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlor_detail_notification_layout);
        control();
        String housenamr = getIntent().getStringExtra("housname");
        String name = getIntent().getStringExtra("Ten nguoi dat phong");
        String Phone = getIntent().getStringExtra("Số Điện thoai");
        String time = getIntent().getStringExtra("Thời gian đến xem phòng");
        String date = getIntent().getStringExtra("ngày đến xem phòng");
        String note = getIntent().getStringExtra("ghi chu");
        String idUser = getIntent().getStringExtra("idUSer");
        String id = getIntent().getStringExtra("id");
        housename.setText(housenamr);
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
                        Notificationbooking notificationLandlor = new Notificationbooking(idUser, "KH01", name, Phone, time, date, note, id, getID, "Từ chối đặt lịch", "");
                        addToFavorite(notificationLandlor);
                        DatabaseReference databaseReference;
                        databaseReference = FirebaseDatabase.getInstance().getReference("booking");
                        databaseReference.child(id).child("tt").setValue("2");
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
                    public void onClick(DialogInterface dialogInterface, int potision) {
                        Notificationbooking notificationbooking = new Notificationbooking(idUser, "KH01", name, Phone, time, date, note, id, getID, "Đồng ý đặt lịch", housenamr);
                        addToFavorite(notificationbooking);
                        DatabaseReference databaseReference;
                        databaseReference = FirebaseDatabase.getInstance().getReference("booking");
                        databaseReference.child(id).child("tt").setValue("2");
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void control(){
        back = findViewById(R.id.btn_notifiback1);
        housename = findViewById(R.id.edt_hname);
        TenNgDatP = findViewById(R.id.edt_name);
        SDT = findViewById(R.id.edt_phone);
        Time = findViewById(R.id.edt_time);
        Date = findViewById(R.id.edt_date);
        Notes = findViewById(R.id.edt_notes);
        Huy = findViewById(R.id.btn_huy);
        DongY = findViewById(R.id.btn_dY);
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
