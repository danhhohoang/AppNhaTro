package com.example.appnhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnhatro.Firebase.FireBaseLandLord;
import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.MyRecyclerViewAdapter;
import com.example.appnhatro.R;
import com.example.appnhatro.item.Rating;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LandLordPostDetailActivity extends AppCompatActivity {
    RecyclerView rcvComment;
    ArrayList<Rating> listComment = new ArrayList<>();
    FireBaseLandLord fireBaseLandLord = new FireBaseLandLord();
    String idPost="";
    TextView house_name, area, price, address, title,status,sumlike,sumRating;
    ImageView hinh;
    Button btnXoa,btnSua;
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_post_detail_activity_layout);
        idPost = getIntent().getStringExtra("it_id");
        control();
        event();
    }

    private void event() {
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandLordPostDetailActivity.this,LandLordUpdatePostActivity.class);
                intent.putExtra("it_id", idPost);
                startActivity(intent);
                finish();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireBaseLandLord.deletePost(idPost);
                finish();
            }
        });
    }
    public void setDuLieu(Post post, Bitmap bitmap){
        house_name.setText(post.getHouse_name());
        address.setText(post.getAddress());
        area.setText(formatter.format(Integer.valueOf(post.getArea())) + "m2");
        price.setText(formatter.format(Integer.valueOf(post.getPrice())) + " đ/Tháng");
        title.setText(post.getTitle());
        hinh.setImageBitmap(bitmap);
    }
    private void control() {
        house_name =findViewById(R.id.tvNamePostDetailLandLord);
        address = findViewById(R.id.tvDiaChiPostDetailLandLord);
        price = findViewById(R.id.tvGiaPostDetailLandLord);
        area=findViewById(R.id.tvAreaPostDetailLandLord);
        status = findViewById(R.id.tvTinhTrangLandLord);
        sumlike = findViewById(R.id.tvSLLikePostLandLord);
        title= findViewById(R.id.tvTitlePostDetailLandLord);
        hinh = findViewById(R.id.imgRoomPostDetailLandLord);
        btnSua = findViewById(R.id.btnSuaPhongLandLord);
        btnXoa = findViewById(R.id.btnXoaPhongPostDetailLandLord);
        sumRating = findViewById(R.id.sumRating);
    }
    public void setLike(String Rating){
        sumRating.setText(Rating);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fireBaseLandLord.readOnePost(this,idPost);
    }
}