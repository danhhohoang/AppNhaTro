package com.example.appnhatro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnhatro.Activity.BookingActivity;
import com.example.appnhatro.Activity.LandLordFeedBack;
import com.example.appnhatro.Activity.LandLordPostDetailActivity;
import com.example.appnhatro.Activity.LandlordWallActivity;
import com.example.appnhatro.Activity.RepportActivity;
import com.example.appnhatro.Activity.TenantCommentActivity;
import com.example.appnhatro.Adapters.TeantCommentAdapter;
import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.Favorite;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.item.Rating;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TenantPostDetail extends AppCompatActivity {
    TextView house_name, area, price, address, title, userId, nameUser,tvVietDanhGia,tvLuotDanhGia;
    MyRecyclerViewAdapter myRecyclerViewAdapterLienQuan;
    TeantCommentAdapter adapterComment;
    ArrayList<Rating> postListComment = new ArrayList<>();
    ArrayList<Post> listLienQuan = new ArrayList<>();
    FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    ImageView imgRating1,imgRating2,imgRating3,imgRating4,imgRating5,hinh,imgFavorite,imgAvatar;
    String it_id,it_idLogin;
    boolean isFavorite = false;
    Button btnReport,btnDatCoc,btnXemPhong;
    RecyclerView rcvComment;
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    Rating ratingComment = new Rating();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_details);
//        it_idLogin = getIntent().getStringExtra("it_idLogin");
        it_idLogin="KH03";
        it_id = getIntent().getStringExtra("it_id");
        control();

        //Comment
        rcvComment = (RecyclerView) findViewById(R.id.rcvCommentPostDetailTenant);
        adapterComment = new TeantCommentAdapter(this,R.layout.item_comment_post_detail_tenant_layout,postListComment,it_idLogin);
        GridLayoutManager gridLayoutManagerRating = new GridLayoutManager(this,1);
        gridLayoutManagerRating.setOrientation(RecyclerView.VERTICAL);
        rcvComment.setLayoutManager(gridLayoutManagerRating);
        rcvComment.setAdapter(adapterComment);

        //Phong tro
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcvPostAnotherDetail);
        myRecyclerViewAdapterLienQuan = new MyRecyclerViewAdapter(this,R.layout.layout_item_list_horizontal,listLienQuan);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        myRecyclerViewAdapterLienQuan.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBaseThueTro.readDataItem(position, listLienQuan, TenantPostDetail.this);
            }
        });
        recyclerView.setAdapter(myRecyclerViewAdapterLienQuan);
        event();
        setImageIcon();
    }

    private void control() {
        house_name = findViewById(R.id.tvNamePostDetail);
        address = findViewById(R.id.tvDiaChiPostDetail);
        area = findViewById(R.id.tvAreaPostDetail);
        price = findViewById(R.id.tvGiaPostDetail);
        title = findViewById(R.id.tvTitle);
        userId = findViewById(R.id.tvIdUserPostDetail);
        nameUser = findViewById(R.id.tvNameUserPostDetail);
        imgFavorite = findViewById(R.id.imgFavoritePostDetail);
        hinh = findViewById(R.id.imgRoomPostDetail);
        imgRating1 = findViewById(R.id.imgRating1);
        imgRating2 = findViewById(R.id.imgRating2);
        imgRating3 =findViewById(R.id.imgRating3);
        imgRating4=findViewById(R.id.imgRating4);
        imgRating5=findViewById(R.id.imgRating5);
        imgAvatar = findViewById(R.id.img_tenant_post_details_Landlord_avatar); //Minh them moi
        btnReport = findViewById(R.id.btnRepost);
        btnXemPhong = findViewById(R.id.btnXPhong);
        tvLuotDanhGia = findViewById(R.id.tvLuotDanhGia);
        tvVietDanhGia = findViewById(R.id.tvVietDanhGia);
    }

    public void setDuLieu(Post post, Bitmap bitmap){
        house_name.setText(post.getHouse_name());
        address.setText(post.getAddress());
        area.setText(formatter.format(Integer.valueOf(post.getArea())) + "m2");
        price.setText(formatter.format(Integer.valueOf(post.getPrice())) + " đ/Tháng");
        title.setText(post.getTitle());
        userId.setText("id:" + post.getUserID());
        hinh.setImageBitmap(bitmap);
    }

    public void setName(user User){
        nameUser.setText(User.getName());
    }
    public void event(){
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strname = house_name.getText().toString();
                String strID = userId.getText().toString();
                String strIDpost = it_id;
                Intent intent = new Intent(TenantPostDetail.this, RepportActivity.class);
                intent.putExtra("Name_hour", strname);
                intent.putExtra("ID", strID);
                intent.putExtra("IdPost", strIDpost);
                startActivity(intent);
            }
        });
        btnXemPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TenantPostDetail.this, BookingActivity.class);
                startActivity(intent);
            }
        });

        //Minh them moi su kien cho viec click vao avatar
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TenantPostDetail.this, LandlordWallActivity.class);
                String cutter = userId.getText().toString().replace("id:", "");
                intent.putExtra("KEY_ID", cutter);
                startActivity(intent);
            }
        });
        tvVietDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireBaseThueTro.checkHistory(TenantPostDetail.this,it_id,it_idLogin);
                }
        });
        adapterComment.setOnItemClickListener(new TeantCommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                TextView menu = view.findViewById(R.id.tvOptions);
                menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(v.getContext(), menu);
                        popupMenu.inflate(R.menu.menu_comment_landlord);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.xoa:
                                        fireBaseThueTro.deleteCommentForUser(postListComment.get(position));
                                        tvVietDanhGia.setVisibility(View.VISIBLE);
                                        ratingComment = new Rating();
                                        break;
                                    case R.id.sua:
                                        Intent intent = new Intent(TenantPostDetail.this, TenantCommentActivity.class);
                                        intent.putExtra("Rating", ratingComment);
                                        startActivity(intent);
                                        break;
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                    }
                });
            }
        });
    }
    public void setImageIcon() {
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) {
                    deleteFromFavorite(it_id, it_idLogin);
                } else {
                    addToFavorite(it_id, it_idLogin);
                }
            }
        });

    }

    private void loadFavoritePost(String postId, String userId) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Like").child(userId).child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    isFavorite = true;
                    imgFavorite.setSelected(isFavorite);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addToFavorite(String postId, String userId) {
        Favorite favorite = new Favorite(userId, postId);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Like");
        databaseReference.child(userId).child(postId).setValue(favorite)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        isFavorite = true;
                        imgFavorite.setSelected(true);
                    }
                });
    }

    private void deleteFromFavorite(String postId, String userId) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Like");
        databaseReference.child(userId).child(postId).removeValue().addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(
                            @NonNull Task<Void> task) {
                        isFavorite = false;
                        imgFavorite.setSelected(false);
                    }
                });
    }
    public void setRatingg(int Rating,String sl) {
        if (Rating == 5) {
            imgRating1.setSelected(true);
            imgRating2.setSelected(true);
            imgRating3.setSelected(true);
            imgRating4.setSelected(true);
            imgRating5.setSelected(true);
        } else if (Rating == 4) {
            imgRating1.setSelected(true);
            imgRating2.setSelected(true);
            imgRating3.setSelected(true);
            imgRating4.setSelected(true);
            imgRating5.setSelected(false);
        } else if (Rating == 3) {
            imgRating1.setSelected(true);
            imgRating2.setSelected(true);
            imgRating3.setSelected(true);
            imgRating4.setSelected(false);
            imgRating5.setSelected(false);
        } else if (Rating == 2) {
            imgRating1.setSelected(true);
            imgRating2.setSelected(true);
            imgRating3.setSelected(false);
            imgRating4.setSelected(false);
            imgRating5.setSelected(false);
        } else if (Rating == 1) {
            imgRating1.setSelected(true);
            imgRating2.setSelected(false);
            imgRating3.setSelected(false);
            imgRating4.setSelected(false);
            imgRating5.setSelected(false);
        } else {
            imgRating1.setSelected(false);
            imgRating2.setSelected(false);
            imgRating3.setSelected(false);
            imgRating4.setSelected(false);
            imgRating5.setSelected(false);
        }
        tvLuotDanhGia.setText(sl+" Lượt đánh giá");
    }
    public void setLayOutComment(Boolean check){
        if(check) {
            rcvComment.setVisibility(View.VISIBLE);
        }else{
            rcvComment.setVisibility(View.GONE);
        }
    }
    public void setVisibilityWriteComment(){
        tvVietDanhGia.setVisibility(View.GONE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        fireBaseThueTro.docListPost(listLienQuan,myRecyclerViewAdapterLienQuan);
        fireBaseThueTro.readOnePost(this,it_id);
        fireBaseThueTro.getRatingPost(this,it_id,adapterComment,postListComment,ratingComment,it_idLogin);
        loadFavoritePost(it_id, it_idLogin);
    }
    public void gotoCommentActivity(){
        ratingComment.setIdPost(it_id);
        ratingComment.setIdUser(it_idLogin);
        ratingComment.setRating("0");
        Intent intent = new Intent(TenantPostDetail.this, TenantCommentActivity.class);
        intent.putExtra("Rating", ratingComment);
        startActivity(intent);
    }
    public void dialogg(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TenantPostDetail.this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn chưa ở cặn hộ này nên không thể đánh giá");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}