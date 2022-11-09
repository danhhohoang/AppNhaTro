package com.example.appnhatro;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Firebase.FireBasePostListFindPeople;
import com.example.appnhatro.Firebase.FireBasePostRenting;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.PostFindRoomateModel;

import java.util.ArrayList;
import java.util.List;

public class TenantPostList extends AppCompatActivity {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    //List horizone
    private TenantPostListAdapter tenantPostListAdapter;
    private ArrayList<PostFindRoomateModel> postFindRoomateModels = new ArrayList<>();
    private ArrayList<Post> posts = new ArrayList<>();
    FireBasePostListFindPeople fireBasePostListFindPeople = new FireBasePostListFindPeople();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_list);

        ListPost();
    }

    public void ListPost(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_tpl);
        tenantPostListAdapter =  new TenantPostListAdapter(this, R.layout.layout_item_tenant_post_list,postFindRoomateModels,posts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        fireBasePostListFindPeople.readPostFindPeople(postFindRoomateModels,tenantPostListAdapter);
        fireBasePostListFindPeople.readPostList(posts,tenantPostListAdapter);
        recyclerView.setAdapter(tenantPostListAdapter);

    }

}
