package com.example.appnhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnhatro.Adapters.AdminCommentAdapter;
import com.example.appnhatro.Adapters.ImagePostDetailAdapter;
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
    private RecyclerView rcvComment, rcvHinh;
    private ArrayList<Rating> listComment = new ArrayList<>();
    private AdminCommentAdapter adminCommentAdapter;
    private FirebaseAdmin firebaseAdmin = new FirebaseAdmin();
    private String idpost = "";
    private TextView house_name, area, price, address, title, status;
    private ImageView back, rating1, rating2, rating3, rating4, rating5,imgUser;
    private ImagePostDetailAdapter imagePostDetailAdapter;
    private ArrayList<String> imagePost = new ArrayList<>();
    private DecimalFormat formatter = new DecimalFormat("#,###,###");
    private Button btnXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_post_detail_activity_layout);
        idpost = getIntent().getStringExtra("idPost");
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
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAdmin.deletePost(idpost);
            }
        });
    }

    private void control() {
        rcvHinh = (RecyclerView) findViewById(R.id.rcvImagePostDetailAdmin);
        imagePostDetailAdapter = new ImagePostDetailAdapter(this, R.layout.list_view_item_home_tenant, imagePost);
        GridLayoutManager gridLayoutManagers = new GridLayoutManager(this, 1);
        gridLayoutManagers.setOrientation(RecyclerView.HORIZONTAL);
        rcvHinh.setLayoutManager(gridLayoutManagers);
        rcvHinh.setAdapter(imagePostDetailAdapter);

        rcvComment = (RecyclerView) findViewById(R.id.rcvCommentPostDetailAdmin);
        adminCommentAdapter = new AdminCommentAdapter(this, R.layout.admin_item_comment_layout, listComment);
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
        back = findViewById(R.id.imgBackPostDetailAdmin);
        rating1 = findViewById(R.id.imgRating1Admin);
        rating2 = findViewById(R.id.imgRating2Admin);
        rating3 = findViewById(R.id.imgRating3Admin);
        rating4 = findViewById(R.id.imgRating4Admin);
        rating5 = findViewById(R.id.imgRating5Admin);
        imgUser = findViewById(R.id.img_admin_post_details_Landlord_avatar);
        btnXoa = findViewById(R.id.btnXoaPostDetailAdmin);
    }

    public void setDuLieu(Post post,Bitmap hinh) {
        house_name.setText(post.getHouse_name());
        address.setText(post.getAddress());
        area.setText(formatter.format(Integer.valueOf(post.getArea())) + "m2");
        price.setText(formatter.format(Integer.valueOf(post.getPrice())) + " đ/Tháng");
        title.setText(post.getTitle());
        imgUser.setImageBitmap(hinh);
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
        firebaseAdmin.readOnePostAdmin(AdminPostDetailActivity.this, idpost, listComment, adminCommentAdapter, imagePost);
    }
}