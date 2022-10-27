package com.example.appnhatro;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TenantPostList extends AppCompatActivity {
    RecyclerView tpl;
    TenantPostListAdapter tenantPostListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_list);

        tpl = findViewById(R.id.rcv_tpl);
        tenantPostListAdapter = new TenantPostListAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        tpl.setLayoutManager(linearLayoutManager);

        tenantPostListAdapter.setData(getPostListFindPeople());
        tpl.setAdapter(tenantPostListAdapter);
    }
    private List<PostListFindPeople> getPostListFindPeople(){
        List<PostListFindPeople> postListFindPeople = new ArrayList<>();
        postListFindPeople.add(new PostListFindPeople(1,1,1,1,1,"Null","Null","Null","Nam"));
        postListFindPeople.add(new PostListFindPeople(1,1,1,1,1,"Null","Null","Null","Nam"));
        return postListFindPeople;
    }
}
