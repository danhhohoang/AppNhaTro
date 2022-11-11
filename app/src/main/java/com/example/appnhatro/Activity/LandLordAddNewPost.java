package com.example.appnhatro.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.appnhatro.Firebase.FireBaseLandLord;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.R;
import com.example.appnhatro.item.Rating;
import com.example.appnhatro.tool.ConverImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class LandLordAddNewPost extends AppCompatActivity {
    private FireBaseLandLord fireBaseLandLord = new FireBaseLandLord();
    private EditText txtTenPhong, txtDiaChi, txtDienTich, txtGia, txtMoTa,txtIdPost;
    private ImageView imgHinh;
    private Button btnHuy, btnAdd;
    private Uri uri=null;
    private String idPost = "";
    private String idUser="";
    private ConverImage converImage = new ConverImage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_add_new_post_activity_layout);
        control();
        idUser="KH01";
        event();
    }

    public void control() {
        txtTenPhong = findViewById(R.id.txtTenPhongLandLordAddNewPost);
        txtDiaChi = findViewById(R.id.txtDiaChiLandLordAddNewPost);
        txtDienTich = findViewById(R.id.txtDienTichAddNewPostLandLord);
        txtGia = findViewById(R.id.txtGiaNewPostLandLord);
        txtMoTa = findViewById(R.id.txtMoTaAddNewPostLandLord);
        imgHinh = findViewById(R.id.imgAddNewPostLandlord);
        btnAdd = findViewById(R.id.btnAddNewPostLandLord);
        btnHuy = findViewById(R.id.btnHuyAddNewPostLandLord);
        txtIdPost = findViewById(R.id.txtIdNewPostLandLord);
    }

    public void event() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtTenPhong.getText().toString().equals("")){
                    txtTenPhong.setError("Vui lòng điền tên phòng");
                }else if(txtDiaChi.getText().toString().equals("")) {
                    txtDiaChi.setError("Vui lòng điền Địa chỉ");
                }else if(txtDienTich.getText().toString().equals("")){
                    txtDienTich.setError("Vui lòng điền Diện tích");
                }else if(txtGia.getText().toString().equals("")) {
                    txtGia.setError("Vui lòng điền Giá");
                }else if(txtMoTa.getText().toString().equals("")) {
                    txtMoTa.setError("Vui lòng điền Mô tả");
                }else if(uri==null) {
                    Toast.makeText(LandLordAddNewPost.this, "Chọn Ảnh", Toast.LENGTH_SHORT).show();
                }else {
                    Post post = new Post(idPost, idUser, txtMoTa.getText() + "", txtDiaChi.getText() + "", "Quận3", txtGia.getText() + "", txtDienTich.getText() + "", txtTenPhong.getText() + "", idPost + ".jpg", "Còn phòng");
                    fireBaseLandLord.addNewPost(LandLordAddNewPost.this, post, uri);
                    //Thong báo
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 3);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && null != data && data.getData() != null) {
            uri = data.getData();
            imgHinh.setImageURI(uri);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fireBaseLandLord.getId(LandLordAddNewPost.this);
    }
    public void setId(String id) {
        idPost = id;
        txtIdPost.setText(id);
    }
}