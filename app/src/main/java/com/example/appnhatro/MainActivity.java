package com.example.appnhatro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, HomeTenantActivity.class);
        startActivity(intent);
////        writeDatabase();
////        readDatabase();
    }
    private void Tri(){

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