package com.example.appnhatro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class HomeTenantActivity extends AppCompatActivity implements RecyclerViewInterface{
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    RecyclerView ChoThue;
    RecyclerView OGhep;
    TenantPostDetailAdapter tenantPostDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.home_tenant_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardViewImageHome);
        persons.add("haha");
        persons.add("hoho");
        persons.add("hehe");
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
        ChoThue = findViewById(R.id.choThueRecyclerView);
        tenantPostDetailAdapter = new TenantPostDetailAdapter(this,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        ChoThue.setLayoutManager(linearLayoutManager);
        tenantPostDetailAdapter.setData(getPostList());
        ChoThue.setAdapter(tenantPostDetailAdapter);
    }
    @SuppressLint("MissingInflatedId")
    public void loadOGhep(){
        OGhep = findViewById(R.id.oGhepRecyclerView);
        tenantPostDetailAdapter = new TenantPostDetailAdapter(this,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        OGhep.setLayoutManager(linearLayoutManager);
        tenantPostDetailAdapter.setData(getPostList());
        OGhep.setAdapter(tenantPostDetailAdapter);
    }
    private List<PostList> getPostList(){
        List<PostList> postList = new ArrayList<>();
        postList.add(new PostList(1,1,30,17000000,"Homestay ở ghép Q7, 1,7 triệu/người BAO TẤT CẢ PHÍ","Địa chỉ: 134/15G Đường Nguyễn Thị Thập, Phường Bình Thuận, Quận 7, Hồ Chí Minh","Null","Cho thuê","Check"));
        postList.add(new PostList(2,2,30,20000000,"Phòng đẹp thoáng mát khu Phú Lợi, P7,Q.8","Địa chỉ: 288/62 Đường Dương Bá Trạc, Phường 2, Quận 8, Hồ Chí Minh","Null","Cho thuê","Check"));
        postList.add(new PostList(1,1,30,17000000,"Phòng full nội thất trong chung cư Quận 8","Địa chỉ: 21/1 Đường Trường Sơn, Phường 4, Tân Bình, Hồ Chí Minh","Null","Cho thuê","Check"));
        postList.add(new PostList(1,1,30,17000000,"Hẽm 60 Đường Số 2 Hiệp Bình Phước, Thủ Đức","Địa chỉ: Hẽm 60 Đường Số 2 Hiệp Bình Phước, Thủ Đức","Null","Cho thuê","Check"));
        postList.add(new PostList(1,1,30,17000000,"DT 25m2 có gác ở 3-4 người Ngay SPKT. Vincom Q9","Địa chỉ: Đường Phạm Văn Đồng, Phường Linh Tây, Thủ Đức, Hồ Chí Minh","Null","Cho thuê","Check"));
        return postList;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this,TenantPostDetail.class);
        intent.putExtra("it_house_name",getPostList().get(position).getHouse_name());
        intent.putExtra("it_address",getPostList().get(position).getAddress());
        intent.putExtra("it_area",getPostList().get(position).getArea());
        intent.putExtra("it_price",getPostList().get(position).getPrice());
        startActivity(intent);
    }
}