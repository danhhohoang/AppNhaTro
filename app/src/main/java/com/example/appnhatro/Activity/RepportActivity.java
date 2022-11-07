package com.example.appnhatro.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.ReportModels;
import com.example.appnhatro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RepportActivity extends AppCompatActivity {
    EditText TenBaiDang;
    EditText IDnguoiDang,IDpost;
    EditText TenNguoiGui;
    EditText TieuDe;
    EditText NoiDung;
    Button Huy, Gui;
    String tenbaidang,idbd, idPost,id_login, getIdPost = "";

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
        IDpost = findViewById(R.id.txtIDbaidang);
        Huy = findViewById(R.id.btnHuy);
        id_login = getIntent().getStringExtra("it_ID");
        setIntent();
        setEvent();

        //Tải dữ liệu lên firebase
        Gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportModels post = new ReportModels(IDpost.getText().toString(), NoiDung.getText().toString(),
                        "KH04", IDnguoiDang.getText().toString(), "4", NoiDung.getText().toString());
                addToFavorite(post);
                AlertDialog.Builder a = new AlertDialog.Builder(RepportActivity.this);
                a.setTitle("Thông Báo");
                a.setMessage("Bạn đã gửi thành công Report");
                a.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog al = a.create();
                al.show();
            }
        });
        Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(RepportActivity.this);
                b.setTitle("Thông Báo");
                b.setMessage("Xác nhận hủy Report?");
                b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog al = b.create();
                al.show();
            }
        });
    }

    public void setEvent(){
        TenBaiDang.setText(tenbaidang);
        IDnguoiDang.setText(idbd);
        IDpost.setText(idPost);
        Gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strname = TenNguoiGui.getText().toString();
                String strIDpost = idPost;
                Intent intent = new Intent(RepportActivity.this, landlo_notification_Adapter.class);
                intent.putExtra("Ten nguoi gui", strname);
                intent.putExtra("IdPost", strIDpost);
                startActivity(intent);

            }
        });
    }

    private void addToFavorite(ReportModels post) {
        Log.d("text", post.getId());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Report");
        databaseReference.child(post.getId()).setValue(post);
    }
    private void setIntent(){
        Intent intent = this.getIntent();
        tenbaidang = intent.getStringExtra("Name_hour");
        idbd = intent.getStringExtra("ID");
        idPost = intent.getStringExtra("IdPost");
    }

    public void SetID(String id){
        getIdPost = id;
    }
}