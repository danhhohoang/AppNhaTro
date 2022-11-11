package com.example.appnhatro.Firebase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.PostFindRoomateModel;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.TenantPostDetail;
import com.example.appnhatro.TenantPostRentingAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBasePostRenting {
    public FireBasePostRenting() {

    }
    public void readPostFindPeople(ArrayList<Post> list, TenantPostRentingAdapter tenantPostRentingAdapter,String userTenantID){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("HistoryTransaction");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TransactionModel transactionModel = dataSnapshot.getValue(TransactionModel.class);
                    if (transactionModel.getId_user().equals(userTenantID)){
                        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    Post post = dataSnapshot.getValue(Post.class);
                                    if (post.getId().equals(transactionModel.getPost())){
                                        list.add(post);
                                    }
                                }
                                tenantPostRentingAdapter.notifyDataSetChanged();
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
