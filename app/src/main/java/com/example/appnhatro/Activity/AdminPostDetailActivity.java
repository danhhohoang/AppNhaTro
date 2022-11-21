package com.example.appnhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnhatro.Adapters.AdminCommentAdapter;
import com.example.appnhatro.Adapters.LandLordCommentAdapter;
import com.example.appnhatro.Adapters.TeantCommentAdapter;
import com.example.appnhatro.Firebase.FireBaseLandLord;
import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Firebase.FirebaseAdmin;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.MyRecyclerViewAdapter;
import com.example.appnhatro.R;
import com.example.appnhatro.item.Rating;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdminPostDetailActivity extends AppCompatActivity {
    RecyclerView rcvComment;
    ArrayList<Rating> listComment = new ArrayList<>();
    AdminCommentAdapter adminCommentAdapter;
    FirebaseAdmin firebaseAdmin = new FirebaseAdmin();
    Post post =new Post();
    TextView house_name, area, price, address, title, status;
    ImageView hinh, back, rating1, rating2, rating3, rating4, rating5;
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_post_detail_activity_layout);
        Intent intent = getIntent();
        post = (Post) intent.getSerializableExtra("Post");
        control();
        event();
    }

    private void event() {
    }

    private void control() {
        rcvComment = (RecyclerView) findViewById(R.id.rcvCommentPostDetailAdmin);
        adminCommentAdapter= new AdminCommentAdapter(this, R.layout.admin_item_comment_layout, listComment);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvComment.setLayoutManager(gridLayoutManager);
        rcvComment.setAdapter(adminCommentAdapter);
        house_name = findViewById(R.id.tvNamePostDetailAdmin);
        address = findViewById(R.id.tvDiaChiPostDetailAdmin);
        price = findViewById(R.id.tvGiaPostDetailAdmin);
        area = findViewById(R.id.tvAreaPostDetailAdmin);
        status = findViewById(R.id.tvTinhTrangAdmin);
        title = findViewById(R.id.tvTitleAdmin);
        hinh = findViewById(R.id.imgRoomPostDetailAdmin);
        back = findViewById(R.id.imgBackPostDetailAdmin);
        rating1 = findViewById(R.id.imgRating1Admin);
        rating2 = findViewById(R.id.imgRating2Admin);
        rating3 = findViewById(R.id.imgRating3Admin);
        rating4 = findViewById(R.id.imgRating4Admin);
        rating5 = findViewById(R.id.imgRating5Admin);
    }
    public void setDuLieu(Post post, Bitmap bitmap) {
        house_name.setText(post.getHouse_name());
        address.setText(post.getAddress());
        area.setText(formatter.format(Integer.valueOf(post.getArea())) + "m2");
        price.setText(formatter.format(Integer.valueOf(post.getPrice())) + " đ/Tháng");
        title.setText(post.getTitle());
        hinh.setImageBitmap(bitmap);
    }
    public void setRating(int Rating) {
        if (Rating == 5) {
            rating1.setSelected(true);
            rating2.setSelected(true);
            rating3.setSelected(true);
            rating4.setSelected(true);
            rating5.setSelected(true);
        } else if (Rating == 4) {
            rating1.setSelected(true);
            rating2.setSelected(true);
            rating3.setSelected(true);
            rating4.setSelected(true);
            rating5.setSelected(false);
        } else if (Rating == 3) {
            rating1.setSelected(true);
            rating2.setSelected(true);
            rating3.setSelected(true);
            rating4.setSelected(false);
            rating5.setSelected(false);
        } else if (Rating == 2) {
            rating1.setSelected(true);
            rating2.setSelected(true);
            rating3.setSelected(false);
            rating4.setSelected(false);
            rating5.setSelected(false);
        } else if (Rating == 1) {
            rating1.setSelected(true);
            rating2.setSelected(false);
            rating3.setSelected(false);
            rating4.setSelected(false);
            rating5.setSelected(false);
        } else {
            rating1.setSelected(false);
            rating2.setSelected(false);
            rating3.setSelected(false);
            rating4.setSelected(false);
            rating5.setSelected(false);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        firebaseAdmin.readOnePostAdmin(AdminPostDetailActivity.this,post.getId(),listComment,adminCommentAdapter);
    }
}