package com.example.appnhatro.Firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.appnhatro.Activity.RepportActivity;
import com.example.appnhatro.UserSignUp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseUserSignUp {
    public FirebaseUserSignUp(){
    }
    public void autoid(Context context){
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference();
//        databaseReference.child("user").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ArrayList<String> dsPost = new ArrayList<>();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    dsPost.add(dataSnapshot.getKey());
//                }
//                String[] temp = dsPost.get(dsPost.size() - 1).split("KH");
//                String id="";
//                if(Integer.parseInt(temp[1]) < 10){
//                    id = "KH0" + (Integer.parseInt(temp[1]) + 1);
//                }else {
//                    id = "KH" + (Integer.parseInt(temp[1]) + 1);
//                }
//                ((UserSignUp) context).SetID(id);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}
