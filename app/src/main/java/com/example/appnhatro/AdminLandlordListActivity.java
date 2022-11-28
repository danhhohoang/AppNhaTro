package com.example.appnhatro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Models.USER_ROLE;
import com.example.appnhatro.Models.UserRoleModel;
import com.example.appnhatro.Models.user;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminLandlordListActivity extends AppCompatActivity {
    RecyclerView al;
    AdminLandlordListAdapter adminLandlordListAdapter;
    DatabaseReference databaseReferenceUser;
    DatabaseReference databaseReferenceUserRole;
    private ArrayList<user> list = new ArrayList<>();
    SearchView sv_all;
    Button btnAddLandlord_ALL,btnBack_ALL;

    public static final String ID = "ID";
    public static final String BUNDLE = "BUNDLE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_landlord_list);
        setControl();
        setEvent();
    }

    public void onRead(String keywords){
        al.setHasFixedSize(true);
        adminLandlordListAdapter = new AdminLandlordListAdapter(list, new RecyclerViewInterfaceAdminLandlord() {
            @Override
            public void onItemClickLandlord(user User) {
                onItemClick(User);
            }

            @Override
            public void onClick(int position) {
                Intent intent = new Intent(AdminLandlordListActivity.this, com.example.appnhatro.Activity.AdminLandlordListActivity.class);
                intent.putExtra("idpost",list.get(position).getId());
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        al.setLayoutManager(linearLayoutManager);

        adminLandlordListAdapter.setData(list);
        al.setAdapter(adminLandlordListAdapter);

        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("user");
        databaseReferenceUserRole = FirebaseDatabase.getInstance().getReference("User_Role");
        databaseReferenceUserRole.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                USER_ROLE user_role = snapshot.getValue(USER_ROLE.class);
                if (user_role != null) {
                    if (user_role.getId_role().equals("2")) {
                        databaseReferenceUser.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot1, @Nullable String previousChildName) {
                                user User = snapshot1.getValue(user.class);
                                if (User != null) {
                                    if (User.getId().equals(user_role.getId_user())) {
                                        if(User.getId().toLowerCase().contains(keywords.toLowerCase())){
                                            list.add(0,User);
                                        }
                                    }
                                }
                                adminLandlordListAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                user User = snapshot.getValue(user.class);
                                if (User == null || list == null || list.isEmpty()) {
                                    return;
                                }
                                for (int i = 0; i < list.size(); i++) {
                                    if (User.getId() == list.get(i).getId()) {
                                        list.set(i, User);
                                        adminLandlordListAdapter.notifyItemChanged(i);
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                user User = snapshot.getValue(user.class);
                                if (User == null || list == null || list.isEmpty()) {
                                    return;
                                }
                                for (int i = 0; i < list.size(); i++) {
                                    if (User.getId() == list.get(i).getId()) {
                                        list.set(i, User);
                                        adminLandlordListAdapter.notifyDataSetChanged();
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setEvent() {
        onRead("");

        sv_all.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                list.clear();
                onRead(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                list.clear();
                onRead(s);
                return false;
            }
        });
        btnBack_ALL.setOnClickListener(click->{
            finish();
        });
        btnAddLandlord_ALL.setOnClickListener(click->{
            Intent intent = new Intent(AdminLandlordListActivity.this,AdminLandlordAddActivity.class);
            startActivity(intent);
        });
    }

    public void setControl() {
        al = findViewById(R.id.rcv_al);
        btnAddLandlord_ALL = findViewById(R.id.btnAddLandlord_ALL);
        btnBack_ALL = findViewById(R.id.btnBack_ALL);
        sv_all = findViewById(R.id.svDSChuTro);
    }

    public void onItemClick(user User) {
        Intent intent = new Intent(this, AdminLandlordDetailsActivity.class);
        String status = User.getStatus();
        Bundle bundle = new Bundle();
        bundle.putSerializable("OBJECT", User);
        intent.putExtra("status",status);
        intent.putExtra(BUNDLE, bundle);
        startActivity(intent);
    }

}
