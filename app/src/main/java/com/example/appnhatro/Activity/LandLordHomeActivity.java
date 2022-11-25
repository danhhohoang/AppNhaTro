package com.example.appnhatro.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.LandLordHomeListAdapter;
//import com.example.appnhatro.FakeModels.PostFake;
import com.example.appnhatro.Firebase.FireBaseLandLord;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.R;
import com.example.appnhatro.TenantPostDetail;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class LandLordHomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private LandLordHomeListAdapter landLordHomeListAdapter;
    private ArrayList<Post> listAll = new ArrayList<>();
    private FireBaseLandLord fireBaseLandLord = new FireBaseLandLord();
    private String userId;
    private ImageView imgAdd;
    TextView itemMenu1, itemMenu2, itemMenu3, itemMenu4, itemMenu5, itemMenu6, itemMenu7;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;

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
        userId = sharedPreferences.getString("idUser", "KH01");
        //get Post by userId LandLord
        recyclerView.setAdapter(landLordHomeListAdapter);
        searchView = findViewById(R.id.sv_Search_Home_LandLord);
        imgAdd = findViewById(R.id.imgThemLandLord);
        event();
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

        itemMenu1 = findViewById(R.id.tv_lhlDSCT);
        itemMenu2 = findViewById(R.id.tv_lhlDSNTT);
        itemMenu3 = findViewById(R.id.tv_lhlCSVQL);
        itemMenu4 = findViewById(R.id.tv_lhlGDGD);
        itemMenu5 = findViewById(R.id.tv_lhlGYND);
        itemMenu6 = findViewById(R.id.tv_lhlTKTT);
        itemMenu7 = findViewById(R.id.tv_lhlDX);

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
        fireBaseLandLord.readListPostFromUser(landLordHomeListAdapter, listAll, userId);
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
}