package com.example.appnhatro;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdminLandlordList extends AppCompatActivity {
    RecyclerView al;
    AdminLandlordListAdapter adminLandlordListAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_landlord_list);

        al = findViewById(R.id.rcv_al);
        adminLandlordListAdapter = new AdminLandlordListAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        al.setLayoutManager(linearLayoutManager);

        adminLandlordListAdapter.setData(getAdminLandLordList());
        al.setAdapter(adminLandlordListAdapter);
    }
    private List<LandLordList> getAdminLandLordList(){
        List<LandLordList> adminLandlordList = new ArrayList<>();
        adminLandlordList.add(new LandLordList(1,1,1700000,"Shutdown"));
        adminLandlordList.add(new LandLordList(1,1,1700000,"Shutdown"));
        return adminLandlordList;
    }
}
