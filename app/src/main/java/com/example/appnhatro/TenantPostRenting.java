package com.example.appnhatro;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Firebase.FireBasePostRenting;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.Models.user;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TenantPostRenting extends AppCompatActivity {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    //List horizone
    RecyclerView rc;
    private TenantPostRentingAdapter tenantPostRentingAdapter;
    private ArrayList<Post> posts = new ArrayList<>();
    ImageView back;
    SearchView sv_tpr;
    String iduser;
    SharedPreferences sharedPreferences;
    FireBasePostRenting fireBasePostRenting = new FireBasePostRenting();
    DatabaseReference databaseReferenceHistory;
    DatabaseReference databaseReferencePost;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_renting);
        ListPost();
        onRead("");

        rc = findViewById(R.id.rcv_tpr);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this));

        posts = new ArrayList<>();
        tenantPostRentingAdapter =  new TenantPostRentingAdapter(this, R.layout.layout_item_tenant_post_renting,posts);
        rc.setAdapter(tenantPostRentingAdapter);

        tenantPostRentingAdapter.setOnItemClickListener(new TenantPostRentingAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBasePostRenting.readDataItem(position,posts,TenantPostRenting.this);
            }
        });
    }

    public void ListPost(){
        sv_tpr = findViewById(R.id.sv_tpr);
        back = findViewById(R.id.iv_tprback);
        sv_tpr.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                posts.clear();
                onRead(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                posts.clear();
                onRead(s);
                return false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void onRead(String keyword){
        databaseReferencePost = FirebaseDatabase.getInstance().getReference("Post");
        databaseReferenceHistory = FirebaseDatabase.getInstance().getReference("HistoryTransaction");
        databaseReferenceHistory.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TransactionModel transactionModel = snapshot.getValue(TransactionModel.class);
                if (transactionModel.getId_user().equals("KH02")){
                    databaseReferencePost.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Post post = snapshot.getValue(Post.class);
                            ArrayList<Post> list = new ArrayList<>();
                            if (post.getUserID().equals("KH02")){
                                if(post!=null){
                                    if(post.getHouse_name().toLowerCase().contains(keyword.toLowerCase())){
                                        list.add(post);
                                    }
                                    posts.clear();
                                    posts.addAll(list);
                                    tenantPostRentingAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TransactionModel transactionModel = snapshot.getValue(TransactionModel.class);
                if (transactionModel.getId_user().equals("KH02")){
                    databaseReferencePost.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Post post = snapshot.getValue(Post.class);
                            ArrayList<Post> list = new ArrayList<>();
                            if (post.getUserID().equals("KH02")){
                                if(post!=null){
                                    if(post.getHouse_name().toLowerCase().contains(keyword.toLowerCase())){
                                        list.add(post);
                                    }
                                    posts.clear();
                                    posts.addAll(posts);
                                    tenantPostRentingAdapter.notifyDataSetChanged();
                                }
                            }

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                TransactionModel transactionModel = snapshot.getValue(TransactionModel.class);
                if (transactionModel.getId_user().equals("KH02")){
                    databaseReferencePost.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Post post = snapshot.getValue(Post.class);
                            ArrayList<Post> list = new ArrayList<>();
                            if (post.getUserID().equals("KH02")){
                                if(post!=null){
                                    if(post.getHouse_name().toLowerCase().contains(keyword.toLowerCase())){
                                        list.add(post);
                                    }
                                    posts.clear();
                                    posts.addAll(posts);
                                    tenantPostRentingAdapter.notifyDataSetChanged();
                                }
                            }

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TransactionModel transactionModel = snapshot.getValue(TransactionModel.class);
                if (transactionModel.getId_user().equals("KH02")){
                    databaseReferencePost.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Post post = snapshot.getValue(Post.class);
                            ArrayList<Post> list = new ArrayList<>();
                            if (post.getUserID().equals("KH02")){
                                if(post!=null){
                                    if(post.getHouse_name().toLowerCase().contains(keyword.toLowerCase())){
                                        list.add(post);
                                    }
                                    posts.clear();
                                    posts.addAll(posts);
                                    tenantPostRentingAdapter.notifyDataSetChanged();
                                }
                            }

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
