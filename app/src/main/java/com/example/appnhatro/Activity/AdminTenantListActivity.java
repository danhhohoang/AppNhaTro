package com.example.appnhatro.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.TenantListAdapter;
import com.example.appnhatro.Adapters.TenantSearchAdapter;
import com.example.appnhatro.Firebase.FireBaseTenantList;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;

import java.util.ArrayList;
import java.util.Locale;

public class AdminTenantListActivity extends AppCompatActivity {

    ArrayList<user> userArrayList = new ArrayList<>();
    ArrayList<user> appearanceList = new ArrayList<>();
    TenantListAdapter adapter;
    FireBaseTenantList fireBaseTenantList = new FireBaseTenantList();
    SearchView searchView;
    RecyclerView recyclerView;
    CardView cardView;
    ImageView btnBack;
    String TAG = "minh";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_tenant_list_activity_layout);
        setControl();

        adapter = new TenantListAdapter(this, R.layout.item_admin_tenant_list, userArrayList);
        fireBaseTenantList.getAllUserByRole(this, userArrayList, adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        setEvent();
    }

    public void setData() {
        adapter = new TenantListAdapter(this, R.layout.item_admin_tenant_list, userArrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appearanceList.addAll(userArrayList);
        adapter = new TenantListAdapter(this, R.layout.item_admin_tenant_list, appearanceList);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void returnUserByFilter() {
        String query = searchView.getQuery().toString().toLowerCase();
        appearanceList.clear();

        for (user itemUser : userArrayList) {
            if (itemUser.getName().toLowerCase().contains(query) ||
                    itemUser.getPhone().contains(query) || itemUser.getId().equalsIgnoreCase(query)) {
                appearanceList.add(itemUser);
            }
        }

        adapter = new TenantListAdapter(this, R.layout.item_admin_tenant_list, appearanceList);
        recyclerView.setAdapter(adapter);
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
                returnUserByFilter();
                return true;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setControl() {
        searchView = findViewById(R.id.search_bar_admin_tenant_list);
        recyclerView = findViewById(R.id.recycler_admin_tenant_list);
        cardView = findViewById(R.id.cardView_item_admin_tenant_list);
        btnBack = findViewById(R.id.image_admin_tenant_list_back);
    }
}
