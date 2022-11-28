package com.example.appnhatro.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.Landlord_Notification_Adapter;
import com.example.appnhatro.Admin_notification_adapter;
import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Firebase.FirebaseAdminNotification;
import com.example.appnhatro.Interface.Admininterface;
import com.example.appnhatro.Models.NotifiAdminmodels;
import com.example.appnhatro.Models.ReportModels;
import com.example.appnhatro.R;
import com.example.appnhatro.ViewHolderImageHome;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Admin_notification_Activity extends AppCompatActivity implements Admininterface {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    String getID, IDat;
    SharedPreferences sharedPreferences;
    private Admin_notification_adapter admin_notification_adapter;
    private ArrayList<ReportModels> reportModels = new ArrayList<>();
    SearchView sv_tpr;
    FireBaseThueTro fireBaseThueTro;
    FirebaseAdminNotification firebaseAdminNotification = new FirebaseAdminNotification();
    DatabaseReference databaseReference;
    Button DongY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_notification_list);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        getID = sharedPreferences.getString("idUser", "");
        ListPost();

    }

    public void ListPost() {
        RecyclerView recyclerView = findViewById(R.id.rcv_adTB);
        admin_notification_adapter = new Admin_notification_adapter(this, R.layout.admin_notification_item, reportModels, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(admin_notification_adapter);
        admin_notification_adapter.setOnItemClickListener(new Landlord_Notification_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                firebaseAdminNotification.readFirebases(position, reportModels, Admin_notification_Activity.this);
            }
        });
        sv_tpr = findViewById(R.id.sv_admintimkiem);
        DongY = findViewById(R.id.btn_danhan);
        sv_tpr.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                admin_notification_adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                admin_notification_adapter.getFilter().filter(s);
                return false;
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onclick(int potision) {
        NotifiAdminmodels notifiAdminmodels = new NotifiAdminmodels(getID, reportModels.get(potision).getTenNguoiGui(), reportModels.get(potision).getTitle(), IDat, "Đã Tiếp Nhận Phiếu Tố Cáo", reportModels.get(potision).getPost_Name());
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Notify");
        databaseReference.child(IDat).setValue(notifiAdminmodels);
        ReportModels repost = reportModels.get(potision);
        repost.setStatus("2");
        DatabaseReference databaseReferenceRepost = FirebaseDatabase.getInstance().getReference("Report");
        databaseReferenceRepost.child(repost.getId()).setValue(repost);
    }
    public void heid(View view){
        DongY.setVisibility(view.INVISIBLE);
    }
    @Override
    protected void onResume(){
        super.onResume();
        firebaseAdminNotification.readPostFindPeople(reportModels, admin_notification_adapter, getID,Admin_notification_Activity.this);
    }
    public void IdNotification (String id){
        IDat = id;
    }
}
