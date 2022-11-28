package com.example.appnhatro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhatro.Adapters.HistoryTranstionAdapter;
import com.example.appnhatro.Adapters.UpdateStatusLandlordAdapter;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryTransactionActivity extends AppCompatActivity {
    RecyclerView rc;
    ArrayList<TransactionModel> list;
    SearchView sv_htSearch;
    ImageView back;
    HistoryTranstionAdapter historyTranstionAdapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_transaction);
        setControl();

        rc = findViewById(R.id.rcv_ht);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        historyTranstionAdapter = new HistoryTranstionAdapter(this,list);
        rc.setAdapter(historyTranstionAdapter);

        onRead("");
    }

    private void setControl() {
        sv_htSearch = findViewById(R.id.sv_ht);
        back = findViewById(R.id.iv_htBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sv_htSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                if (transactionModel.getStatus().equals("2")){
                    if(transactionModel!=null){
                        if(transactionModel.getPrice().toLowerCase().contains(keyword.toLowerCase())){
                            list.add(transactionModel);
                            historyTranstionAdapter.notifyDataSetChanged();
                        }
                        historyTranstionAdapter.notifyDataSetChanged();
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
                            historyTranstionAdapter.notifyDataSetChanged();
                        }
                        historyTranstionAdapter.notifyDataSetChanged();
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
                            historyTranstionAdapter.notifyDataSetChanged();
                        }
                        historyTranstionAdapter.notifyDataSetChanged();
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
                            historyTranstionAdapter.notifyDataSetChanged();
                        }
                        historyTranstionAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
