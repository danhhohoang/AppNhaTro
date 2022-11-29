package com.example.appnhatro.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class StatisticalLandLordActivity extends AppCompatActivity{
    ArrayList barArray;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    Bundle bundle;
    ImageView back;
    int fee1,fee2,fee3,fee4,fee5,fee6,fee7,fee8,fee9,fee10,fee11,fee12 = 0 ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistical_landlord);
        getBundle();
        getData();
        BarChart barChart = findViewById(R.id.id_bc);
        back = findViewById(R.id.iv_slBack);
        BarDataSet barDataSet = new BarDataSet(barArray,"test");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);
        barChart.animateX(5000);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        return;
    }

//    private void data(){
//        databaseReference1 = FirebaseDatabase.getInstance().getReference("HistoryTransaction");
//        databaseReference2 = FirebaseDatabase.getInstance().getReference("Post");
//
//        databaseReference1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    barArray = new ArrayList();
//                    TransactionModel transactionModel = dataSnapshot.getValue(TransactionModel.class);
//                    if (transactionModel.getaId_user().equals("KH02")) {
//                        String[] parse = transactionModel.getDate().split(" ");
//                        String month;
//                        month = parse[0];
//                        if (month.equals("NOV")) {
//                            int price = Integer.valueOf(transactionModel.getTotal());
//                            int fee = 0;
//                            fee += price - price * 0.05;
//                            fee11 = fee;
//                        }
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }

    private void getData(){
        barArray = new ArrayList();
        barArray.add(new BarEntry(1, bundle.getInt("fee1")));
        barArray.add(new BarEntry(2, bundle.getInt("fee2")));
        barArray.add(new BarEntry(3, bundle.getInt("fee3")));
        barArray.add(new BarEntry(4, bundle.getInt("fee4")));
        barArray.add(new BarEntry(8, bundle.getInt("fee5")));
        barArray.add(new BarEntry(5, bundle.getInt("fee6")));
        barArray.add(new BarEntry(6, bundle.getInt("fee7")));
        barArray.add(new BarEntry(7, bundle.getInt("fee8")));
        barArray.add(new BarEntry(9, bundle.getInt("fee9")));
        barArray.add(new BarEntry(10, bundle.getInt("fee10")));
        barArray.add(new BarEntry(11, bundle.getInt("fee11")));
        barArray.add(new BarEntry(12, bundle.getInt("fee12")));
    }
    public void getBundle(){
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("month");

    }

    public void setControl(){
        back = findViewById(R.id.iv_slBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
