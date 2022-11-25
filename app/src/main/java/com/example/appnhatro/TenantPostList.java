package com.example.appnhatro;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Activity.DetailPostUpdateList;
import com.example.appnhatro.Firebase.FireBasePostListFindPeople;
import com.example.appnhatro.Firebase.FireBasePostRenting;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.PostAndPostFindPeople;
import com.example.appnhatro.Models.PostFindRoomateModel;
import com.google.android.gms.tasks.OnSuccessListener;
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

    SearchView sv_tpl;
    //List horizone
    private TenantPostListAdapter tenantPostListAdapter;
    private ArrayList<Post> posts = new ArrayList<>();
    FireBasePostListFindPeople fireBasePostListFindPeople = new FireBasePostListFindPeople();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_list);
        ListPost();
    }

    public void ListPost(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_tpl);
        sv_tpl = findViewById(R.id.sv_tpl);
        tenantPostListAdapter =  new TenantPostListAdapter(this, R.layout.layout_item_tenant_post_list,posts,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        fireBasePostListFindPeople.readPostFindPeople(posts,tenantPostListAdapter,"KH02");
        recyclerView.setAdapter(tenantPostListAdapter);

        tenantPostListAdapter.setOnItemClickListener(new TenantPostListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBasePostListFindPeople.readDataItem(position,posts,TenantPostList.this);
            }
        });

        sv_tpl.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                tenantPostListAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                tenantPostListAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public void onUpdateClick(int position) {
        Intent intent = new Intent(TenantPostList.this, DetailPostUpdateList.class);
        intent.putExtra("it_id", posts.get(position).getId());
        intent.putExtra("it_userID", posts.get(position).getUserID());
        intent.putExtra("it_title", posts.get(position).getTitle());
        intent.putExtra("it_address", posts.get(position).getAddress());
        intent.putExtra("it_address_district", posts.get(position).getAddress_district());
        intent.putExtra("it_price", posts.get(position).getPrice());
        intent.putExtra("it_area", posts.get(position).getArea());
        intent.putExtra("it_housename", posts.get(position).getHouse_name());
        intent.putExtra("it_image", posts.get(position).getImage());
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
        databaseReferenceDelete.child(posts.get(position).getId()).removeValue(new DatabaseReference.CompletionListener() {
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
}
