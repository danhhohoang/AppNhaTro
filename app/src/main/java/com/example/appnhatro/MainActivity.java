package com.example.appnhatro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appnhatro.Activity.AdminHomeActivity;
import com.example.appnhatro.Activity.AdminListAllPostActivity;
import com.example.appnhatro.Activity.AdminTenantListActivity;
import com.example.appnhatro.Activity.Admin_notification_Activity;
import com.example.appnhatro.Activity.LandLordHomeActivity;
import com.example.appnhatro.Activity.PostActivity;
import com.example.appnhatro.Activity.TermAndSerciveActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 4000);
    }

}