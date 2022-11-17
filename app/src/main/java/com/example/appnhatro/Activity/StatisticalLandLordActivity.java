package com.example.appnhatro.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.Post;
import com.example.appnhatro.Models.StatiscalLandLord;
import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.R;
import com.example.appnhatro.tool.GetValueFromLoop;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StatisticalLandLordActivity extends AppCompatActivity implements GetValueFromLoop{
    ArrayList barArray;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    int fee = 0 ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistical_landlord);

        setContentView(R.layout.statistical_landlord);
        BarChart barChart = findViewById(R.id.id_bc);
        data(new GetValueFromLoop() {
            @Override
            public void onCallBack(int value) {
                Log.d("Test", "onCallBack: "+value);
            }
        });
        BarDataSet barDataSet = new BarDataSet(barArray,"test");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);

    }

    private void data(final GetValueFromLoop getValueFromLoop){
        databaseReference1 = FirebaseDatabase.getInstance().getReference("HistoryTransaction");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Post");
        barArray = new ArrayList();

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    barArray = new ArrayList();
                    TransactionModel transactionModel = dataSnapshot.getValue(TransactionModel.class);
                    if (transactionModel.getId_user().equals("KH02")) {
                        String[] parse = transactionModel.getDate().split(" ");
                        String month;
                        month = parse[0];
                        if (month.equals("NOV")) {
                            int price = Integer.valueOf(transactionModel.getTotal());
                            fee += price - price * 0.5;
                        }
                    }
                }
                getValueFromLoop.onCallBack(fee);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

//        barArray.add(new BarEntry(2, fee));
//        barArray.add(new BarEntry(3, fee));
//        barArray.add(new BarEntry(4, fee));
//        barArray.add(new BarEntry(5, fee));
//        barArray.add(new BarEntry(6, fee));
//        barArray.add(new BarEntry(7, fee));
//        barArray.add(new BarEntry(8, fee));
//        barArray.add(new BarEntry(9, fee));
//        barArray.add(new BarEntry(10, fee));
//        barArray.add(new BarEntry(11, fee));
//        barArray.add(new BarEntry(12, fee));
    }

//    private void getData(){
//        data(new GetValueFromLoop() {
//            @Override
//            public void onCallBack(int value) {
//                this = value;
//            }
//        });
//    }

    @Override
    public void onCallBack(int value) {
        fee = value;
    }
}
