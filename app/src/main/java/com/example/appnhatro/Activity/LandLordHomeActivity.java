package com.example.appnhatro.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.LandLordHomeListAdapter;
import com.example.appnhatro.Firebase.FireBaseLandLord;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.R;

import java.util.ArrayList;

public class LandLordHomeActivity extends AppCompatActivity {
    RecyclerView rcvList;
    LandLordHomeListAdapter landLordHomeListAdapter;
    ArrayList<Post> list = new ArrayList<>();
    FireBaseLandLord fireBaseLandLord = new FireBaseLandLord();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_home_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvLLListPostHome);
        landLordHomeListAdapter = new LandLordHomeListAdapter(this, R.layout.lanlord_home_item_layout,list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        fireBaseLandLord.readListPostFromUser(landLordHomeListAdapter, list, "KH01");
        recyclerView.setAdapter(landLordHomeListAdapter);

    }
}