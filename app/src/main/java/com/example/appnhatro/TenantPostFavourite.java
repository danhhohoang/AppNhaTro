package com.example.appnhatro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Firebase.FireBasePostFavorite;
import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.Favorite;
import com.example.appnhatro.Models.LikedPostModel;
import com.example.appnhatro.Models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TenantPostFavourite extends AppCompatActivity{
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    String idUser;
    //List horizone
    private TenantPostFavouriteAdapter tenantPostFavouriteAdapter;
    private ArrayList<Post> posts= new ArrayList<>();
    ArrayList<Favorite> likedPostModels = new ArrayList<>();
    FireBasePostFavorite fireBasePostFavorite = new FireBasePostFavorite();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_favourite);

        ListPost();
//        idUser="KH02";
//        databaseReference = FirebaseDatabase.getInstance().getReference("Like");
//        databaseReference.child(idUser).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Favorite post = dataSnapshot.getValue(Favorite.class);
//                    likedPostModels.add(post);
//                    Log.d("test","This warning :" + post.getIdPost());
//                    Log.d("test","This warning :" + post.getIdPost());
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @SuppressLint("MissingInflatedId")
    public void ListPost(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_tpf);
        tenantPostFavouriteAdapter =  new TenantPostFavouriteAdapter(this, R.layout.layout_item_tenant_post_favourite,posts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        fireBasePostFavorite.readListPost(posts,tenantPostFavouriteAdapter,"KH02");
        recyclerView.setAdapter(tenantPostFavouriteAdapter);
        tenantPostFavouriteAdapter.setOnItemClickListener(new TenantPostFavouriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBasePostFavorite.readDataItem(position,posts,TenantPostFavourite.this);
            }
        });
    }
}
