package com.example.appnhatro.Firebase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.appnhatro.Activity.Detail_Notification_Landlor;
import com.example.appnhatro.Activity.Landlord_Notification_Activity;
import com.example.appnhatro.Adapters.Landlord_Notification_Adapter;
import com.example.appnhatro.Models.DatLichModels;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.TenantPostDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseLandlordNotification {
    public FirebaseLandlordNotification() {

    }

    public void readPostFindPeople(ArrayList<DatLichModels> list, Landlord_Notification_Adapter landlord_notification_adapter, String userTenantID) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("booking");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DatLichModels datLichModels = dataSnapshot.getValue(DatLichModels.class);
                    if (datLichModels.getIdUser().equals(userTenantID)) {
                        list.add(datLichModels);
                    }
                }
                landlord_notification_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readFirebase(int position, ArrayList<DatLichModels> list, Context context) {
        DatLichModels data = list.get(position);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("booking").child(data.getName()).child(data.getPhone()).child(data.getTime()).child(data.getDate()).child(data.getNotes()).child(data.getIdUser()).child(data.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Intent intent = new Intent(context, Detail_Notification_Landlor.class);
               intent.putExtra("Ten nguoi dat phong", data.getName());
               intent.putExtra("Số Điện thoai", data.getName());
               intent.putExtra("Thời gian đến xem phòng", data.getTime());
               intent.putExtra("ngày đến xem phòng", data.getDate());
               intent.putExtra("ghi chu", data.getNotes());
               intent.putExtra("idUSer", data.getIdUser());
               intent.putExtra("id", data.getId());
               context.startActivity(intent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
