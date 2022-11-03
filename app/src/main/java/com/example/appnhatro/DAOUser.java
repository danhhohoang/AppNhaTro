package com.example.appnhatro;

import com.example.appnhatro.Models.user;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOUser {
    private DatabaseReference databaseReference;
    public DAOUser(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user");
    }
    public Task<Void> add(user u){
        return databaseReference.push().setValue(u);
    }

    public Task<Void> update(String key, HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }
}
