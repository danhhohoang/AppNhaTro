package com.example.appnhatro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.Favorite;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
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
    TextView house_name, area, price, address, title, userId, nameUser;
    MyRecyclerViewAdapter myRecyclerViewAdapterLienQuan;
    ArrayList<Post> postList = new ArrayList<>();
    ArrayList<Post> listLienQuan = new ArrayList<>();
    FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    ImageView imgRating1,imgRating2,imgRating3,imgRating4,imgRating5,hinh,imgFavorite;
    String it_id,it_idLogin;
    boolean isFavorite = false;
    Button btnXoa,btnReport,btnDatCoc,btnXemPhong;
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_details);
        it_idLogin = getIntent().getStringExtra("it_idLogin");
        it_id = getIntent().getStringExtra("it_id");
        control();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcvPostAnotherDetail);
        myRecyclerViewAdapterLienQuan = new MyRecyclerViewAdapter(this,R.layout.layout_item_list_horizontal,listLienQuan);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        fireBaseThueTro.docListPost(listLienQuan,myRecyclerViewAdapterLienQuan);
        myRecyclerViewAdapterLienQuan.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBaseThueTro.readDataItem(position, listLienQuan, TenantPostDetail.this);
            }
        });
        recyclerView.setAdapter(myRecyclerViewAdapterLienQuan);
        event();
        fireBaseThueTro.readOnePost(this,it_id);
        fireBaseThueTro.getRatingPost(this,it_id,it_idLogin);
        loadFavoritePost(it_id, it_idLogin);
        setImageIcon();
    }

    private void control() {
        btnXoa = findViewById(R.id.btnXoaDetailPost);
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
        btnXoa = findViewById(R.id.btnXoaDetailPost);
    }

    public void setDuLieu(Post post, Bitmap bitmap){
        house_name.setText(post.getHour_name());
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
    public void setRating(int rating){
        if(rating==5){
            imgRating1.setSelected(true);
            imgRating2.setSelected(true);
            imgRating3.setSelected(true);
            imgRating4.setSelected(true);
            imgRating5.setSelected(true);
            btnXoa.setVisibility(View.VISIBLE);
        } else if(rating==4){
            imgRating1.setSelected(true);
            imgRating2.setSelected(true);
            imgRating3.setSelected(true);
            imgRating4.setSelected(true);
            btnXoa.setVisibility(View.VISIBLE);
        }
        else if(rating==3){
            imgRating1.setSelected(true);
            imgRating2.setSelected(true);
            imgRating3.setSelected(true);
            btnXoa.setVisibility(View.VISIBLE);
        }
        else if(rating==2){
            imgRating1.setSelected(true);
            imgRating2.setSelected(true);
            btnXoa.setVisibility(View.VISIBLE);
        }else if(rating==1){
            imgRating1.setSelected(true);
            btnXoa.setVisibility(View.VISIBLE);
        }
    }
    public void event(){
        imgRating1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgRating1.setSelected(true);
                imgRating2.setSelected(false);
                imgRating3.setSelected(false);
                imgRating4.setSelected(false);
                imgRating5.setSelected(false);
                btnXoa.setVisibility(View.VISIBLE);
                fireBaseThueTro.addRating(it_id,it_idLogin,"1");
            }
        });
        imgRating2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgRating1.setSelected(true);
                imgRating2.setSelected(true);
                imgRating3.setSelected(false);
                imgRating4.setSelected(false);
                imgRating5.setSelected(false);
                btnXoa.setVisibility(View.VISIBLE);
                fireBaseThueTro.addRating(it_id,it_idLogin,"2");
            }
        });
        imgRating3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgRating1.setSelected(true);
                imgRating2.setSelected(true);
                imgRating3.setSelected(true);
                imgRating4.setSelected(false);
                imgRating5.setSelected(false);
                btnXoa.setVisibility(View.VISIBLE);
                fireBaseThueTro.addRating(it_id,it_idLogin,"3");
            }
        });
        imgRating4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgRating1.setSelected(true);
                imgRating2.setSelected(true);
                imgRating3.setSelected(true);
                imgRating4.setSelected(true);
                imgRating5.setSelected(false);
                btnXoa.setVisibility(View.VISIBLE);
                fireBaseThueTro.addRating(it_id,it_idLogin,"4");
            }
        });
        imgRating5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgRating1.setSelected(true);
                imgRating2.setSelected(true);
                imgRating3.setSelected(true);
                imgRating4.setSelected(true);
                imgRating5.setSelected(true);
                btnXoa.setVisibility(View.VISIBLE);
                fireBaseThueTro.addRating(it_id,it_idLogin,"5");
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireBaseThueTro.deleteRating(it_idLogin,it_id);
                imgRating1.setSelected(false);
                imgRating2.setSelected(false);
                imgRating3.setSelected(false);
                imgRating4.setSelected(false);
                imgRating5.setSelected(false);
                btnXoa.setVisibility(View.GONE);
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

}