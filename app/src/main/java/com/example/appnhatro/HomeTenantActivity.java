package com.example.appnhatro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Activity.Tenant_notification_activity;
import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.Post;

import java.util.ArrayList;

public class HomeTenantActivity extends AppCompatActivity {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    //List horizone
    private MyRecyclerViewAdapter myRecyclerViewAdapterOthue;
    private ArrayList<Post> listHorizoneOthue= new ArrayList<>();
    //
    private MyRecyclerViewAdapter myRecyclerViewAdapterOGhep;
    private ArrayList<Post> listHorizoneOGhep= new ArrayList<>();
    LinearLayout Post;
    ImageButton notification, DSP;
    FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    ImageButton ivbtnAccount_HT, btn_pdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tenant_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardViewImageHome);
        persons.add("ptro4.jpg");
        persons.add("ptro2.jpg");
        persons.add("ptro1.jpg");
        adapter = new ViewHolderImageHome(this, R.layout.list_view_item_home_tenant, persons);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loadNhachoThue();
        loadOGhep();
        control();
        setEvent();
        DangBai();
    }
    private void setEvent(){
        ivbtnAccount_HT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeTenantActivity.this,TenantAccountActivity.class);
                intent.putExtra("ID","KH01");
                startActivity(intent);
            }
        });
    }
    private void control() {
        Post = findViewById(R.id.post);
        notification = findViewById(R.id.notidication);
        ivbtnAccount_HT = findViewById(R.id.ivbtnAccount_HT);
        btn_pdt = findViewById(R.id.btn_PDT);
        DSP =findViewById(R.id.btn_DSP);
    }
    @SuppressLint("MissingInflatedId")
    public void loadNhachoThue(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.choThueRecyclerView);
        myRecyclerViewAdapterOthue =  new MyRecyclerViewAdapter(this, R.layout.layout_item_list_horizontal,listHorizoneOthue);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        fireBaseThueTro.docListPost(listHorizoneOthue,myRecyclerViewAdapterOthue);
        recyclerView.setAdapter(myRecyclerViewAdapterOthue);
        myRecyclerViewAdapterOthue.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBaseThueTro.readDataItem(position,listHorizoneOthue,HomeTenantActivity.this);
            }
        });
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
    public void DangBai(){
        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeTenantActivity.this, TenantPostFavourite.class);
                startActivity(intent);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeTenantActivity.this, Tenant_notification_activity.class);
                startActivity(intent);
            }
        });
        btn_pdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeTenantActivity.this, TenantPostRenting.class);
                startActivity(intent);
            }
        });
        DSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeTenantActivity.this, TenantSearchActivity.class);
                startActivity(intent);
            }
        });
    }
}