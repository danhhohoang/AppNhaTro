package com.example.appnhatro.Firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.appnhatro.Adapters.AdminTenantListAdapter;
import com.example.appnhatro.Adapters.TenantSearchAdapter;
import com.example.appnhatro.Models.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBaseTenantList {

    public void getAllUser(Context context, ArrayList<user> userArrayList, AdminTenantListAdapter adapter){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userFirebase = firebaseDatabase.getReference("user");
        userFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    user _user = dataSnapshot.getValue(user.class);
                    userArrayList.add(_user);
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
