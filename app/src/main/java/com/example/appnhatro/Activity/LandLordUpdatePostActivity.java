package com.example.appnhatro.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appnhatro.Firebase.FireBaseLandLord;
import com.example.appnhatro.Models.BitMap;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.R;
import com.example.appnhatro.tool.ConverImage;

import java.util.ArrayList;

public class LandLordUpdatePostActivity extends AppCompatActivity {
    private FireBaseLandLord fireBaseLandLord = new FireBaseLandLord();
    private EditText txtTenPhong, txtDiaChi, txtDienTich, txtGia, txtMoTa, txtIdPost;
    private ImageView imgHinh, imgHinh1, imgHinh2;
    private Button btnHuy, btnAdd;
    private Uri uri[] = new Uri[3];
    private BitMap bitmap = null;
    private String idPost = "";
    private String idUser = "";
    private ConverImage converImage = new ConverImage();
    private Spinner spnDistrict,spnTinhTrang;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_update_post_layout_activity);
        idPost = getIntent().getStringExtra("it_id");
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        idUser = sharedPreferences.getString("idUser", "KH01");
        control();
        fireBaseLandLord.getPost(this, idPost);
        event();
    }

    public void control() {
        spnDistrict = findViewById(R.id.spn_QuanUpdateNewPostLandLord);
        ArrayAdapter districAdapter = new ArrayAdapter<String>(this, R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_districts));
        districAdapter.setDropDownViewResource(R.layout.text_spinner);
        spnDistrict.setAdapter(districAdapter);
        spnTinhTrang = findViewById(R.id.spn_TinhTrangUpdateNewPostLandLord);
        ArrayAdapter statusAdapter = new ArrayAdapter<String>(this, R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_status));
        statusAdapter.setDropDownViewResource(R.layout.text_spinner);
        spnTinhTrang.setAdapter(statusAdapter);
        txtTenPhong = findViewById(R.id.txtTenPhongLandLordUpdatePost);
        txtDiaChi = findViewById(R.id.txtDiaChiLandLordUpdatePost);
        txtDienTich = findViewById(R.id.txtDienTichUpdatePostLandLord);
        txtGia = findViewById(R.id.txtGiaUpdatePostLandLord);
        txtMoTa = findViewById(R.id.txtMoTaUpdatePostLandLord);
        imgHinh = findViewById(R.id.imgUpdatePostLandlord);
        btnAdd = findViewById(R.id.btnUpdatePostLandLord);
        btnHuy = findViewById(R.id.btnHuyUpdatePostLandLord);
        txtIdPost = findViewById(R.id.txtIdUpdatePostLandLord);
        imgHinh1 = findViewById(R.id.imgUpdatePostLandlord1);
        imgHinh2 = findViewById(R.id.imgUpdatePostLandlord2);
    }

    public void event() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTenPhong.getText().toString().equals("")) {
                    txtTenPhong.setError("Vui lòng điền tên phòng");
                } else if (txtDiaChi.getText().toString().equals("")) {
                    txtDiaChi.setError("Vui lòng điền Địa chỉ");
                } else if (txtDienTich.getText().toString().equals("")) {
                    txtDienTich.setError("Vui lòng điền Diện tích");
                } else if (txtGia.getText().toString().equals("")) {
                    txtGia.setError("Vui lòng điền Giá");
                } else if (txtMoTa.getText().toString().equals("")) {
                    txtMoTa.setError("Vui lòng điền Mô tả");
                }
                if (uri[0] == null && uri[1] == null && uri[2] == null) {
                    Post post = new Post(idPost, idUser, txtMoTa.getText() + "", txtDiaChi.getText() + "", spnDistrict.getSelectedItem().toString(), txtGia.getText() + "", txtDienTich.getText() + "", txtTenPhong.getText() + "", idPost + ".jpg", spnTinhTrang.getSelectedItem().toString(), idPost + "_1.jpg", idPost + "_2.jpg");
                    fireBaseLandLord.addUpdatePostNoImage(LandLordUpdatePostActivity.this, post);
                } else {
                    Post post = new Post(idPost, idUser, txtMoTa.getText() + "", txtDiaChi.getText() + "", spnDistrict.getSelectedItem().toString(), txtGia.getText() + "", txtDienTich.getText() + "", txtTenPhong.getText() + "", idPost + ".jpg", spnTinhTrang.getSelectedItem().toString(), idPost + "_1.jpg", idPost + "_2.jpg");
                    fireBaseLandLord.addUpdatePost(LandLordUpdatePostActivity.this, post, uri);
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
                startActivityForResult(i, 1);
            }
        });
        imgHinh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 2);
            }
        });
        imgHinh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 3);
            }
        });
    }

    public void setDuLieu(Post post, Bitmap bitmap) {
        txtTenPhong.setText(post.getHouse_name());
        txtDiaChi.setText(post.getAddress());
        txtDienTich.setText(post.getArea());
        txtGia.setText(post.getPrice());
        txtMoTa.setText(post.getTitle());
        imgHinh2.setImageBitmap(bitmap);
        txtIdPost.setText(idPost);
    }

    public void setImage(Bitmap bitmap) {
        imgHinh.setImageBitmap(bitmap);
    }

    public void setImage1(Bitmap bitmap) {
        imgHinh1.setImageBitmap(bitmap);
    }

    public void setDistrictAndStatus(String District,String status) {
        String[] listQuan = getResources().getStringArray(R.array.string_spn_districts);
        for (int i = 0; i < listQuan.length; i++) {
            if (listQuan[i].equals(District)) {
                spnDistrict.setSelection(i);
                break;
            }
        }
        if(status.equals("Còn phòng")){
            spnTinhTrang.setSelection(0);
        }else {
            spnTinhTrang.setSelection(1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && null != data && data.getData() != null) {
            uri[0] = data.getData();
            imgHinh.setImageURI(uri[0]);
        }
        if (requestCode == 2 && null != data && data.getData() != null) {
            uri[1] = data.getData();
            imgHinh1.setImageURI(uri[1]);
        }
        if (requestCode == 3 && null != data && data.getData() != null) {
            uri[2] = data.getData();
            imgHinh2.setImageURI(uri[2]);
        }
    }

    protected void onResume() {
        super.onResume();
    }
}