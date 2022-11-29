package com.example.appnhatro;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Activity.DetailPostUpdateList;
import com.example.appnhatro.Adapters.AdminLandlordListAdapter;
import com.example.appnhatro.Firebase.FireBasePostListFindPeople;
import com.example.appnhatro.Firebase.FireBasePostRenting;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.PostAndPostFindPeople;
import com.example.appnhatro.Models.PostFindRoomateModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TenantPostList extends AppCompatActivity implements RecyclerCRUD{
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    DatabaseReference databaseReference;

    String getID;
    SharedPreferences sharedPreferences;
    ArrayList<Post> list;
    SearchView sv_tpl;
    ImageView back;
    RecyclerView rc;
    private TenantPostListAdapter tenantPostListAdapter;
    private ArrayList<Post> posts = new ArrayList<>();
    FireBasePostListFindPeople fireBasePostListFindPeople = new FireBasePostListFindPeople();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_list);
        ListPost();
        setControl();
        onRead("");
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        getID = sharedPreferences.getString("idUser", "");

        rc = findViewById(R.id.rcv_tpl);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        tenantPostListAdapter =  new TenantPostListAdapter(this, R.layout.layout_item_tenant_post_list,list,this);
        rc.setAdapter(tenantPostListAdapter);
        tenantPostListAdapter.setOnItemClickListener(new TenantPostListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBasePostListFindPeople.readDataItem(position,list,TenantPostList.this);
            }
        });
    }

    public void ListPost(){
        sv_tpl = findViewById(R.id.sv_tpl);
        sv_tpl.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                list.clear();
                onRead(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                list.clear();
                onRead(s);
                return false;
            }
        });
    }

    public void onRead(String keyword){
        databaseReference = FirebaseDatabase.getInstance().getReference("Post_Oghep");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                if (post.getUserID().equals(getID)){
                    if(post!=null){
                        if(post.getHouse_name().toLowerCase().contains(keyword.toLowerCase())){
                            list.add(post);
                        }
                        tenantPostListAdapter.notifyDataSetChanged();
                    }
                }
                tenantPostListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                for (int i = 0;i< posts.size();i++ ){
                    if (post.getId() == posts.get(i).getId()){
                        posts.set(i,post);
                        tenantPostListAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                for (int i = 0;i< posts.size();i++ ){
                    if (post.getId() == posts.get(i).getId()){
                        posts.remove(i);
                        tenantPostListAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                for (int i = 0;i< posts.size();i++ ){
                    if (post.getId() == posts.get(i).getId()){
                        posts.remove(i);
                        tenantPostListAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onUpdateClick(int position) {
        Intent intent = new Intent(TenantPostList.this, DetailPostUpdateList.class);
        intent.putExtra("it_id", list.get(position).getId());
        intent.putExtra("it_userID", list.get(position).getUserID());
        intent.putExtra("it_title", list.get(position).getTitle());
        intent.putExtra("it_address", list.get(position).getAddress());
        intent.putExtra("it_address_district", list.get(position).getAddress_district());
        intent.putExtra("it_price", list.get(position).getPrice());
        intent.putExtra("it_area", list.get(position).getArea());
        intent.putExtra("it_housename", list.get(position).getHouse_name());
        intent.putExtra("it_image", list.get(position).getImage());
        startActivity(intent);
    }

    public static final void setContentNotify(final Dialog dialog, int gravity, int truefalse, int duongdanlayout) {
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

    private void openDialogNotifyNoButton(final Dialog dialog, int gravity, String noidung, int duongdanlayout) {
        setContentNotify(dialog, gravity, Gravity.BOTTOM, duongdanlayout);
        TextView tvNoidung = dialog.findViewById(R.id.tvNoidung_NotifyNoButton);
        tvNoidung.setText(noidung);
        dialog.show();
    }

    @Override
    public void onDeleteClick(int position) {
        Handler handler = new Handler();
        final Dialog dialog = new Dialog(TenantPostList.this);
        openDialogNotifyNoButton(dialog, Gravity.CENTER,"Xóa bài đăng thành công",R.layout.layout_dialog_notify_no_button);
        DatabaseReference databaseReferenceDelete;
        databaseReferenceDelete = FirebaseDatabase.getInstance().getReference("Post");
        databaseReferenceDelete.child(list.get(position).getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (dialog.isShowing()){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    },2000);
                }
            }
        });
    }
    public void setControl(){
        back = findViewById(R.id.iv_tplback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
