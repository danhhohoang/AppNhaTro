package com.example.appnhatro.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnhatro.Adapters.AdminHomeAdapter;
import com.example.appnhatro.Firebase.FirebaseAdmin;
import com.example.appnhatro.Models.HistoryTransaction;
import com.example.appnhatro.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {
    private AdminHomeAdapter adapter;
    private RecyclerView rcvHomeAdmin;
    private ImageView menuAdmin;
    private ArrayList<HistoryTransaction> historyTransactions = new ArrayList<>();
    private FirebaseAdmin firebaseAdmin = new FirebaseAdmin();
    TextView itemMenu1, itemMenu2, itemMenu3, itemMenu4, itemMenu5, itemMenu6, itemMenu7;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        control();
        event();
        actionToolBar();
    }

    private void event() {

    }

    private void control() {
        itemMenu1 = findViewById(R.id.tv_ahDSCT);
        itemMenu2 = findViewById(R.id.tv_ahDSNTT);
        itemMenu3 = findViewById(R.id.tv_ahCSVQL);
        itemMenu4 = findViewById(R.id.tv_ahGDGD);
        itemMenu5 = findViewById(R.id.tv_ahGYND);
        itemMenu6 = findViewById(R.id.tv_ahTKTT);
        itemMenu7 = findViewById(R.id.tv_ahDX);
        toolbar = findViewById(R.id.ah_toolbar);
        drawerLayout = findViewById(R.id.ah_drawerLayout);
        navigationView = findViewById(R.id.ah_navigationView);
        rcvHomeAdmin = (RecyclerView) findViewById(R.id.rcvHomeAdmin);
        adapter = new AdminHomeAdapter(this, R.layout.admin_item_home_layout,historyTransactions);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvHomeAdmin.setLayoutManager(gridLayoutManager);
        rcvHomeAdmin.setAdapter(adapter);

        itemMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        itemMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        itemMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        itemMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        itemMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        itemMenu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        itemMenu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAdmin.readItemInHome(adapter,historyTransactions);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu);
        toolbar.setTitle("Trang chủ admin");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}