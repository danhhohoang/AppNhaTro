package com.example.appnhatro.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Models.TransactionModel;
import com.example.appnhatro.R;
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

public class StatisticalAdminActivity extends AppCompatActivity {
    ArrayList barArray;
    ArrayList barArrayFilter = new ArrayList();
    Spinner spinner;
    DatabaseReference databaseReferenceSp;
    List<String> names;
    String spDefaultValue;
    Bundle bundle;
    Button filter;
    ImageView back;
    int feefilter1,feefilter2,feefilter3,feefilter4,feefilter5,feefilter6,feefilter7,feefilter8,feefilter9,feefilter10,feefilter11,feefilter12 = 0 ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_admin);
        getBundle();
        getData();
        setControl();
        BarChart barChart = findViewById(R.id.bc_saAll);
        BarDataSet barDataSet = new BarDataSet(barArray,"test");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);
        barChart.animateX(1500);
        getDataFilter();
        BarChart barChart2 = findViewById(R.id.bc_saFilter);
        BarDataSet barDataSet2 = new BarDataSet(barArrayFilter,"test");
        BarData barData2 = new BarData(barDataSet);
        barChart2.setData(barData2);
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet2.setValueTextColor(Color.BLACK);
        barDataSet2.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);;
    }
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

    private void getDataFilter(){
        barArrayFilter = new ArrayList();
        barArrayFilter.clear();
        barArrayFilter.add(new BarEntry(1, feefilter1));
        barArrayFilter.add(new BarEntry(2, feefilter2));
        barArrayFilter.add(new BarEntry(3, feefilter3));
        barArrayFilter.add(new BarEntry(4, feefilter4));
        barArrayFilter.add(new BarEntry(8, feefilter5));
        barArrayFilter.add(new BarEntry(5, feefilter6));
        barArrayFilter.add(new BarEntry(6, feefilter7));
        barArrayFilter.add(new BarEntry(7, feefilter8));
        barArrayFilter.add(new BarEntry(9, feefilter9));
        barArrayFilter.add(new BarEntry(10, feefilter10));
        barArrayFilter.add(new BarEntry(11, feefilter11));
        barArrayFilter.add(new BarEntry(12, feefilter12));
        barArrayFilter.addAll(barArrayFilter);
    }
    public void getBundle(){
        Intent intent = getIntent();
        bundle = intent.getBundleExtra("month");
    }

    public void setControl(){
        back = findViewById(R.id.iv_saBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spinner = findViewById(R.id.sp_saUser);
        filter = findViewById(R.id.btn_saFilter);
        names = new ArrayList<>();
        databaseReferenceSp = FirebaseDatabase.getInstance().getReference();
        databaseReferenceSp.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String spinnerName = dataSnapshot.child("id").getValue(String.class);
                    names.add(spinnerName);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(StatisticalAdminActivity.this, android.R.layout.simple_spinner_item,names);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                spDefaultValue = selectedItem;
                data(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFilter();
                BarChart barChart = findViewById(R.id.bc_saFilter);
                BarDataSet barDataSet = new BarDataSet(barArrayFilter,"test");
                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                barDataSet.setValueTextColor(Color.BLACK);
                barDataSet.setValueTextSize(16f);
                barChart.getDescription().setEnabled(true);
                barDataSet.notifyDataSetChanged();
                barChart.invalidate();
            }
        });
    }
    private void data(String onSelected){
        feefilter1 = 0;
        feefilter2 = 0;
        feefilter3 = 0;
        feefilter4 = 0;
        feefilter5 = 0;
        feefilter6 = 0;
        feefilter7 = 0;
        feefilter8 = 0;
        feefilter9 = 0;
        feefilter10 = 0;
        feefilter11 = 0;
        feefilter12 = 0;
        DatabaseReference databaseReference1;
        databaseReference1 = FirebaseDatabase.getInstance().getReference("HistoryTransaction");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TransactionModel transactionModel = dataSnapshot.getValue(TransactionModel.class);
                    if (transactionModel.getId_landlord().equals(onSelected)) {
                        String[] parse = transactionModel.getDate().split("-");
                        String month;
                        month = parse[1];
                        if (month.equals("1")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m1 = 0;
                            m1 += price;
                            feefilter1 = m1;
                        }
                        if (month.equals("2")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m2 = 0;
                            m2 += price;
                            feefilter2 = m2;
                        }
                        if (month.equals("3")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m3 = 0;
                            m3 += price;
                            feefilter3 = m3;
                        }
                        if (month.equals("4")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m4 = 0;
                            m4 += price;
                            feefilter4 = m4;
                        }
                        if (month.equals("5")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m5 = 0;
                            m5 += price;
                            feefilter5 = m5;
                        }
                        if (month.equals("6")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m6 = 0;
                            m6 += price;
                            feefilter6 = m6;
                        }
                        if (month.equals("7")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m7 = 0;
                            m7 += price;
                            feefilter7 = m7;
                        }
                        if (month.equals("8")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m8 = 0;
                            m8 += price;
                            feefilter8 = m8;
                        }
                        if (month.equals("9")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m9 = 0;
                            m9 += price;
                            feefilter9 = m9;
                        }
                        if (month.equals("10")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m10 = 0;
                            m10 += price;
                            feefilter10 = m10;
                        }
                        if (month.equals("11")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m11 = 0;
                            m11 += price;
                            feefilter11 = m11;
                        }
                        if (month.equals("12")) {
                            int price = Integer.valueOf(transactionModel.getDeposits());
                            int m12 = 0;
                            m12 += price;
                            feefilter12 = m12;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
