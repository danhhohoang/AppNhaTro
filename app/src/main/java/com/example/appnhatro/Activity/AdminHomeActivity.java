package com.example.appnhatro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.appnhatro.Adapters.AdminHomeAdapter;
import com.example.appnhatro.AdminLandlordListActivity;
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

public class AdminHomeActivity extends AppCompatActivity {
    private AdminHomeAdapter adapter;
    private RecyclerView rcvHomeAdmin;
    private ArrayList<HistoryTransaction> historyTransactions = new ArrayList<>();
    private FirebaseAdmin firebaseAdmin = new FirebaseAdmin();
    TextView itemMenu1, itemMenu2, itemMenu3, itemMenu4, itemMenu5, itemMenu6, itemMenu7,itemMenuAccount;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    int fee1, fee2, fee3, fee4, fee5, fee6, fee7, fee8, fee9, fee10, fee11, fee12 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        control();
        event();
        actionToolBar();
        data();
    }

    private void event() {
        itemMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminLandlordListActivity.class);
                startActivity(intent);
            }
        });

        itemMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminTenantListActivity.class);
                startActivity(intent);
            }
        });

        itemMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this,TermAndSerciveActivity.class);
                startActivity(intent);
            }
        });

        itemMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this,AdminHistoryTransaction.class);
                startActivity(intent);
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
                Intent intent = new Intent(AdminHomeActivity.this, StatisticalAdminActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("fee1",fee1);
                bundle.putInt("fee2",fee2);
                bundle.putInt("fee3",fee3);
                bundle.putInt("fee4",fee4);
                bundle.putInt("fee5",fee5);
                bundle.putInt("fee6",fee6);
                bundle.putInt("fee7",fee7);
                bundle.putInt("fee8",fee8);
                bundle.putInt("fee9",fee9);
                bundle.putInt("fee10",fee10);
                bundle.putInt("fee11",fee11);
                bundle.putInt("fee12",fee12);
                intent.putExtra("month",bundle);
                startActivity(intent);
            }
        });

        itemMenu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        itemMenuAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                String idUser = sharedPreferences.getString("idUser", "");
                Intent intent = new Intent(AdminHomeActivity.this,AdminAccountActivity.class);
                intent.putExtra("ID",idUser);
                startActivity(intent);
            }
        });
    }

    private void control() {
        itemMenu1 = findViewById(R.id.tv_ahDSCT);
        itemMenu2 = findViewById(R.id.tv_ahDSNTT);
        itemMenu3 = findViewById(R.id.tv_ahCSVQL);
        itemMenu4 = findViewById(R.id.tv_ahGDGD);
        itemMenu5 = findViewById(R.id.tv_ahGYND);
        itemMenu6 = findViewById(R.id.tv_ahTKTT);
        itemMenu7 = findViewById(R.id.tv_ahDX);
        itemMenuAccount = findViewById(R.id.tv_ahAccount);
        toolbar = findViewById(R.id.ah_toolbar);
        drawerLayout = findViewById(R.id.ah_drawerLayout);
        navigationView = findViewById(R.id.ah_navigationView);
        rcvHomeAdmin = (RecyclerView) findViewById(R.id.rcvHomeAdmin);
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

    private void data(){
        DatabaseReference databaseReference1;
        databaseReference1 = FirebaseDatabase.getInstance().getReference("HistoryTransaction");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TransactionModel transactionModel = dataSnapshot.getValue(TransactionModel.class);
                    if (transactionModel.getStatus().equals("2")){
                        String[] parse = transactionModel.getDate().split("-");
                        String month;
                        month = parse[1];
                        if (month.equals("1")) {
                            int price = Integer.valueOf(transactionModel.getPrice());
                            int m1 = 0;
                            m1 += price *5/100;
                            fee1 += m1;
                        }
                        if (month.equals("2")) {
                            int price = Integer.valueOf(transactionModel.getPrice());
                            int m2 = 0;
                            m2 += price*5/100;
                            fee2 += m2;
                        }
                        if (month.equals("3")) {
                            int price = Integer.valueOf(transactionModel.getPrice());
                            int m3 = 0;
                            m3 += price*5/100;
                            fee3 += m3;
                        }
                        if (month.equals("4")) {
                            int price = Integer.valueOf(transactionModel.getPrice());
                            int m4 = 0;
                            m4 += price*5/100;
                            fee4 += m4;
                        }
                        if (month.equals("5")) {
                            int price = Integer.valueOf(transactionModel.getPrice());
                            int m5 = 0;
                            m5 += price*5/100;
                            fee5 += m5;
                        }
                        if (month.equals("6")) {
                            int price = Integer.valueOf(transactionModel.getPrice());
                            int m6 = 0;
                            m6 += price*5/100;
                            fee6 += m6;
                        }
                        if (month.equals("7")) {
                            int price = Integer.valueOf(transactionModel.getPrice());
                            int m7 = 0;
                            m7 += price*5/100;
                            fee7 += m7;
                        }
                        if (month.equals("8")) {
                            int price = Integer.valueOf(transactionModel.getPrice());
                            int m8 = 0;
                            m8 += price*5/100;
                            fee8 += m8;
                        }
                        if (month.equals("9")) {
                            int price = Integer.valueOf(transactionModel.getPrice());
                            int m9 = 0;
                            m9 += price*5/100;
                            fee9 += m9;
                        }
                        if (month.equals("10")) {
                            int price = Integer.valueOf(transactionModel.getPrice());
                            int m10 = 0;
                            m10 += price*5/100;
                            fee10 += m10;
                        }
                        if (month.equals("11")) {
                            int price = Integer.valueOf(transactionModel.getPrice());
                            int m11 = 0;
                            m11 += price*5/100;
                            fee11 += m11;
                        }
                        if (month.equals("12")) {
                            int price = Integer.valueOf(transactionModel.getPrice());
                            int m12 = 0;
                            m12 += price*5/100;
                            fee12 += m12;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}