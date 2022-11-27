package com.example.appnhatro.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.TenantListAdapter;
import com.example.appnhatro.Firebase.FireBaseTenantList;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;

import java.util.ArrayList;

public class AdminTenantListActivity extends AppCompatActivity {

    ArrayList<user> userArrayList = new ArrayList<>();
    TenantListAdapter adapter;
    FireBaseTenantList fireBaseTenantList = new FireBaseTenantList();
    SearchView searchView;
    RecyclerView recyclerView;
    CardView cardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_tenant_list_activity_layout);
        setControl();
        adapter = new TenantListAdapter(this, R.layout.item_admin_tenant_list, userArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userArrayList.clear();
        fireBaseTenantList.getAllUser(AdminTenantListActivity.this, userArrayList, adapter);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setEvent() {
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    userArrayList.clear();
                    fireBaseTenantList.getAllUser(AdminTenantListActivity.this, userArrayList, adapter);
                } else {
                    userArrayList.clear();
                    fireBaseTenantList.reTurnUserByFilter(newText, userArrayList, adapter);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }

    private void setControl() {
        searchView = findViewById(R.id.search_bar_admin_tenant_list);
        recyclerView = findViewById(R.id.recycler_admin_tenant_list);
        cardView = findViewById(R.id.cardView_item_admin_tenant_list);
    }
}
