package com.example.appnhatro.Firebase;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.appnhatro.Models.Favorite;
import com.example.appnhatro.Models.LikedPostModel;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.TenantPostDetail;
import com.example.appnhatro.TenantPostFavouriteAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBasePostFavorite {
    public FireBasePostFavorite() {
    }

    public void readListPost(ArrayList<Post> list, TenantPostFavouriteAdapter tenantPostFavouriteAdapter,String idKH) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("Post");
        DatabaseReference databaseReference3 = firebaseDatabase.getReference("Like");
        databaseReference.child("Like").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    databaseReference3.child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                Favorite favorite = dataSnapshot1.getValue(Favorite.class);
                                if(favorite.getIdUser().equals(idKH)){
                                    databaseReference2.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                Post post = dataSnapshot.getValue(Post.class);
                                                if (post.getId().equals(favorite.getIdPost())){
                                                    list.add(post);
                                                }
                                            }
                                            tenantPostFavouriteAdapter.notifyDataSetChanged();
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//                    Favorite favorite = dataSnapshot.getValue(Favorite.class);
//                    if(favorite.getIdUser().equals(idKH)){
//                        databaseReference2.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                    Post post = dataSnapshot.getValue(Post.class);
//                                    if (post.getId().equals(favorite.getIdPost())){
//                                        list.add(post);
//                                    }
//                                }
//                                tenantPostFavouriteAdapter.notifyDataSetChanged();
//                            }
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void readWishList(ArrayList<LikedPostModel> list){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Like").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    LikedPostModel post = dataSnapshot.getValue(LikedPostModel.class);
                    list.add(post);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readDataItem(int position, ArrayList<Post> list, Context context) {
        Post data = list.get(position);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").child(data.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent intent = new Intent(context, TenantPostDetail.class);
                intent.putExtra("it_id", data.getId());
                intent.putExtra("it_idLogin", "KH02");
                context.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
