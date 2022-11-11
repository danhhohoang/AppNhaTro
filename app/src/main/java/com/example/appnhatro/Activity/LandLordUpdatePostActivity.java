package com.example.appnhatro.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.appnhatro.Firebase.FireBaseLandLord;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.R;
import com.example.appnhatro.tool.ConverImage;

public class LandLordUpdatePostActivity extends AppCompatActivity {
    private FireBaseLandLord fireBaseLandLord = new FireBaseLandLord();
    private EditText txtTenPhong, txtDiaChi, txtDienTich, txtGia, txtMoTa,txtIdPost;
    private ImageView imgHinh;
    private Button btnHuy, btnAdd;
    private Uri uri;
    private BitMap bitmap = null;
    private String idPost = "";
    private String idUser="";
    private ConverImage converImage = new ConverImage();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_update_post_layout_activity);
        idPost = getIntent().getStringExtra("it_id");
        control();
        event();
    }
    public void control() {
        txtTenPhong = findViewById(R.id.txtTenPhongLandLordUpdatePost);
        txtDiaChi = findViewById(R.id.txtDiaChiLandLordUpdatePost);
        txtDienTich = findViewById(R.id.txtDienTichUpdatePostLandLord);
        txtGia = findViewById(R.id.txtGiaUpdatePostLandLord);
        txtMoTa = findViewById(R.id.txtMoTaUpdatePostLandLord);
        imgHinh = findViewById(R.id.imgUpdatePostLandlord);
        btnAdd = findViewById(R.id.btnUpdatePostLandLord);
        btnHuy = findViewById(R.id.btnHuyUpdatePostLandLord);
        txtIdPost = findViewById(R.id.txtIdUpdatePostLandLord);
    }
    public void event() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                converImage.docAnh(uri, LandLordUpdatePostActivity.this, txtTenPhong.getText() + "");
                Post post= new Post(idPost,idUser,txtMoTa.getText()+"",txtDiaChi.getText()+"","Quận3",txtGia.getText()+"",txtDienTich.getText()+"",txtTenPhong.getText()+"",txtTenPhong.getText()+"","Còn phòng");
                fireBaseLandLord.addNewPost(post);
                //Thong báo

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

    public void setDuLieu(Post post, Bitmap bitmap){
        txtTenPhong.setText(post.getHouse_name());
        txtDiaChi.setText(post.getAddress());
        txtDienTich.setText(post.getArea());
        txtGia.setText(post.getPrice());
        txtMoTa.setText(post.getTitle());
        imgHinh.setImageBitmap(bitmap);
        txtIdPost.setText(idPost);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && null != data && data.getData() != null) {
            uri = data.getData();
            imgHinh.setImageURI(uri);
        }
    }
    protected void onResume() {
        super.onResume();
        fireBaseLandLord.getPost(this,idPost);
    }
}