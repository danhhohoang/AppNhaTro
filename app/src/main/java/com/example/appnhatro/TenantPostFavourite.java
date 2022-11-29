package com.example.appnhatro;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Firebase.FireBasePostFavorite;
import com.example.appnhatro.Models.Favorite;
import com.example.appnhatro.Models.Post;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TenantPostFavourite extends AppCompatActivity{
    private ArrayList<String> persons = new ArrayList<>();
    androidx.appcompat.widget.SearchView sv_tpf;
    String idUser;
    SharedPreferences sharedPreferences;
    ImageView back;

    String getID;
    RecyclerView rc;
    //List horizone
    private TenantPostFavouriteAdapter tenantPostFavouriteAdapter;
    private ArrayList<Post> posts= new ArrayList<>();
    ArrayList<Favorite> likedPostModels = new ArrayList<>();
    FireBasePostFavorite fireBasePostFavorite = new FireBasePostFavorite();
    DatabaseReference databaseReferenceLike;
    DatabaseReference databaseReferencePost;
    DatabaseReference databaseReferencePostOghep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_favourite);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        getID = sharedPreferences.getString("idUser", "");
        ListPost();
        onRead("");

        rc = findViewById(R.id.rcv_tpf);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this));

        posts = new ArrayList<>();
        tenantPostFavouriteAdapter =  new TenantPostFavouriteAdapter(this, R.layout.layout_item_tenant_post_favourite,posts);
        rc.setAdapter(tenantPostFavouriteAdapter);
        tenantPostFavouriteAdapter.setOnItemClickListener(new TenantPostFavouriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBasePostFavorite.readDataItem(position,posts,TenantPostFavourite.this);
            }
        });
    }

    @SuppressLint("MissingInflatedId")
    public void ListPost(){
        back = findViewById(R.id.iv_tpfback);
        sv_tpf = findViewById(R.id.sv_tpf);
        sv_tpf.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
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
        databaseReferenceLike = FirebaseDatabase.getInstance().getReference("Like");
        databaseReferencePost = FirebaseDatabase.getInstance().getReference("Post");
        databaseReferencePostOghep = FirebaseDatabase.getInstance().getReference("Post_Oghep");
        databaseReferenceLike.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                databaseReferenceLike.child(snapshot.getKey()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Favorite favorite = snapshot.getValue(Favorite.class);
                        if(favorite.getIdUser().equals(getID)){
                            databaseReferencePost.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    Post post = snapshot.getValue(Post.class);
                                    if (post.getId().equals(favorite.getIdPost())){
                                        if(post!=null){
                                            if(post.getHouse_name().toLowerCase().contains(keyword.toLowerCase())){
                                                posts.add(post);
                                            }
                                            tenantPostFavouriteAdapter.notifyDataSetChanged();
                                        }
                                    }

                                }

                                @Override
                                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    Post post = snapshot.getValue(Post.class);
                                    for (int i = 0;i< posts.size();i++ ){
                                        if (post.getId() == posts.get(i).getId()){
                                            posts.set(i,post);
                                            tenantPostFavouriteAdapter.notifyItemChanged(i);
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
                                            tenantPostFavouriteAdapter.notifyItemChanged(i);
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
                                            tenantPostFavouriteAdapter.notifyItemChanged(i);
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            databaseReferencePostOghep.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    Post post = snapshot.getValue(Post.class);
                                    if (post.getId().equals(favorite.getIdPost())){
                                        posts.add(post);
                                    }
                                    tenantPostFavouriteAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                    Post post = snapshot.getValue(Post.class);
                                    for (int i = 0;i< posts.size();i++ ){
                                        if (post.getId() == posts.get(i).getId()){
                                            posts.set(i, post);
                                            tenantPostFavouriteAdapter.notifyItemChanged(i);
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
                                            tenantPostFavouriteAdapter.notifyItemChanged(i);
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
                                            tenantPostFavouriteAdapter.notifyItemChanged(i);
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Post post = snapshot.getValue(Post.class);
                        for (int i = 0;i< posts.size();i++ ){
                            if (post.getId() == posts.get(i).getId()){
                                posts.set(i, post);
                                tenantPostFavouriteAdapter.notifyItemChanged(i);
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
                                tenantPostFavouriteAdapter.notifyItemChanged(i);
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
                                tenantPostFavouriteAdapter.notifyItemChanged(i);
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
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                for (int i = 0;i< posts.size();i++ ){
                    if (post.getId() == posts.get(i).getId()){
                        posts.set(i, post);
                        tenantPostFavouriteAdapter.notifyItemChanged(i);
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
                        tenantPostFavouriteAdapter.notifyItemChanged(i);
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
                        tenantPostFavouriteAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
