package com.example.appnhatro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdminLandlordList extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView al;
    AdminLandlordListAdapter adminLandlordListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_landlord_list);

        al = findViewById(R.id.rcv_al);
        adminLandlordListAdapter = new AdminLandlordListAdapter(this,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        al.setLayoutManager(linearLayoutManager);

        adminLandlordListAdapter.setData(getAdminLandLordList());
        al.setAdapter(adminLandlordListAdapter);
    }
    private List<LandLordList> getAdminLandLordList(){
        List<LandLordList> adminLandlordList = new ArrayList<>();
        adminLandlordList.add(new LandLordList(1,1,1700000,"Shutdown","huynhbaonguyen","@gmail.com","4353545345","tran duy hung, hanoi"));
        adminLandlordList.add(new LandLordList(1,1,1700000,"Shutdown","huynhbaonguyen","@gmail.com","4353545345","tran duy hung, hanoi"));
        return adminLandlordList;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this,AdminLandlordDetails.class);
        intent.putExtra("it_ald_id",getAdminLandLordList().get(position).getID());
        intent.putExtra("it_ald_tenchutro",getAdminLandLordList().get(position).getTenchutro());
        intent.putExtra("it_ald_tentaikhoan",getAdminLandLordList().get(position).getTentaikhoan());
        intent.putExtra("it_ald_CMND",getAdminLandLordList().get(position).getCmnd());
        intent.putExtra("it_ald_diachi",getAdminLandLordList().get(position).getDiachi());
        startActivity(intent);
    }
}
