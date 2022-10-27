package com.example.appnhatro.Activity;

import static android.widget.AdapterView.*;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.MainActivity;
import com.example.appnhatro.R;

public class PostActivity extends AppCompatActivity {

    Spinner spntinhtrang;
    Spinner spngioitinh;
    Spinner spnsoluong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        SpinnerSet();

    }

    private void SpinnerSet() {
        spntinhtrang = findViewById(R.id.spntinhtrang);
        spngioitinh = findViewById(R.id.spngioitinh);
        spnsoluong = findViewById(R.id.spnSl);

        ArrayAdapter status = new ArrayAdapter<String>(this, R.layout.post_layout, getResources().getStringArray(R.array.string_status));
        ArrayAdapter sex = new ArrayAdapter<String>(this, R.layout.post_layout, getResources().getStringArray(R.array.string_sex));
        ArrayAdapter amount = new ArrayAdapter<String>(this, R.layout.post_layout, getResources().getStringArray(R.array.string_amount));

        status.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        sex.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        amount.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spntinhtrang.setAdapter(status);
        spngioitinh.setAdapter(sex);
        spnsoluong.setAdapter(amount);

        spntinhtrang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(PostActivity.this, "h", Toast.LENGTH_SHORT).show();
            }
        });

        spngioitinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }
}