package com.example.appnhatro.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.ArrayList;

public class LandLordAddNewPost extends AppCompatActivity {
    private FireBaseLandLord fireBaseLandLord = new FireBaseLandLord();
    private EditText txtTenPhong, txtDiaChi, txtDienTich, txtGia, txtMoTa,txtIdPost;
    private ImageView imgHinh,imgHinh1,imgHinh2;
    private Button btnHuy, btnAdd;
    private Uri uri[]= new Uri[3];
    private String idPost = "";
    private String idUser="";
    private ConverImage converImage = new ConverImage();
    private SharedPreferences sharedPreferences;
    private Spinner spnDistrict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_add_new_post_activity_layout);
        control();
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        idUser = sharedPreferences.getString("idUser", "KH01");
        event();
    }

    public void control() {
        spnDistrict = findViewById(R.id.spn_QuanAddNewPostLandLord);
        ArrayAdapter districAdapter = new ArrayAdapter<String>(this, R.layout.text_spinner, getResources().getStringArray(R.array.string_spn_district));
        districAdapter.setDropDownViewResource(R.layout.text_spinner);
        spnDistrict.setAdapter(districAdapter);
        txtTenPhong = findViewById(R.id.txtTenPhongLandLordAddNewPost);
        txtDiaChi = findViewById(R.id.txtDiaChiLandLordAddNewPost);
        txtDienTich = findViewById(R.id.txtDienTichAddNewPostLandLord);
        txtGia = findViewById(R.id.txtGiaNewPostLandLord);
        txtMoTa = findViewById(R.id.txtMoTaAddNewPostLandLord);
        imgHinh = findViewById(R.id.imgAddNewPostLandlord);
        btnAdd = findViewById(R.id.btnAddNewPostLandLord);
        btnHuy = findViewById(R.id.btnHuyAddNewPostLandLord);
        txtIdPost = findViewById(R.id.txtIdNewPostLandLord);
        imgHinh1 = findViewById(R.id.imgAddNewPostLandlord1);
        imgHinh2=findViewById(R.id.imgAddNewPostLandlord2);
    }

    public void event() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtTenPhong.getText().toString().equals("")){
                    txtTenPhong.setError("Vui lòng điền tên phòng");
                }else if(txtDiaChi.getText().toString().equals("")) {
                    txtDiaChi.setError("Vui lòng điền Địa chỉ");
                }else if(spnDistrict.getSelectedItemPosition()==0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LandLordAddNewPost.this);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Bạn chưa chọn Quận");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }else if(txtDienTich.getText().toString().equals("")){
                    txtDienTich.setError("Vui lòng điền Diện tích");
                }else if(txtGia.getText().toString().equals("")) {
                    txtGia.setError("Vui lòng điền Giá");
                }else if(txtMoTa.getText().toString().equals("")) {
                    txtMoTa.setError("Vui lòng điền Mô tả");
                }else if(uri[0]==null||uri[1]==null||uri[2]==null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LandLordAddNewPost.this);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Bạn phải chọn đủ 3 ảnh");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }else {
                    Post post = new Post(idPost, idUser, txtMoTa.getText() + "", txtDiaChi.getText() + "", spnDistrict.getSelectedItem().toString(), txtGia.getText() + "", txtDienTich.getText() + "", txtTenPhong.getText() + "", idPost + ".jpg", "Còn phòng",idPost + "_1.jpg",idPost + "_2.jpg");
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