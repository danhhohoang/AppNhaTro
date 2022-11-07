package com.example.appnhatro.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.LandLorNotificationPost;
import com.example.appnhatro.Firebase.FirebaseNoficationPost;
import com.example.appnhatro.HomeTenantActivity;
import com.example.appnhatro.Models.Dangbaimodels;
import com.example.appnhatro.MyRecyclerViewAdapter;
import com.example.appnhatro.R;
import com.example.appnhatro.ViewHolderImageHome;

import java.util.ArrayList;

public class tenant_notification_activity extends AppCompatActivity {
    LandLorNotificationPost Adapter;
    FirebaseNoficationPost NotficationPost=new FirebaseNoficationPost();
    ArrayList<Dangbaimodels> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_notification_list);
        loadNhachoThue();
    }
    @SuppressLint("MissingInflatedId")
    public void loadNhachoThue(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listnotifilecation);
        Adapter =  new LandLorNotificationPost(this, R.layout.tenant_post_notification_layout, arrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(Adapter);
        NotficationPost.readListPostFromUser(Adapter, arrayList);
    }
}
