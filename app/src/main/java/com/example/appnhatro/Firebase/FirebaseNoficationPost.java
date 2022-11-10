package com.example.appnhatro.Firebase;

import androidx.annotation.NonNull;

import com.example.appnhatro.Adapters.LandLorNotificationPost;
import com.example.appnhatro.Adapters.LandLordHomeListAdapter;
import com.example.appnhatro.Models.Dangbaimodels;
import com.example.appnhatro.Models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseNoficationPost {
    public FirebaseNoficationPost() {
    }
//    public void LayThongTin(ArrayList<Post> list , int position){
//
//    }
    public void readListPostFromUser(LandLorNotificationPost landLordHomeListAdapter, ArrayList<Dangbaimodels> list){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Post_Oghep").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Dangbaimodels post = dataSnapshot.getValue(Dangbaimodels.class);
                    list.add(post);
                }
                landLordHomeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
