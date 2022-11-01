package com.example.appnhatro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeTenantActivity extends AppCompatActivity {
    private ArrayList<Post> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    //List horizone
    private MyRecyclerViewAdapter myRecyclerViewAdapterOthue;
    private ArrayList<Post> listHorizoneOthue= new ArrayList<>();
    //
    private MyRecyclerViewAdapter myRecyclerViewAdapterOGhep;
    private ArrayList<Post> listHorizoneOGhep= new ArrayList<>();
    FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tenant_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardViewImageHome);
        adapter = new ViewHolderImageHome(this, R.layout.list_view_item_home_tenant, persons);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loadNhachoThue();
        loadOGhep();
    }
    @SuppressLint("MissingInflatedId")
    public void loadNhachoThue(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.choThueRecyclerView);
        myRecyclerViewAdapterOthue =  new MyRecyclerViewAdapter(this, R.layout.layout_item_list_horizontal,listHorizoneOthue);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        fireBaseThueTro.docListPost(listHorizoneOthue,myRecyclerViewAdapterOthue);
        myRecyclerViewAdapterOthue.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBaseThueTro.readDataItem(position,listHorizoneOthue,HomeTenantActivity.this);
            }
        });
        recyclerView.setAdapter(myRecyclerViewAdapterOthue);
    }

    @SuppressLint("MissingInflatedId")
    public void loadOGhep(){
        RecyclerView recyclerVieww = (RecyclerView) findViewById(R.id.oGhepRecyclerView);
        myRecyclerViewAdapterOGhep =  new MyRecyclerViewAdapter(this, R.layout.layout_item_list_horizontal,listHorizoneOGhep);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerVieww.setLayoutManager(gridLayoutManager);
        fireBaseThueTro.docListPost(listHorizoneOGhep,myRecyclerViewAdapterOGhep);
        recyclerVieww.setAdapter(myRecyclerViewAdapterOGhep);
        myRecyclerViewAdapterOGhep.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBaseThueTro.readDataItem(position,listHorizoneOGhep,HomeTenantActivity.this);
            }
        });
    }
}