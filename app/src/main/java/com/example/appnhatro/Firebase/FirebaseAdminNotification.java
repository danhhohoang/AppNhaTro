package com.example.appnhatro.Firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.appnhatro.Activity.Admin_notification_Activity;
import com.example.appnhatro.Admin_notification_adapter;
import com.example.appnhatro.Models.ReportModels;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseAdminNotification {
    public FirebaseAdminNotification(){

    }
    public void readPostFindPeople(ArrayList<ReportModels> list, Admin_notification_adapter admin_notification_adapter, String getID,Context context) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("Report");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ReportModels> data = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ReportModels reportModels = dataSnapshot.getValue(ReportModels.class);
                    if (reportModels.getID_NguoiDang().equals("KH01") && reportModels.getStatus().equals("1")) {
                        data.add(reportModels);
                    }
                }
                list.clear();
                list.addAll(data);
                admin_notification_adapter.notifyDataSetChanged();
                DatabaseReference databaseReferences = firebaseDatabase.getReference();
                databaseReferences.child("Notify").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> dsNotification = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            dsNotification.add(dataSnapshot.getKey());
                        }
                        String[] temp = dsNotification.get(dsNotification.size() - 1).split("N");
                        String id = "";
                        if (Integer.parseInt(temp[1]) < 9) {
                            id = "N0" + (Integer.parseInt(temp[1]) + 1);
                        } else {
                            id = "N" + (Integer.parseInt(temp[1]) + 1);
                        }
                        ((Admin_notification_Activity) context).IdNotification(id);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readFirebases(int position, ArrayList<ReportModels> list, Context context) {
        ReportModels data = list.get(position);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("booking").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void IdNotificationADM(Context context) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Notify").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> dsNotification = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dsNotification.add(dataSnapshot.getKey());
                }
                String[] temp = dsNotification.get(dsNotification.size() - 1).split("N");
                String id = "";
                Log.d("da", "onDataChange: " + temp[1]);
                if (Integer.parseInt(temp[1]) < 10) {
                    id = "N0" + (Integer.parseInt(temp[1]) + 1);
                } else {
                    id = "N0" + (Integer.parseInt(temp[1]) + 1);
                }
                ((Admin_notification_Activity) context).IdNotification(id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
