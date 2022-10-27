package com.example.appnhatro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TenantPostDetail extends AppCompatActivity implements RecyclerViewInterface{
    TextView house_name,area,price,address;
    RecyclerView tpd;
    TenantPostDetailAdapter tenantPostDetailAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_details);

        tpd = findViewById(R.id.rcv_tpd);
        tenantPostDetailAdapter = new TenantPostDetailAdapter(this,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        tpd.setLayoutManager(linearLayoutManager);

        tenantPostDetailAdapter.setData(getPostList());
        tpd.setAdapter(tenantPostDetailAdapter);

        String it_housename = getIntent().getStringExtra("it_house_name");
        String it_address = getIntent().getStringExtra("it_address");
        String it_area = getIntent().getStringExtra("it_area");
        String it_price = getIntent().getStringExtra("it_price");

        house_name = findViewById(R.id.txt_tpdHousename);
        address = findViewById(R.id.txt_tpdAddress);
        area = findViewById(R.id.txt_tpdArea);
        price = findViewById(R.id.txt_tpdPrice);

        house_name.setText(it_housename);
        address.setText(it_address);
        area.setText(it_area);
        price.setText(it_price);
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
