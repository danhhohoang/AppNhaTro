package com.example.appnhatro;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TenantPostRenting extends AppCompatActivity {
    RecyclerView tpr;
    TenantPostRentingAdapter tenantPostRentingAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_renting);

        tpr = findViewById(R.id.rcv_tpr);
        tenantPostRentingAdapter = new TenantPostRentingAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        tpr.setLayoutManager(linearLayoutManager);

        tenantPostRentingAdapter.setData(getPostList());
        tpr.setAdapter(tenantPostRentingAdapter);

    }
    private List<PostList> getPostList(){
        List<PostList> lists = new ArrayList<>();
        lists.add(new PostList(1,1,30,17000000,"Homestay ở ghép Q7, 1,7 triệu/người BAO TẤT CẢ PHÍ","Địa chỉ: 134/15G Đường Nguyễn Thị Thập, Phường Bình Thuận, Quận 7, Hồ Chí Minh","Null","Cho thuê","Check"));
        lists.add(new PostList(2,2,30,20000000,"Phòng đẹp thoáng mát khu Phú Lợi, P7,Q.8","Địa chỉ: 288/62 Đường Dương Bá Trạc, Phường 2, Quận 8, Hồ Chí Minh","Null","Cho thuê","Check"));
        lists.add(new PostList(1,1,30,17000000,"Phòng full nội thất trong chung cư Quận 8","Địa chỉ: 21/1 Đường Trường Sơn, Phường 4, Tân Bình, Hồ Chí Minh","Null","Cho thuê","Check"));
        lists.add(new PostList(1,1,30,17000000,"Hẽm 60 Đường Số 2 Hiệp Bình Phước, Thủ Đức","Địa chỉ: Hẽm 60 Đường Số 2 Hiệp Bình Phước, Thủ Đức","Null","Cho thuê","Check"));
        lists.add(new PostList(1,1,30,17000000,"DT 25m2 có gác ở 3-4 người Ngay SPKT. Vincom Q9","Địa chỉ: Đường Phạm Văn Đồng, Phường Linh Tây, Thủ Đức, Hồ Chí Minh","Null","Cho thuê","Check"));
        return lists;
    }
}
