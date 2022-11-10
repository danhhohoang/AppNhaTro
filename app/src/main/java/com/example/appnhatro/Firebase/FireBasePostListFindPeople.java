package com.example.appnhatro.Firebase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.appnhatro.Models.LikedPostModel;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.PostAndPostFindPeople;
import com.example.appnhatro.Models.PostFindRoomateModel;
import com.example.appnhatro.TenantPostDetail;
import com.example.appnhatro.TenantPostListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBasePostListFindPeople {
    public FireBasePostListFindPeople(){
    }

    public void readPostFindPeople(ArrayList<PostAndPostFindPeople> listPFPP, TenantPostListAdapter tenantPostListAdapter){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("Post");
        databaseReference.child("PostFindPeople").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PostAndPostFindPeople postAndPostFindPeople = dataSnapshot.getValue(PostAndPostFindPeople.class);
                    if (postAndPostFindPeople.getId_user().equals("KH02")){
                        databaseReference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    PostAndPostFindPeople postAndPostFindPeople1 = dataSnapshot.getValue(PostAndPostFindPeople.class);
                                    if (postAndPostFindPeople1.getId().equals(postAndPostFindPeople.getPostID())){
                                        listPFPP.add(postAndPostFindPeople1);
                                    }
//                    Log.d("test","aa" + post.getTitle());
                                }
                                tenantPostListAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
//                    Log.d("test","aa" + post.getTitle());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readDataItem(int position, ArrayList<PostFindRoomateModel> list, Context context) {
        PostFindRoomateModel data = list.get(position);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("PostFindPeople").child(data.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent intent = new Intent(context, TenantPostDetail.class);
                intent.putExtra("it_id", data.getTitle());
                intent.putExtra("it_idLogin", "KH02");
                context.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}


