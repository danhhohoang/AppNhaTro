package com.example.appnhatro.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.ReportModels;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RepportActivity extends AppCompatActivity {
    EditText TenBaiDang;
    EditText IDnguoiDang,IDpost;
    EditText TenNguoiGui;
    EditText TieuDe;
    EditText NoiDung;
    Button Huy, Gui;
    ImageView back;
    String tenbaidang,idbd, idPost,id_login, getIdPost = "", userid, idlandlord, newid;
    SharedPreferences sharedPreferences;
    private FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();

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
        back = findViewById(R.id.btn_reportback);
        idlandlord = getIntent().getStringExtra("idlandlord");
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        userid = sharedPreferences.getString("idUser", "");
        id_login = getIntent().getStringExtra("ID");
        String[] parse = id_login. split(":");
        newid = parse[1];
        setIntent();
        setEvent();
        ten();
        //Tải dữ liệu lên firebase
        Gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder a = new AlertDialog.Builder(RepportActivity.this);
                a.setTitle("Thông Báo");
                a.setMessage("Bạn có chắc muốn gửi report");
                a.setPositiveButton("Gửi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ReportModels post = new ReportModels(getIdPost, TenBaiDang.getText().toString(), IDnguoiDang.getText().toString(),
                                IDpost.getText().toString(), TenNguoiGui.getText().toString(), TieuDe.getText().toString(), NoiDung.getText().toString(), "1", userid);
                        addToFavorite(post);
                        finish();
                    }
                });
                a.setNegativeButton("Không gửi", new DialogInterface.OnClickListener() {
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void  ten(){
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    user _user = dataSnapshot.getValue(user.class);
                    if (_user.getId().equals(userid)){
                        TenNguoiGui.setText(_user.getName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setEvent(){
        TenBaiDang.setText(tenbaidang);
        IDnguoiDang.setText(newid);
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

    @Override
    protected void onResume(){
        super.onResume();
        fireBaseThueTro.autoid(RepportActivity.this);
    }
    public void SetID(String id){
        getIdPost = id;
    }
}
