package com.example.appnhatro.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.AdminTenantListAdapter;
import com.example.appnhatro.Firebase.FireBaseTenantList;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;

import java.util.ArrayList;

public class AdminTenantListActivity extends AppCompatActivity {

    ArrayList<user> userArrayList = new ArrayList<>();
    AdminTenantListAdapter adapter;
    FireBaseTenantList fireBaseTenantList = new FireBaseTenantList();

    SearchView searchView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_tenant_list_activity_layout);
        setControl();
        setEvent();

        //Adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        fireBaseTenantList.getAllUser(AdminTenantListActivity.this,userArrayList,adapter);
        adapter = new AdminTenantListAdapter(this, R.layout.item_admin_tenant_list, userArrayList);

        recyclerView.setAdapter(adapter);



    }

    private void setEvent() {

    }

    private void setControl() {
        searchView = findViewById(R.id.search_bar_admin_tenant_list);
        recyclerView = findViewById(R.id.recycler_admin_tenant_list);

    }
}
