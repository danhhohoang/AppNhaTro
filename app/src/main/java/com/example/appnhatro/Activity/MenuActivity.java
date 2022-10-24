package com.example.appnhatro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.appnhatro.R;
import com.example.appnhatro.fragment.HomeFragment;
import com.example.appnhatro.fragment.PostFrgment;
import com.example.appnhatro.fragment.ReportFragment;
import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_REPORT = 1;
    private static final int FRAGMENT_POST = 2;

    //gán màn hình chính
    private int mCurrentFragment = FRAGMENT_REPORT;


    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ánh xạ view
        mDrawerLayout = findViewById(R.id.linear_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //mặc định khi mở là màn hìnhh
        replaceFragment(new ReportFragment());
        navigationView.getMenu().findItem(R.id.nav_Report).setChecked(true);

    }

    private void replaceFragment(ReportFragment reportFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, reportFragment);
    }


    @Override
    //set sự kiện chọn màn hình
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        } else if (id == R.id.nav_Report) {
            if (mCurrentFragment != FRAGMENT_REPORT) {
                replaceFragment(new ReportFragment());
                mCurrentFragment = FRAGMENT_REPORT;
            }
        } else if (id == R.id.nav_post) {
            if (mCurrentFragment != FRAGMENT_POST) {
                replaceFragment(new PostFrgment());
                mCurrentFragment = FRAGMENT_POST;
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer((GravityCompat.START));
        } else {
            super.onBackPressed();
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}