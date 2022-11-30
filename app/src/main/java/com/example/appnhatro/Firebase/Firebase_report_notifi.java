package com.example.appnhatro.Firebase;

import androidx.annotation.NonNull;

import com.example.appnhatro.Adapters.Tenant_report_notifi_adapter;
import com.example.appnhatro.Models.NotifiAdminmodels;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Firebase_report_notifi {

    public Firebase_report_notifi(){
    }
    public void readPostFindPeople(ArrayList<NotifiAdminmodels> list, Tenant_report_notifi_adapter tenant_report_notifi_adapter, String getID) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("Notify");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<NotifiAdminmodels> notifi = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NotifiAdminmodels notifiAdminmodels = dataSnapshot.getValue(NotifiAdminmodels.class);
                    if (notifiAdminmodels.getIduser().equals(getID))
                    {
                        notifi.add(notifiAdminmodels);
                    }
                }
                list.clear();
                list.addAll(notifi);
                tenant_report_notifi_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
