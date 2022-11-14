package com.example.appnhatro.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.Landlord_Notification_Adapter;
import com.example.appnhatro.Firebase.FireBasePostFavorite;
import com.example.appnhatro.Firebase.FireBasePostRenting;
import com.example.appnhatro.Firebase.FirebaseLandlordNotification;
import com.example.appnhatro.Models.DatLichModels;
import com.example.appnhatro.Models.LikedPostModel;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.R;
import com.example.appnhatro.TenantPostRentingAdapter;
import com.example.appnhatro.ViewHolderImageHome;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Landlord_Notification_Activity extends AppCompatActivity {
    private ArrayList<String> persons = new ArrayList<>();
    private ViewHolderImageHome adapter;
    //List horizone
    private Landlord_Notification_Adapter landlord_notification_adapter;
    private ArrayList<DatLichModels> datLichModels = new ArrayList<>();

    SearchView sv_tpr;
    FirebaseLandlordNotification firebaseLandlordNotification = new FirebaseLandlordNotification();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_notification_list);
        ListPost();

    }

    public void ListPost() {
        RecyclerView recyclerView = findViewById(R.id.rcv_TB);
        landlord_notification_adapter = new Landlord_Notification_Adapter(this, R.layout.landlord_notification_booking_item, datLichModels);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        firebaseLandlordNotification.readPostFindPeople(datLichModels, landlord_notification_adapter, "KH02");
        recyclerView.setAdapter(landlord_notification_adapter);
//        landlord_notification_adapter.setOnItemClickListener(new Landlord_Notification_Adapter.OnItemClickListener() {
//            @Override
//            public void onItemClickListener(int position, View view) {
//                firebaseLandlordNotification.readFirebase(position, datLichModels, Landlord_Notification_Activity.this);
//            }
//        });
        sv_tpr = findViewById(R.id.sv_timkiem);
        sv_tpr.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                landlord_notification_adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                landlord_notification_adapter.getFilter().filter(s);
                return false;
            }
        });


    }
}
