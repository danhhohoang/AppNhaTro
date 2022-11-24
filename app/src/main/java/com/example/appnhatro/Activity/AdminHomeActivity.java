package com.example.appnhatro.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.appnhatro.Adapters.AdminHomeAdapter;
import com.example.appnhatro.Firebase.FirebaseAdmin;
import com.example.appnhatro.Models.HistoryTransaction;
import com.example.appnhatro.R;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {
    private AdminHomeAdapter adapter;
    private RecyclerView rcvHomeAdmin;
    private ImageView menuAdmin;
    private ArrayList<HistoryTransaction> historyTransactions = new ArrayList<>();
    private FirebaseAdmin firebaseAdmin = new FirebaseAdmin();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        control();
        event();
    }

    private void event() {
        menuAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void control() {
        rcvHomeAdmin = (RecyclerView) findViewById(R.id.rcvHomeAdmin);
        menuAdmin = findViewById(R.id.imgMenuAdmin);
        adapter = new AdminHomeAdapter(this, R.layout.admin_item_home_layout,historyTransactions);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvHomeAdmin.setLayoutManager(gridLayoutManager);
        rcvHomeAdmin.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAdmin.readItemInHome(adapter,historyTransactions);
    }
}