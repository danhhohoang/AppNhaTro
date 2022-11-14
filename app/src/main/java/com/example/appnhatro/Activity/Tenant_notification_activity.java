package com.example.appnhatro.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.Landlord_Notification_Adapter;
import com.example.appnhatro.Adapters.Tenant_Notification_Adapter;
import com.example.appnhatro.Firebase.FirebaseLandlordNotification;
import com.example.appnhatro.Firebase.FirebaseTenantNofication;
import com.example.appnhatro.Models.DatLichModels;
import com.example.appnhatro.Models.Notificationbooking;
import com.example.appnhatro.R;
import com.example.appnhatro.ViewHolderImageHome;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Tenant_notification_activity extends AppCompatActivity {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    //List horizone
    private Tenant_Notification_Adapter tenant_notification_adapter;
    private ArrayList<Notificationbooking> notificationbookings = new ArrayList<>();
    SearchView sv_tpr;
    FirebaseTenantNofication firebaseTenantNofication = new FirebaseTenantNofication();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_notification_list);
        ListPost();
    }

    public void ListPost() {
        RecyclerView recyclerView = findViewById(R.id.listnotifilecation);
        tenant_notification_adapter = new Tenant_Notification_Adapter(this, R.layout.tenant_post_notification_item, notificationbookings);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        firebaseTenantNofication.readPostFindPeople(notificationbookings, tenant_notification_adapter, "");
        recyclerView.setAdapter(tenant_notification_adapter);
        tenant_notification_adapter.setOnItemClickListener(new Tenant_Notification_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                firebaseTenantNofication.readFirebase(position, notificationbookings, Tenant_notification_activity.this);
            }
        });
        sv_tpr = findViewById(R.id.timkiem);
        sv_tpr.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                tenant_notification_adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                tenant_notification_adapter.getFilter().filter(s);
                return false;
            }
        });
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
