package com.example.appnhatro.Firebase;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appnhatro.Activity.Detail_Notification_Landlor;
import com.example.appnhatro.Adapters.Landlord_Notification_Adapter;
import com.example.appnhatro.Models.DatLichModels;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseLandlordNotification {
    public FirebaseLandlordNotification() {

    }

    public void readPostFindPeople(ArrayList<DatLichModels> list, Landlord_Notification_Adapter landlord_notification_adapter, String getID) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("booking");

        databaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DatLichModels datLichModels = snapshot.getValue(DatLichModels.class);
                if (datLichModels.getIdLandlord().equals(getID) && datLichModels.getTt().equals("1")) {
                    list.add(datLichModels);
                }
                landlord_notification_adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DatLichModels datLichModels = snapshot.getValue(DatLichModels.class);
                for (int i = 0; i < list.size(); i++){
                    if (datLichModels.getId() == list.get(i).getId()){
                        list.remove(i);
                        landlord_notification_adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
        databaseReference.child("booking").child(data.getTt()).child(data.getHouname()).child(data.getIdLandlord()).child(data.getName()).child(data.getPhone()).child(data.getTime()).child(data.getDate()).child(data.getNotes()).child(data.getIdUser()).child(data.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent intent = new Intent(context, Detail_Notification_Landlor.class);
                intent.putExtra("Ten nguoi dat phong", data.getName());
                intent.putExtra("Số Điện thoai", data.getPhone());
                intent.putExtra("Thời gian đến xem phòng", data.getTime());
                intent.putExtra("ngày đến xem phòng", data.getDate());
                intent.putExtra("ghi chu", data.getNotes());
                intent.putExtra("idUSer", data.getIdUser());
                intent.putExtra("id", data.getId());
                intent.putExtra("idpost", data.getIdPost());
                intent.putExtra("housname", data.getHouname());
                intent.putExtra("idLandlor", data.getIdLandlord());
                intent.putExtra("tt", data.getTt());
                context.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
