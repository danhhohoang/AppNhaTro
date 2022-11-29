package com.example.appnhatro.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.UpdateStatusLandlordAdapter;
import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.R;
import com.example.appnhatro.tool.RecycleViewUpdateStatus;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UpdateStatusLandlordActivity extends AppCompatActivity implements RecycleViewUpdateStatus {
    RecyclerView rc;
    ArrayList<TransactionModel> list;
    ImageView btn,back;
    SearchView sv_usapSearch;
    UpdateStatusLandlordAdapter updateStatusLandlordAdapter;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    String getID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_status_accept_payment);
        setControl();
        onRead("");
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        getID = sharedPreferences.getString("idUser", "");

        rc = findViewById(R.id.rcv_usap);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        updateStatusLandlordAdapter = new UpdateStatusLandlordAdapter(this,list,this);
        rc.setAdapter(updateStatusLandlordAdapter);
    }

    private void setControl() {
        sv_usapSearch = findViewById(R.id.sv_usap);
        btn = findViewById(R.id.iv_usapHistory);
        back = findViewById(R.id.iv_usapBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateStatusLandlordActivity.this,HistoryTransactionActivity.class);
                startActivity(intent);
            }
        });
        sv_usapSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    }


    public void onRead(String keyword){
        databaseReference = FirebaseDatabase.getInstance().getReference("HistoryTransaction");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TransactionModel transactionModel = snapshot.getValue(TransactionModel.class);
                if (transactionModel.getStatus().equals("0") && transactionModel.getId_landlord().equals(getID)){
                    if(transactionModel!=null){
                        if(transactionModel.getDate().toLowerCase().contains(keyword.toLowerCase())){
                            list.add(0,transactionModel);
                        }
                    }
                }
                updateStatusLandlordAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TransactionModel transactionModel = snapshot.getValue(TransactionModel.class);
                for (int i = 0;i< list.size();i++ ){
                    if (transactionModel.getId() == list.get(i).getId()){
                        list.remove(i);
                        updateStatusLandlordAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                TransactionModel transactionModel = snapshot.getValue(TransactionModel.class);
                for (int i = 0;i< list.size();i++ ){
                    if (transactionModel.getId() == list.get(i).getId()){
                        list.remove(i);
                        updateStatusLandlordAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TransactionModel transactionModel = snapshot.getValue(TransactionModel.class);
                for (int i = 0;i< list.size();i++ ){
                    if (transactionModel.getId() == list.get(i).getId()){
                        list.remove(i);
                        updateStatusLandlordAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onclick(int position) {
        TransactionModel transactionModel = new TransactionModel(list.get(position).getId(),list.get(position).getPrice(),list.get(position).getId_user(),list.get(position).getId_landlord(),list.get(position).getPost(),"2",list.get(position).getDate(),list.get(position).getDeposits());
        DatabaseReference databaseReference2;
        databaseReference2 = FirebaseDatabase.getInstance().getReference("HistoryTransaction");
        databaseReference2.child(list.get(position).getId()).child("status").setValue(transactionModel.getStatus());
    }
}
