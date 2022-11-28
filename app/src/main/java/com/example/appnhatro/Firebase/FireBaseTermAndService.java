package com.example.appnhatro.Firebase;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.appnhatro.Activity.TenantViewTermAndPolicyActivity;
import com.example.appnhatro.Activity.TermAndSerciveActivity;
import com.example.appnhatro.Models.TermAndPolicy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FireBaseTermAndService {

    TermAndPolicy finalTermAndPolicy;

    public FireBaseTermAndService() {
    }

    //Doc Term And Service tu Firebase
    public void getTermAndServiceFormDB(Activity activity,ArrayList<TermAndPolicy> termAndPolicyList) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("TermAndPolicy").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TermAndPolicy finalTermAndPolicy = snapshot.getValue(TermAndPolicy.class);
                termAndPolicyList.add(finalTermAndPolicy);
                Log.d("WATCH HERE", "onDataChange: " + termAndPolicyList.get(0).getContent());
                ((TermAndSerciveActivity) activity).doSomeThing();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getTermAndServiceForTenant(Activity activity,ArrayList<TermAndPolicy> termAndPolicyList) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("TermAndPolicy").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TermAndPolicy finalTermAndPolicy = snapshot.getValue(TermAndPolicy.class);
                termAndPolicyList.add(finalTermAndPolicy);
                Log.d("WATCH HERE", "onDataChange: " + termAndPolicyList.get(0).getContent());
                ((TenantViewTermAndPolicyActivity) activity).doSomeThing();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setTermAndServiceToDB(TermAndPolicy finalTermAndPolicy){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("TermAndPolicy");
        databaseReference.child("content").setValue(finalTermAndPolicy.getContent());
    }


}
