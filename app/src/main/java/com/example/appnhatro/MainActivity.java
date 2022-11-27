package com.example.appnhatro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Activity.AdminHomeActivity;
import com.example.appnhatro.Activity.LandLordHomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, LandLordHomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 4000);
    }

}