package com.example.appnhatro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.AdminHomeAdapter;
import com.example.appnhatro.Firebase.FirebaseAdmin;
import com.example.appnhatro.LoginActivity;
import com.example.appnhatro.Models.HistoryTransaction;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminHistoryTransaction extends AppCompatActivity {
    private AdminHomeAdapter adapter;
    private RecyclerView rcvHistoryTransactionAdmin;
    private ArrayList<HistoryTransaction> historyTransactions = new ArrayList<>();
    private FirebaseAdmin firebaseAdmin = new FirebaseAdmin();
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_history_transaction);
        control();
        event();
    }

    private void event() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void control() {
        back = findViewById(R.id.imgBackHistoryTransactionAdmin);
        rcvHistoryTransactionAdmin = (RecyclerView) findViewById(R.id.rcv_HistoryTransactionAdmin);
        adapter = new AdminHomeAdapter(this, R.layout.admin_item_home_layout, historyTransactions);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvHistoryTransactionAdmin.setLayoutManager(gridLayoutManager);
        rcvHistoryTransactionAdmin.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAdmin.readItemHistoryTransaction(adapter, historyTransactions);
    }
}