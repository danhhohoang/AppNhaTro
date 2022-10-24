package com.example.appnhatro;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TenantPostDetail extends AppCompatActivity {
    TextView house_name,area,price,address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_tenant_post_details);

        String it_housename = getIntent().getStringExtra("it_house_name");
        String it_address = getIntent().getStringExtra("it_address");
        String it_area = getIntent().getStringExtra("it_area");
        String it_price = getIntent().getStringExtra("it_price");

        house_name = findViewById(R.id.txt_tpdHousename);
        address = findViewById(R.id.txt_tpdAddress);
        area = findViewById(R.id.txt_tpdArea);
        price = findViewById(R.id.txt_tpdPrice);

        house_name.setText(it_housename);
        address.setText(it_address);
        area.setText(it_area);
        price.setText(it_price);
    }
}
