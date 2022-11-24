package com.example.appnhatro;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Firebase.FireBasePostFavorite;
import com.example.appnhatro.Models.Favorite;
import com.example.appnhatro.Models.Post;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class TenantPostFavourite extends AppCompatActivity{
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    androidx.appcompat.widget.SearchView sv_tpf;
    String idUser;
    SharedPreferences sharedPreferences;
    ImageView back;

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
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        idUser = sharedPreferences.getString("idUser", "");
        back = findViewById(R.id.ivBack_YT);
        ListPost();
//        idUser="KH02";
//            databaseReference = FirebaseDatabase.getInstance().getReference("Like");
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
        fireBasePostFavorite.readListPost(posts,tenantPostFavouriteAdapter,idUser);
        recyclerView.setAdapter(tenantPostFavouriteAdapter);
        tenantPostFavouriteAdapter.setOnItemClickListener(new TenantPostFavouriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                    fireBasePostFavorite.readDataItem(position,posts,TenantPostFavourite.this);
            }
        });
        sv_tpf = findViewById(R.id.sv_tpf);
        sv_tpf.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                tenantPostFavouriteAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                tenantPostFavouriteAdapter.getFilter().filter(s);
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
}
