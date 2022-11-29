package com.example.appnhatro.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.HistoryTransactionTenantAdapter;
import com.example.appnhatro.Adapters.HistoryTranstionAdapter;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryTransactionTenantActivity extends AppCompatActivity {
    RecyclerView rc;
    ArrayList<TransactionModel> list;
    SearchView sv_httSearch;
    ImageView back;
    HistoryTransactionTenantAdapter historyTransactionTenantAdapter;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    String getID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_transaction_tenant);
        setControl();
        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        getID = sharedPreferences.getString("idUser", "");

        rc = findViewById(R.id.rcv_htt);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        historyTransactionTenantAdapter = new HistoryTransactionTenantAdapter(this,list);
        rc.setAdapter(historyTransactionTenantAdapter);

        onRead("");
    }

    private void setControl() {
        sv_httSearch = findViewById(R.id.sv_htt);
        back = findViewById(R.id.iv_httBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sv_httSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                if (transactionModel.getStatus().equals("0")&&transactionModel.getId_user().equals(getID)){
                    if(transactionModel!=null){
                        if(transactionModel.getPrice().toLowerCase().contains(keyword.toLowerCase())){
                            list.add(transactionModel);
                            historyTransactionTenantAdapter.notifyDataSetChanged();
                        }
                        historyTransactionTenantAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TransactionModel transactionModel = snapshot.getValue(TransactionModel.class);
                if (transactionModel.getStatus().equals("1")){
                    if(transactionModel!=null){
                        if(transactionModel.getPrice().toLowerCase().contains(keyword.toLowerCase())){
                            list.add(transactionModel);
                            historyTransactionTenantAdapter.notifyDataSetChanged();
                        }
                        historyTransactionTenantAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                TransactionModel transactionModel = snapshot.getValue(TransactionModel.class);
                if (transactionModel.getStatus().equals("1")){
                    if(transactionModel!=null){
                        if(transactionModel.getPrice().toLowerCase().contains(keyword.toLowerCase())){
                            list.add(transactionModel);
                            historyTransactionTenantAdapter.notifyDataSetChanged();
                        }
                        historyTransactionTenantAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TransactionModel transactionModel = snapshot.getValue(TransactionModel.class);
                if (transactionModel.getStatus().equals("1")){
                    if(transactionModel!=null){
                        if(transactionModel.getPrice().toLowerCase().contains(keyword.toLowerCase())){
                            list.add(transactionModel);
                            historyTransactionTenantAdapter.notifyDataSetChanged();
                        }
                        historyTransactionTenantAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
