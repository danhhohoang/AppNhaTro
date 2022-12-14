package com.example.appnhatro.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.Tenant_Notification_Adapter;
import com.example.appnhatro.Firebase.FirebaseTenantNofication;
import com.example.appnhatro.Models.Notificationbooking;
import com.example.appnhatro.R;
import com.example.appnhatro.ViewHolderImageHome;

import java.util.ArrayList;

public class Tenant_notification_activity extends AppCompatActivity {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    //List horizone
    private Tenant_Notification_Adapter tenant_notification_adapter;
    private ArrayList<Notificationbooking> notificationbookings = new ArrayList<>();
    SearchView sv_tpr;
    String iduser;
    ImageView back;
    SharedPreferences sharedPreferences;
    FirebaseTenantNofication firebaseTenantNofication = new FirebaseTenantNofication();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_notification_list);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        iduser = sharedPreferences.getString("idUser", "");
        back = findViewById(R.id.btn_notifiback);
        ListPost();
    }

    public void ListPost() {

        RecyclerView recyclerView = findViewById(R.id.listnotifilecation);
        tenant_notification_adapter = new Tenant_Notification_Adapter(this, R.layout.tenant_post_notification_item, notificationbookings);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        firebaseTenantNofication.readPostFindPeople(notificationbookings, tenant_notification_adapter, iduser);
        recyclerView.setAdapter(tenant_notification_adapter);
        tenant_notification_adapter.setOnItemClickListener(new Tenant_Notification_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
