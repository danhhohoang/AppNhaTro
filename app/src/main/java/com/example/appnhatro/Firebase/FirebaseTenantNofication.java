package com.example.appnhatro.Firebase;

import androidx.annotation.NonNull;

import com.example.appnhatro.Adapters.Tenant_Notification_Adapter;
import com.example.appnhatro.Models.Notificationbooking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseTenantNofication {
    public FirebaseTenantNofication() {
    }
    public void readPostFindPeople(ArrayList<Notificationbooking> list, Tenant_Notification_Adapter tenant_notification_adapter, String idUser) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("Notification");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Notificationbooking> notifi = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Notificationbooking notificationbooking = dataSnapshot.getValue(Notificationbooking.class);
                    if (notificationbooking.getIdUser().equals(idUser))
                        {
                            notifi.add(notificationbooking);
                    }
                }
                list.clear();
                list.addAll(notifi);
                tenant_notification_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    public void readFirebase(int position, ArrayList<Notificationbooking> list, Context context) {
//        Notificationbooking data = list.get(position);
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference();
//        databaseReference.child("Notification").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Intent intent = new Intent(context, Detail_Notification_Landlor.class);
//                context.startActivity(intent);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
