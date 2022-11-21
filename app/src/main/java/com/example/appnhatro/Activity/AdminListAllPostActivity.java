package com.example.appnhatro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.appnhatro.Adapters.AdminListPostAdapter;
import com.example.appnhatro.Adapters.LandLordHomeListAdapter;
import com.example.appnhatro.Firebase.FirebaseAdmin;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.R;

import java.util.ArrayList;

public class AdminListAllPostActivity extends AppCompatActivity {
    private AdminListPostAdapter adminListPostAdapter;
    private ArrayList<Post> listAll = new ArrayList<>();
    private SearchView searchView;
    private ImageView back;
    private FirebaseAdmin firebaseAdmin = new FirebaseAdmin();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_list_all_post_activity);
        recyclerView = (RecyclerView) findViewById(R.id.rvcAllPostAdmin);
        adminListPostAdapter = new AdminListPostAdapter(this, R.layout.admin_item_comment_layout,listAll);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adminListPostAdapter);
        searchView = findViewById(R.id.sv_Search_Post_Admin);
        back = findViewById(R.id.imgBackListAllPostAdmin);
        event();
    }

    private void event() {
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fillterList(newText);
                return true;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adminListPostAdapter.setOnItemClickListener(new AdminListPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                Post post = listAll.get(position);
                Intent intent = new Intent(AdminListAllPostActivity.this, AdminPostDetailActivity.class);
                intent.putExtra("Post", post);
                startActivity(intent);
            }
        });
    }

    private void fillterList(String text) {
        ArrayList<Post> fillterList = new ArrayList<>();
        for (Post item : listAll)
        {
            if (item.getHouse_name().toLowerCase().contains(text.toLowerCase())) {
                fillterList.add(item);
            }

        }
        if(fillterList.isEmpty()){
        }else {
            adminListPostAdapter = new AdminListPostAdapter(this, R.layout.lanlord_home_item_layout,fillterList);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(gridLayoutManager);
            adminListPostAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAdmin.readAllListPost(adminListPostAdapter,listAll);
    }
}