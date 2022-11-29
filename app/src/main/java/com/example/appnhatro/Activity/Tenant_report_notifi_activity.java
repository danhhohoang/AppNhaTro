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

import com.example.appnhatro.Adapters.Tenant_report_notifi_adapter;
import com.example.appnhatro.Firebase.Firebase_report_notifi;
import com.example.appnhatro.Interface.Admininterface;
import com.example.appnhatro.Models.NotifiAdminmodels;
import com.example.appnhatro.R;
import com.example.appnhatro.ViewHolderImageHome;

import java.util.ArrayList;

public class Tenant_report_notifi_activity extends AppCompatActivity implements Admininterface {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    String getID;
    ImageView back;
    SharedPreferences sharedPreferences;
    private Tenant_report_notifi_adapter tenant_report_notifi_adapter;
    private ArrayList<NotifiAdminmodels> notifiAdminmodels = new ArrayList<>();
    SearchView sv_tpr;
    Firebase_report_notifi firebase_report_notifi = new Firebase_report_notifi();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_report_list);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        getID = sharedPreferences.getString("idUser", "");
        back = findViewById(R.id.btn_notifibackrp);
        ListPost();
    }

    public void ListPost() {
        RecyclerView recyclerView = findViewById(R.id.listnotifilecation);
        tenant_report_notifi_adapter = new Tenant_report_notifi_adapter(this, R.layout.tenant_report_item, notifiAdminmodels, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(tenant_report_notifi_adapter);
        firebase_report_notifi.readPostFindPeople(notifiAdminmodels, tenant_report_notifi_adapter, getID);
        sv_tpr = findViewById(R.id.timkiem);
        sv_tpr.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                tenant_report_notifi_adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                tenant_report_notifi_adapter.getFilter().filter(s);
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

    @Override
    public void onclick(int potision) {

    }
}
