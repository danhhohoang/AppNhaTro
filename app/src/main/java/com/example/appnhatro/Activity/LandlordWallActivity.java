package com.example.appnhatro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.TenantSearchAdapter;
import com.example.appnhatro.Firebase.FireBaseTenantSearch;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.user;
import com.example.appnhatro.R;
import com.example.appnhatro.TenantPostDetail;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class LandlordWallActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView txtName,
            txtPhone;
    ArrayList<Post> dataList = new ArrayList<>();
    ArrayList<user> landlordInfo = new ArrayList<>();
    //gọi lại phần chức năng của tenant search này vì nó giống nhau, không cần phải tạo class mới
    FireBaseTenantSearch fireBaseTenantSearch = new FireBaseTenantSearch();
    TenantSearchAdapter tenantSearchAdapter;
    String mID;
    ShapeableImageView shapeableImageView;


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.landlord_wall_layout);
        setControl();



        //Get intent
        Intent intent = getIntent();
        mID ="KH01";
        //mID = intent.getStringExtra("KEY_ID");



        //
        tenantSearchAdapter = new TenantSearchAdapter(this,R.layout.item_tenant_search,dataList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //
        fireBaseTenantSearch.reTurnPostByLandlord(dataList,tenantSearchAdapter,mID);
        fireBaseTenantSearch.reTurnLandlordInfoByID(this,landlordInfo,mID);

        //load Landlord Info

        //set adapter
        recyclerView.setAdapter(tenantSearchAdapter);
        setEvent();
    }

    public void loadLandlordInfo() {
        user newUser = landlordInfo.get(0);
        txtName.setText(newUser.getName());
        txtPhone.setText(newUser.getPhone());

        //set tam thoi
        shapeableImageView.setImageResource(R.drawable.ic_user);
    }

    private void setEvent() {

        tenantSearchAdapter.setOnItemClickListener(new TenantSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                Toast.makeText(LandlordWallActivity.this, "balablalb", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setControl() {
        recyclerView = findViewById(R.id.recycler_landlord_wall);
        txtName = findViewById(R.id.txt_landlord_wall_name);
        txtPhone = findViewById(R.id.txt_landlord_wall_phone);
        shapeableImageView =findViewById(R.id.img_landlord_wall_avatar);
    }
}
