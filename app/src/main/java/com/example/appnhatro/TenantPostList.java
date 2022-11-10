package com.example.appnhatro;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Firebase.FireBasePostListFindPeople;
import com.example.appnhatro.Firebase.FireBasePostRenting;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.PostAndPostFindPeople;
import com.example.appnhatro.Models.PostFindRoomateModel;

import java.util.ArrayList;
import java.util.List;

public class TenantPostList extends AppCompatActivity {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;

    SearchView sv_tpl;
    //List horizone
    private TenantPostListAdapter tenantPostListAdapter;
    private ArrayList<PostAndPostFindPeople> postAndPostFindPeople = new ArrayList<>();
    FireBasePostListFindPeople fireBasePostListFindPeople = new FireBasePostListFindPeople();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_list);

        ListPost();
    }

    public void ListPost(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_tpl);
        tenantPostListAdapter =  new TenantPostListAdapter(this, R.layout.layout_item_tenant_post_list,postAndPostFindPeople);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        fireBasePostListFindPeople.readPostFindPeople(postAndPostFindPeople,tenantPostListAdapter);
        recyclerView.setAdapter(tenantPostListAdapter);

        sv_tpl = findViewById(R.id.sv_tpl);
        sv_tpl.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                tenantPostListAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                tenantPostListAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

}
