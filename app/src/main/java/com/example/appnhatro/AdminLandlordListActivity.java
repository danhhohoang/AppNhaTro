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

import com.example.appnhatro.Models.UserRoleModel;
import com.example.appnhatro.Models.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminLandlordListActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView al;
    AdminLandlordListAdapter adminLandlordListAdapter;
    DatabaseReference databaseReferenceUser;
    DatabaseReference databaseReferenceUserRole;
    public static ArrayList<user> list = new ArrayList<>();
    SearchView sv_all;
    Button btnAddLandlord_ALL;
    public static final String ID = "ID";
    public static final String CMND = "CMND";
    public static final String EMAIL = "EMAIL";
    public static final String NAME = "NAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String PHONE = "PHONE";
    public static final String BUNDLE = "BUNDLE";
    public static final String AVATAR = "AVATAR";
    public static final String STATUS = "STATUS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_landlord_list);
        setControl();
        setEvent();
    }


    public void setEvent() {
        al.setHasFixedSize(true);
        adminLandlordListAdapter = new AdminLandlordListAdapter(this,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        al.setLayoutManager(linearLayoutManager);

        adminLandlordListAdapter.setData(list);
        al.setAdapter(adminLandlordListAdapter);

        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("user");
        databaseReferenceUserRole = FirebaseDatabase.getInstance().getReference("User_Role");
        databaseReferenceUserRole.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserRoleModel user_role = dataSnapshot.getValue(UserRoleModel.class);
                    if (user_role.getId_role().equals("1")){
                        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    user User = dataSnapshot.getValue(user.class);
                                    if (User.getId().equals(user_role.getId_user())){
                                        list.add(User);
                                    }
                                }
                                adminLandlordListAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sv_all.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adminLandlordListAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adminLandlordListAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    public void setControl() {
        al = findViewById(R.id.rcv_al);
        btnAddLandlord_ALL = findViewById(R.id.btnAddLandlord_ALL);
        sv_all = findViewById(R.id.svDSChuTro);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, AdminLandlordDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AVATAR, list.get(position).getAvatar());
        bundle.putString(ID, list.get(position).getId());
        bundle.putString(NAME, list.get(position).getName());
        bundle.putString(CMND, list.get(position).getCitizenNumber());
        bundle.putString(PHONE, list.get(position).getPhone());
        bundle.putString(EMAIL, list.get(position).getEmail());
        bundle.putString(PASSWORD, list.get(position).getPassword());
        bundle.putString(STATUS, list.get(position).getStatus());
        intent.putExtra(BUNDLE, bundle);
        startActivity(intent);
    }
}
