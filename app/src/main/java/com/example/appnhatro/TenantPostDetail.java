package com.example.appnhatro;

import static com.example.appnhatro.TenantPasswordChangeActivity.setContentNotify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.appnhatro.Models.TransactionModel;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;

public class TenantPostDetail extends AppCompatActivity {
    TextView house_name, area, price, address, title, userId, nameUser,tvVietDanhGia,tvLuotDanhGia,tvTheLoai;
    MyRecyclerViewAdapter myRecyclerViewAdapterLienQuan;
    TeantCommentAdapter adapterComment;
    ArrayList<Rating> postListComment = new ArrayList<>();
    ArrayList<Post> listLienQuan = new ArrayList<>();
    FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    ImageView imgRating1,imgRating2,imgRating3,imgRating4,imgRating5,hinh,imgFavorite,imgAvatar, back;
    String it_id,it_idLogin;
    boolean isFavorite = false;
    Button btnReport,btnXemPhong,btnDatCoc;
   

    RecyclerView rcvComment;
    LinearLayout rcvRatingPost;
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    Rating ratingComment = new Rating();
    String idPost,idUser,idAuto;
    double historyFee;
    String currentDate;
    SharedPreferences sharedPreferences;

    private String amount = "5000000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Merchant123556666";
    private String merchantCode = "MOMOIQA420180417";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Đặt phòng trọ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_details);

        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
       it_idLogin = getIntent().getStringExtra("it_idLogin");
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        it_idLogin = sharedPreferences.getString("idUser", "");
        it_id = getIntent().getStringExtra("it_id");
        control();

        getID();
        idPost = it_id;
        idUser = it_idLogin;
        historyFee = Integer.valueOf(price.getText().toString()) * 0.5;

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String a = it_id.substring(0,2);
        if(a.equals("P_")){
            tvTheLoai.setText("Ở Ghép");
            btnDatCoc.setVisibility(View.GONE);
            btnLienHe.setVisibility(View.VISIBLE);
            btnReport.setVisibility(View.GONE);
            btnXemPhong.setVisibility(View.GONE);
            imgFavorite.setVisibility(View.GONE);
            tvVietDanhGia.setVisibility(View.GONE);
            tvLuotDanhGia.setVisibility(View.GONE);
            rcvRatingPost.setVisibility(View.GONE);
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
            eventPostOGhep();
            setImageIcon();
        }else {
            tvTheLoai.setText("Cho Thuê");
            btnDatCoc.setVisibility(View.VISIBLE);
            btnLienHe.setVisibility(View.GONE);
            btnReport.setVisibility(View.VISIBLE);
            btnXemPhong.setVisibility(View.VISIBLE);
            imgFavorite.setVisibility(View.VISIBLE);
            tvVietDanhGia.setVisibility(View.VISIBLE);
            tvLuotDanhGia.setVisibility(View.VISIBLE);
            rcvRatingPost.setVisibility(View.VISIBLE);
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
    }

    private void control() {
        btnDatCoc = findViewById(R.id.btnDatCoc);
        tvTheLoai=findViewById(R.id.tvChoThue);
        btnLienHe= findViewById(R.id.btnLienHePostDetail);
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
        btnDatCoc = findViewById(R.id.btnDatCoc);
        tvLuotDanhGia = findViewById(R.id.tvLuotDanhGia);
        tvVietDanhGia = findViewById(R.id.tvVietDanhGia);
        back = findViewById(R.id.btn_detailback);
        rcvRatingPost = findViewById(R.id.rcvRatingPost);
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
    public void eventPostOGhep(){
        btnLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TenantPostDetail.this);
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();
                String cutter = userId.getText().toString().replace("id:", "");
                databaseReference.child("user").child(cutter).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user User = snapshot.getValue(user.class);
                        builder.setTitle("Liên Hệ");
                        builder.setMessage("Số điện thoại:"+User.getPhone()+"");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        loadFavoritePost(it_id, it_idLogin);
        String id = it_id.substring(0,2);
        if(id.equals("P_")){
            fireBaseThueTro.docPostLienQuanOGhep(listLienQuan,myRecyclerViewAdapterLienQuan);
            fireBaseThueTro.readOnePostOGhep(this,it_id);
        }else {
            fireBaseThueTro.docPostLienQuanPost(listLienQuan,myRecyclerViewAdapterLienQuan);
            fireBaseThueTro.readOnePost(this,it_id);
            fireBaseThueTro.getRatingPost(this,it_id,adapterComment,postListComment,ratingComment,it_idLogin);
        }
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

    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", historyFee); //Kiểu integer
        eventValue.put("orderId", "123"); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", merchantNameLabel);//gán nhãn
        eventValue.put("fee", "0"); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);


    }
    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("thanhcong", data.getStringExtra("message"));
                    if (data.getStringExtra("message").equals("thanhcong")){
                        DatabaseReference databaseReference;
                        databaseReference = FirebaseDatabase.getInstance().getReference("HistoryTransaction"+idAuto);
                        TransactionModel transactionModel = new TransactionModel(idAuto,"0",it_idLogin,idPost,"1",currentDate,"0",String.valueOf(historyFee));
                        databaseReference.setValue(transactionModel);
                    }
//                    tvMessage.setText("message: " + "Get token " + data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d("thanhcong", "khong thanh cong");
//                        tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
//                    tvMessage.setText("message: " + message);
                    Log.d("thanhcong", "khong thanh cong");
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
//                    tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                    Log.d("thanhcong", "khong thanh cong");
                } else {
                    //TOKEN FAIL
//                    tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                    Log.d("thanhcong", "khong thanh cong");
                }
            } else {
//                tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                Log.d("thanhcong", "khong thanh cong");
            }
        } else {
//            tvMessage.setText("message: " + this.getString(R.string.not_receive_info_err));
            Log.d("thanhcong", "khong thanh cong");
        }
    }

    private void setMomo() {
        btnDatCoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(TenantPostDetail.this);
                openDialogNotify(Gravity.CENTER,"50000",R.layout.layout_dialog_notify_payment);
                requestPayment();
            }
        });
    }

    public final void setContentNotify(final Dialog dialog, int gravity, int truefalse, int duongdanlayout) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(duongdanlayout);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams winLayoutParams = window.getAttributes();
        winLayoutParams.gravity = gravity;
        window.setAttributes(winLayoutParams);

        if (truefalse == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
    }

    private void openDialogNotify(int gravity, String noidung, int duongdanlayout) {
        final Dialog dialog = new Dialog(this);
        setContentNotify(dialog, gravity,Gravity.CENTER, duongdanlayout);
        TextView tienDatCoc = findViewById(R.id.tvNoidung_Price);
        Button close = dialog.findViewById(R.id.btnLeft_NotifyYesNo);
        Button submit = dialog.findViewById(R.id.btnRight_MOMO);
        tienDatCoc.setText(noidung);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPayment();
            }
        });
        dialog.show();
    }

    public void getID(){
        FirebaseDatabase firebaseDatabaseID = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceID = firebaseDatabaseID.getReference("HistoryTransaction");
        databaseReferenceID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsPost = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dsPost.add(dataSnapshot.getKey());
                }
                String[] temp = dsPost.get(dsPost.size() - 1).split("HT");
                String id="";
                if(Integer.parseInt(temp[1]) < 10){
                    id = "HT" + (Integer.parseInt(temp[1]) + 1);
                }else {
                    id = "HT" + (Integer.parseInt(temp[1]) + 1);
                }
                idAuto= id;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}