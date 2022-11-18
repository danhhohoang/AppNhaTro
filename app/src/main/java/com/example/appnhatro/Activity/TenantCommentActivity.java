package com.example.appnhatro.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.R;
import com.example.appnhatro.item.Rating;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TenantCommentActivity extends AppCompatActivity {
    FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    ImageView imgBack, imgrating1, imgrating2, imgrating3, imgrating4, imgrating5;
    boolean rating1, rating2, rating3, rating4, rating5 = false;
    EditText txtName, txtComment;
    Button btnHuy, btnSua, btnNhanXet;
    Rating rating = new Rating();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_comment_activity_layout);
        rating = (Rating) getIntent().getSerializableExtra("Rating");
        control();
        if(rating.getComment()==null){
            btnNhanXet.setVisibility(View.VISIBLE);
            btnSua.setVisibility(View.GONE);
        }else{
            btnNhanXet.setVisibility(View.GONE);
            btnSua.setVisibility(View.VISIBLE);
        }
        if(rating.getFeedback()==null){
            rating.setFeedback("");
        }else{
        }
        setRating();
        txtComment.setText(rating.getComment());
        event();
    }

    private void setRating() {
        if(rating.getRating().equals("5")){
            imgrating1.setSelected(true);
            imgrating2.setSelected(true);
            imgrating3.setSelected(true);
            imgrating4.setSelected(true);
            imgrating5.setSelected(true);
            rating1=true;
            rating2=true;
            rating3=true;
            rating4=true;
            rating5=true;
        }else if(rating.getRating().equals("4")){
            imgrating1.setSelected(true);
            imgrating2.setSelected(true);
            imgrating3.setSelected(true);
            imgrating4.setSelected(true);
            imgrating5.setSelected(false);
            rating1=true;
            rating2=true;
            rating3=true;
            rating4=true;
            rating5=false;
        }else if(rating.getRating().equals("3")){
            imgrating1.setSelected(true);
            imgrating2.setSelected(true);
            imgrating3.setSelected(true);
            imgrating4.setSelected(false);
            imgrating5.setSelected(false);
            rating1=true;
            rating2=true;
            rating3=true;
            rating4=false;
            rating5=false;
        }else if(rating.getRating().equals("2")){
            imgrating1.setSelected(true);
            imgrating2.setSelected(true);
            imgrating3.setSelected(false);
            imgrating4.setSelected(false);
            imgrating5.setSelected(false);
            rating1=true;
            rating2=true;
            rating3=false;
            rating4=false;
            rating5=false;
        }else if(rating.getRating().equals("1")){
            imgrating1.setSelected(true);
            imgrating2.setSelected(false);
            imgrating3.setSelected(false);
            imgrating4.setSelected(false);
            imgrating5.setSelected(false);
            rating1=true;
            rating2=false;
            rating3=false;
            rating4=false;
            rating5=false;
        }else{
            imgrating1.setSelected(false);
            imgrating2.setSelected(false);
            imgrating3.setSelected(false);
            imgrating4.setSelected(false);
            imgrating5.setSelected(false);
            rating1=false;
            rating2=false;
            rating3=false;
            rating4=false;
            rating5=false;
        }
    }

    private void event() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.time.LocalDate.now();
                if(rating5==true) {
                    rating.setRating("5");
                    rating.setComment(txtComment.getText().toString());
                    rating.setDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
                    fireBaseThueTro.addAndUpdateComment(TenantCommentActivity.this, rating);
                }else if(rating4==true) {
                    rating.setRating("4");
                    rating.setComment(txtComment.getText().toString());
                    rating.setDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
                    fireBaseThueTro.addAndUpdateComment(TenantCommentActivity.this, rating);
                }else if(rating3==true) {
                    rating.setRating("3");
                    rating.setComment(txtComment.getText().toString());
                    rating.setDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
                    fireBaseThueTro.addAndUpdateComment(TenantCommentActivity.this, rating);
                }else if(rating2==true) {
                    rating.setRating("2");
                    rating.setComment(txtComment.getText().toString());
                    rating.setDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
                    fireBaseThueTro.addAndUpdateComment(TenantCommentActivity.this, rating);
                }else if(rating1==true) {
                    rating.setRating("1");
                    rating.setComment(txtComment.getText().toString());
                    rating.setDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
                    fireBaseThueTro.addAndUpdateComment(TenantCommentActivity.this, rating);
                }else if(rating1==false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TenantCommentActivity.this);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Bạn chưa chọn đánh giá");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }else if(txtComment.getText().toString().equals("")){
                    txtComment.setError("Vui lòng ghi nhận xét");
                }
            }
        });
        btnNhanXet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.time.LocalDate.now();
                if(rating5==true) {
                    rating.setRating("5");
                    rating.setComment(txtComment.getText().toString());
                    rating.setDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
                    fireBaseThueTro.addAndUpdateComment(TenantCommentActivity.this, rating);
                }else if(rating4==true) {
                    rating.setRating("4");
                    rating.setComment(txtComment.getText().toString());
                    rating.setDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
                    fireBaseThueTro.addAndUpdateComment(TenantCommentActivity.this, rating);
                }else if(rating3==true) {
                    rating.setRating("3");
                    rating.setComment(txtComment.getText().toString());
                    rating.setDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
                    fireBaseThueTro.addAndUpdateComment(TenantCommentActivity.this, rating);
                }else if(rating2==true) {
                    rating.setRating("2");
                    rating.setComment(txtComment.getText().toString());
                    rating.setDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
                    fireBaseThueTro.addAndUpdateComment(TenantCommentActivity.this, rating);
                }else if(rating1==true) {
                    rating.setRating("1");
                    rating.setComment(txtComment.getText().toString());
                    rating.setDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
                    fireBaseThueTro.addAndUpdateComment(TenantCommentActivity.this, rating);
                }else if(rating1==false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TenantCommentActivity.this);
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Bạn chưa chọn đánh giá");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }else if(txtComment.getText().toString().equals("")){
                    txtComment.setError("Vui lòng ghi nhận xét");
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgrating5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgrating1.setSelected(true);
                imgrating2.setSelected(true);
                imgrating3.setSelected(true);
                imgrating4.setSelected(true);
                imgrating5.setSelected(true);
                rating1=true;
                rating2=true;
                rating3=true;
                rating4=true;
                rating5=true;
            }
        });
        imgrating4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgrating1.setSelected(true);
                imgrating2.setSelected(true);
                imgrating3.setSelected(true);
                imgrating4.setSelected(true);
                imgrating5.setSelected(false);
                rating1=true;
                rating2=true;
                rating3=true;
                rating4=true;
                rating5=false;
            }
        });
        imgrating3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgrating1.setSelected(true);
                imgrating2.setSelected(true);
                imgrating3.setSelected(true);
                imgrating4.setSelected(false);
                imgrating5.setSelected(false);
                rating1=true;
                rating2=true;
                rating3=true;
                rating4=false;
                rating5=false;
            }
        });
        imgrating2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgrating1.setSelected(true);
                imgrating2.setSelected(true);
                imgrating3.setSelected(false);
                imgrating4.setSelected(false);
                imgrating5.setSelected(false);
                rating1=true;
                rating2=true;
                rating3=false;
                rating4=false;
                rating5=false;
            }
        });
        imgrating1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgrating1.setSelected(true);
                imgrating2.setSelected(false);
                imgrating3.setSelected(false);
                imgrating4.setSelected(false);
                imgrating5.setSelected(false);
                rating1=true;
                rating2=false;
                rating3=false;
                rating4=false;
                rating5=false;
            }
        });

    }

    private void control() {
        imgBack = findViewById(R.id.imgBackComment);
        imgrating1 = findViewById(R.id.imgRatingComment1);
        imgrating2 = findViewById(R.id.imgRatingComment2);
        imgrating3 = findViewById(R.id.imgRatingComment3);
        imgrating4 = findViewById(R.id.imgRatingComment4);
        imgrating5 = findViewById(R.id.imgRatingComment5);
        txtComment = findViewById(R.id.txtCommentTenant);
        txtName = findViewById(R.id.txtNameUserComment);
        btnHuy = findViewById(R.id.btnHuyComment);
        btnNhanXet = findViewById(R.id.btnNhanXetComment);
        btnSua = findViewById(R.id.btnSuaComment);
    }

    public void setNameUser(String name){
        txtName.setText(name);
    }
    @Override
    protected void onResume() {
        super.onResume();
        fireBaseThueTro.getNameUserInComment(TenantCommentActivity.this,rating.getIdUser());
    }
}