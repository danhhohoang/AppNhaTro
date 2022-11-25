package com.example.appnhatro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Activity.Tenant_notification_activity;
import com.example.appnhatro.Adapters.TenantHomeListPostAdapter;
import com.example.appnhatro.Firebase.FireBaseThueTro;
import com.example.appnhatro.Models.Post;

import java.util.ArrayList;

public class HomeTenantActivity extends AppCompatActivity {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    //List horizone
    private TenantHomeListPostAdapter myRecyclerViewAdapterRatingCao;
    private ArrayList<Post> listHorizoneHightRating = new ArrayList<>();
    //
    LinearLayout Post;
    ImageButton notification, DSP;

    String idUser;
    FireBaseThueTro fireBaseThueTro = new FireBaseThueTro();
    ImageButton ivbtnAccount_HT, btn_pdt;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tenant_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardViewImageHome);
        persons.add("ptro4.jpg");
        persons.add("ptro2.jpg");
        persons.add("ptro1.jpg");
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        idUser = sharedPreferences.getString("idUser", "");
        adapter = new ViewHolderImageHome(this, R.layout.list_view_item_home_tenant, persons);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loadNhaRatingCao();
        control();
        setEvent();
        DangBai();
    }

    private void setEvent() {
        ivbtnAccount_HT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeTenantActivity.this, TenantAccountActivity.class);
                intent.putExtra("ID", idUser);
                startActivity(intent);
            }
        });
    }

    private void control() {
        Post = findViewById(R.id.post);
        notification = findViewById(R.id.notidication);
        ivbtnAccount_HT = findViewById(R.id.ivbtnAccount_HT);
        btn_pdt = findViewById(R.id.btn_PDT);
        DSP = findViewById(R.id.btn_DSP);
    }

    @SuppressLint("MissingInflatedId")
    public void loadNhaRatingCao() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.choThueRecyclerView);
        myRecyclerViewAdapterRatingCao = new TenantHomeListPostAdapter(this, R.layout.tenant_item_home_layout, listHorizoneHightRating);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(myRecyclerViewAdapterRatingCao);
        myRecyclerViewAdapterRatingCao.setOnItemClickListener(new TenantHomeListPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                fireBaseThueTro.readDataItem(position, listHorizoneHightRating, HomeTenantActivity.this);
            }
        });
    }

    public void DangBai() {
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

    @Override
    protected void onResume() {
        super.onResume();
        fireBaseThueTro.getPostRatingCao(listHorizoneHightRating, myRecyclerViewAdapterRatingCao);
    }
}