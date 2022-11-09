package com.example.appnhatro.Firebase;

import androidx.annotation.NonNull;

import com.example.appnhatro.Adapters.LandLordHomeListAdapter;
import com.example.appnhatro.Models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBaseLandLord {
    public FireBaseLandLord() {
    }
    public void xoaPhongTro(ArrayList<Post> list , int position){

    }
    public void readListPostFromUser(LandLordHomeListAdapter landLordHomeListAdapter,ArrayList<Post> list,String idLandLord){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    if(post.getUserID().equals(idLandLord)) {
                        list.add(post);
                    }
                }
                landLordHomeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
