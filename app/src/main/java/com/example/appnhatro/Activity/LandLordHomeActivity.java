package com.example.appnhatro.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.LandLordHomeListAdapter;
import com.example.appnhatro.Firebase.FireBaseLandLord;
import com.example.appnhatro.LandlordAccountActivity;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LandLordHomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private LandLordHomeListAdapter landLordHomeListAdapter;
    private ArrayList<Post> listAll = new ArrayList<>();
    private FireBaseLandLord fireBaseLandLord = new FireBaseLandLord();
    private String getID;
    private ImageView imgAdd;
    TextView itemMenu1, itemMenu2, itemMenu3, itemMenu4, itemMenu5, itemMenu6, itemMenu7;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    int fee1, fee2, fee3, fee4, fee5, fee6, fee7, fee8, fee9, fee10, fee11, fee12 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_home_layout);
        setControl();
        actionToolBar();
        recyclerView = (RecyclerView) findViewById(R.id.rvLLListPostHome);
        landLordHomeListAdapter = new LandLordHomeListAdapter(this, R.layout.lanlord_home_item_layout, listAll);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        getID = sharedPreferences.getString("idUser", "");
        //get Post by userId LandLord
        recyclerView.setAdapter(landLordHomeListAdapter);
        searchView = findViewById(R.id.sv_Search_Home_LandLord);
        imgAdd = findViewById(R.id.imgThemLandLord);
        event();
        data();
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fillterList(newText);
                return true;
            }
        });
    }

    private void setControl() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);


        itemMenu2 = findViewById(R.id.tv_lhlDSNTT);
        itemMenu3 = findViewById(R.id.tv_lhlCSVQL);
        itemMenu4 = findViewById(R.id.tv_lhlGDGD);
        itemMenu5 = findViewById(R.id.tv_lhlGYND);
        itemMenu6 = findViewById(R.id.tv_lhlTKTT);
        itemMenu7 = findViewById(R.id.tv_lhlDX);

        itemMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandLordHomeActivity.this, Landlord_Notification_Activity.class);
                startActivity(intent);
            }
        });
        itemMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandLordHomeActivity.this, TenantViewTermAndPolicyActivity.class);
                startActivity(intent);
            }
        });
        itemMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(LandLordHomeActivity.this, UpdateStatusLandlordActivity.class);
                startActivity(intent);
            }
        });
        itemMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                Intent intent = new Intent(LandLordHomeActivity.this, LandlordAccountActivity.class);
                startActivity(intent);
            }
        });
        itemMenu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandLordHomeActivity.this, StatisticalLandLordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("fee1", fee1);
                bundle.putInt("fee2", fee2);
                bundle.putInt("fee3", fee3);
                bundle.putInt("fee4", fee4);
                bundle.putInt("fee5", fee5);
                bundle.putInt("fee6", fee6);
                bundle.putInt("fee7", fee7);
                bundle.putInt("fee8", fee8);
                bundle.putInt("fee9", fee9);
                bundle.putInt("fee10", fee10);
                bundle.putInt("fee11", fee11);
                bundle.putInt("fee12", fee12);
                intent.putExtra("month", bundle);
                startActivity(intent);
            }
        });
        itemMenu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void event() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fillterList(newText);
                return true;
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandLordHomeActivity.this, LandLordAddNewPost.class);
                startActivity(intent);
            }
        });
        landLordHomeListAdapter.setOnItemClickListener(new LandLordHomeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                Post post = listAll.get(position);
                Intent intent = new Intent(LandLordHomeActivity.this, LandLordPostDetailActivity.class);
                intent.putExtra("it_id", post.getId());
                startActivity(intent);
            }
        });
    }

    private void fillterList(String text) {
        ArrayList<Post> fillterList = new ArrayList<>();

        for (Post item : listAll) {
            if (item.getHouse_name().toLowerCase().contains(text.toLowerCase())) {
                fillterList.add(item);
            }
        }
        if (fillterList.isEmpty()) {
        } else {
            landLordHomeListAdapter = new LandLordHomeListAdapter(this, R.layout.lanlord_home_item_layout, fillterList);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(landLordHomeListAdapter);
            searchView = findViewById(R.id.sv_Search_Home_LandLord);
            landLordHomeListAdapter.notifyDataSetChanged();
        }
    }

    protected void onResume() {
        super.onResume();
        fireBaseLandLord.readListPostFromUser(landLordHomeListAdapter, listAll, getID);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu);
        toolbar.setTitle("Danh sách bài đăng chủ trọ");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void data() {
        DatabaseReference databaseReference1;
        DatabaseReference databaseReference2;
        databaseReference1 = FirebaseDatabase.getInstance().getReference("HistoryTransaction");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Post");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TransactionModel transactionModel = dataSnapshot.getValue(TransactionModel.class);
                    if (transactionModel.getId_user().equals(getID)) {
                        String[] parse = transactionModel.getDate().split(" ");
                        String month;
                        month = parse[0];
                        if (month.equals("1")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m1 = 0;
                            m1 += price;
                            fee1 += m1;
                        }
                        if (month.equals("2")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m2 = 0;
                            m2 += price;
                            fee2 += m2;
                        }
                        if (month.equals("3")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m3 = 0;
                            m3 += price;
                            fee3 += m3;
                        }
                        if (month.equals("4")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m4 = 0;
                            m4 += price;
                            fee4 += m4;
                        }
                        if (month.equals("5")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m5 = 0;
                            m5 += price;
                            fee5 += m5;
                        }
                        if (month.equals("6")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m6 = 0;
                            m6 += price;
                            fee6 += m6;
                        }
                        if (month.equals("7")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m7 = 0;
                            m7 += price;
                            fee7 += m7;
                        }
                        if (month.equals("8")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m8 = 0;
                            m8 += price;
                            fee8 += m8;
                        }
                        if (month.equals("9")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m9 = 0;
                            m9 += price;
                            fee9 += m9;
                        }
                        if (month.equals("10")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m10 = 0;
                            m10 += price;
                            fee10 += m10;
                        }
                        if (month.equals("11")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m11 = 0;
                            m11 += price;
                            fee11 += m11;
                        }
                        if (month.equals("12")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m12 = 0;
                            m12 += price;
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
