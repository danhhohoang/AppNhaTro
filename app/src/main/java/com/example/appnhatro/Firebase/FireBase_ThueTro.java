package com.example.appnhatro.Firebase;

import android.content.Context;

import com.example.appnhatro.MyRecyclerViewAdapter;
import com.example.appnhatro.Room;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FireBase_ThueTro {
    private void ghiPost(Context context, MyRecyclerViewAdapter adapter, ArrayList<Room> list,Room room) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Post");
//        databaseReference.child(room.get()).child("")
    }
}
