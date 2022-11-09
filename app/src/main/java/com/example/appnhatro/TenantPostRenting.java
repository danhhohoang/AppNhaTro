package com.example.appnhatro;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Firebase.FireBasePostFavorite;
import com.example.appnhatro.Firebase.FireBasePostRenting;
import com.example.appnhatro.Models.LikedPostModel;
import com.example.appnhatro.Models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TenantPostRenting extends AppCompatActivity {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    //List horizone
    private TenantPostRentingAdapter tenantPostRentingAdapter;
    private ArrayList<Post> posts = new ArrayList<>();
    FireBasePostRenting fireBasePostRenting = new FireBasePostRenting();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_renting);

        ListPost();

    }
    public void ListPost(){
        RecyclerView recyclerView = findViewById(R.id.rcv_tpr);
        tenantPostRentingAdapter =  new TenantPostRentingAdapter(this, R.layout.layout_item_tenant_post_renting,posts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        fireBasePostRenting.readPostFindPeople(posts,tenantPostRentingAdapter);
        recyclerView.setAdapter(tenantPostRentingAdapter);
        tenantPostRentingAdapter.setOnItemClickListener(new TenantPostRentingAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBasePostRenting.readDataItem(position,posts,TenantPostRenting.this);
            }
        });
    }
}
