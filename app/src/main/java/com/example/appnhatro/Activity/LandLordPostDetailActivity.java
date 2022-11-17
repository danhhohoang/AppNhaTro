package com.example.appnhatro.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.appnhatro.Adapters.LandLordCommentAdapter;
import com.example.appnhatro.Adapters.LandLordHomeListAdapter;
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
    LandLordCommentAdapter commentAdapter;
    FireBaseLandLord fireBaseLandLord = new FireBaseLandLord();
    String idPost = "";
    TextView house_name, area, price, address, title, status, sumlike;
    ImageView hinh, back, rating1, rating2, rating3, rating4, rating5;
    Button btnXoa, btnSua;
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandLordPostDetailActivity.this, LandLordUpdatePostActivity.class);
                intent.putExtra("it_id", idPost);
                startActivity(intent);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LandLordPostDetailActivity.this);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn chưa chọn ảnh");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fireBaseLandLord.deletePost(idPost);
                        finish();
                    }
                });
                builder.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        commentAdapter.setOnItemClickListener(new LandLordCommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                TextView phanhoi = view.findViewById(R.id.tvPhanHoi);
                phanhoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LandLordPostDetailActivity.this, LandLordFeedBack.class);
                        intent.putExtra("Rating", listComment.get(position));
                        startActivity(intent);
                    }
                });
                TextView menu = view.findViewById(R.id.textViewOptions);
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
                                        fireBaseLandLord.deleteFeedBack(listComment.get(position));
                                        break;
                                    case R.id.sua:
                                        Intent intent = new Intent(LandLordPostDetailActivity.this, LandLordFeedBack.class);
                                        intent.putExtra("Rating", listComment.get(position));
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

    public void setDuLieu(Post post, Bitmap bitmap) {
        house_name.setText(post.getHouse_name());
        address.setText(post.getAddress());
        area.setText(formatter.format(Integer.valueOf(post.getArea())) + "m2");
        price.setText(formatter.format(Integer.valueOf(post.getPrice())) + " đ/Tháng");
        title.setText(post.getTitle());
        hinh.setImageBitmap(bitmap);
    }

    private void control() {
        rcvComment = (RecyclerView) findViewById(R.id.rcvCommentPostDetailLandLord);
        commentAdapter = new LandLordCommentAdapter(this, R.layout.landlord_item_comment_post_layout, listComment);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvComment.setLayoutManager(gridLayoutManager);
        rcvComment.setAdapter(commentAdapter);
        house_name = findViewById(R.id.tvNamePostDetailLandLord);
        address = findViewById(R.id.tvDiaChiPostDetailLandLord);
        price = findViewById(R.id.tvGiaPostDetailLandLord);
        area = findViewById(R.id.tvAreaPostDetailLandLord);
        status = findViewById(R.id.tvTinhTrangLandLord);
        sumlike = findViewById(R.id.tvSLLikePostLandLord);
        title = findViewById(R.id.tvTitlePostDetailLandLord);
        hinh = findViewById(R.id.imgRoomPostDetailLandLord);
        btnSua = findViewById(R.id.btnSuaPhongLandLord);
        btnXoa = findViewById(R.id.btnXoaPhongPostDetailLandLord);
        back = findViewById(R.id.imgBackPostDetailLandLord);
        rating1 = findViewById(R.id.imgRating1LandLord);
        rating2 = findViewById(R.id.imgRating2LandLord);
        rating3 = findViewById(R.id.imgRating3LandLord);
        rating4 = findViewById(R.id.imgRating4LandLord);
        rating5 = findViewById(R.id.imgRating5LandLord);
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

    public void setLike(String Like) {
        sumlike.setText(Like);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fireBaseLandLord.readOnePostLandLord(this, idPost, listComment, commentAdapter);
    }
}