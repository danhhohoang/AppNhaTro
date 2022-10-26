package com.example.appnhatro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView a;
    private ArrayList<String> persons = new ArrayList<String>();
    private ViewHolderImageHome adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_tenant_layout);
        Tri();
//        setContentView(R.layout.activity_main);
//        a.findViewById(R.id.texx);
////        writeDatabase();
////        readDatabase();
    }
    private void Tri(){
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardViewImageHome);
//        persons.add("haha");
//        persons.add("hoho");
//        persons.add("hehe");
//        adapter = new ViewHolderImageHome(this, R.layout.list_view_item_home_tenant, persons);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }
    //Tri
    private void writeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dulieu = database.getReference("test");
        dulieu.setValue("thucnghiem");
    }
    private void readDatabase(){
        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Lop = database.getReference("test");
        Lop.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                a.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}