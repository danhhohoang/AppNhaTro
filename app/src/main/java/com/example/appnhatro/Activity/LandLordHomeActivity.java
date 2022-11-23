package com.example.appnhatro.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.LandLordHomeListAdapter;
//import com.example.appnhatro.FakeModels.PostFake;
import com.example.appnhatro.Firebase.FireBaseLandLord;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.R;
import com.example.appnhatro.TenantPostDetail;

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
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_home_layout);
        recyclerView = (RecyclerView) findViewById(R.id.rvLLListPostHome);
        landLordHomeListAdapter = new LandLordHomeListAdapter(this, R.layout.lanlord_home_item_layout,listAll);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        userId = sharedPreferences.getString("idUser", "");
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

    public void event(){
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
                Intent intent = new Intent(LandLordHomeActivity.this,LandLordAddNewPost.class);
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

        for (Post item : listAll)
        {
            if (item.getHouse_name().toLowerCase().contains(text.toLowerCase())) {
                fillterList.add(item);
            }
        }
        if(fillterList.isEmpty()){
        }else {
            landLordHomeListAdapter = new LandLordHomeListAdapter(this, R.layout.lanlord_home_item_layout,fillterList);
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
}