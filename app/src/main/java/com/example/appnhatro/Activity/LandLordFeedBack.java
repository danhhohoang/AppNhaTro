package com.example.appnhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.appnhatro.Firebase.FireBaseLandLord;
import com.example.appnhatro.R;
import com.example.appnhatro.item.Rating;

public class LandLordFeedBack extends AppCompatActivity {
    EditText txtTenNguoiDung,txtComment,txtFeedBack;
    Button btnNhanXet,btnSua,btnHuy;
    Rating rating=new Rating();
    ImageView back;
    FireBaseLandLord fireBaseLandLord = new FireBaseLandLord();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_landlord_activity);
        rating = (Rating) getIntent().getSerializableExtra("Rating");
        control();
        event();
    }

    private void event() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnNhanXet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtFeedBack.getText().toString().equals("")){
                    txtFeedBack.setError("Không được bỏ trống");
                }else{
                    rating.setFeedback(txtFeedBack.getText().toString());
                    fireBaseLandLord.addAndUpdateFeedBackLandLord(LandLordFeedBack.this,rating);
                }
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtFeedBack.getText().toString().equals("")){
                    txtFeedBack.setError("Không được bỏ trống");
                }else{
                    rating.setFeedback(txtFeedBack.getText().toString());
                    fireBaseLandLord.addAndUpdateFeedBackLandLord(LandLordFeedBack.this,rating);
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void control() {
        txtFeedBack = findViewById(R.id.txtPhanHoiLandLord);
        txtComment = findViewById(R.id.txtNhanXetPhanHoiLandLord);
        txtTenNguoiDung=findViewById(R.id.txtNameUserPhanHoi);
        btnNhanXet = findViewById(R.id.btnNhanXetPhanHoi);
        btnSua = findViewById(R.id.btnSuaPhanHoi);
        btnHuy=findViewById(R.id.btnHuyPhanHoi);
        txtComment.setText(rating.getComment());
        txtFeedBack.setText(rating.getFeedback());
        back=findViewById(R.id.imgBackFeedBackDetailLandLord);
        if(rating.getFeedback().equals("")){
            btnNhanXet.setVisibility(View.VISIBLE);
            btnSua.setVisibility(View.GONE);
        }else{
            btnNhanXet.setVisibility(View.GONE);
            btnSua.setVisibility(View.VISIBLE);
        }
    }
    public void setNameUser(String name){
        txtTenNguoiDung.setText(name);
    }
    @Override
    protected void onResume() {
        super.onResume();
        fireBaseLandLord.getNameUserFeedback( this,rating.getIdUser());
    }
}