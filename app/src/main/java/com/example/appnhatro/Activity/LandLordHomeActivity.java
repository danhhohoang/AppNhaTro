package com.example.appnhatro.Activity;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.LandLordHomeListAdapter;
import com.example.appnhatro.FakeModels.PostFake;
import com.example.appnhatro.Firebase.FireBaseLandLord;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.R;

import java.util.ArrayList;
import java.util.List;

public class LandLordHomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SearchView searchView;
    LandLordHomeListAdapter landLordHomeListAdapter;
    ArrayList<Post> listAll = new ArrayList<>();
    FireBaseLandLord fireBaseLandLord = new FireBaseLandLord();
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_home_layout);
        recyclerView = (RecyclerView) findViewById(R.id.rvLLListPostHome);
        landLordHomeListAdapter = new LandLordHomeListAdapter(this, R.layout.lanlord_home_item_layout,listAll);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        userId = "KH01";

        //get Post by userId LandLord
        fireBaseLandLord.readListPostFromUser(landLordHomeListAdapter, listAll, userId);
        recyclerView.setAdapter(landLordHomeListAdapter);
        searchView = findViewById(R.id.sv_Search_Home_LandLord);
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
            Toast.makeText(this, "Không tìm thấy kết quả", Toast.LENGTH_LONG).show();
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
}