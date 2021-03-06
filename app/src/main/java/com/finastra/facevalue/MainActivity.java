package com.finastra.facevalue;

import android.content.Intent;
import android.media.FaceDetector;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import static android.app.PendingIntent.getActivity;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String loadFragment = "";
        if(getIntent().hasExtra("loadFragment")) {
            loadFragment = getIntent().getStringExtra("loadFragment");
        }

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        if(loadFragment.equalsIgnoreCase("AccountHistory")) {
            AccountHistoryFragment accountHistoryFragment = new AccountHistoryFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_main, accountHistoryFragment).commit();
        } else if (loadFragment.equalsIgnoreCase("MoneyTransfer")) {
            MoneyTransferFragment moneyTransferFragment = new MoneyTransferFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_main, moneyTransferFragment).commit();
        } else {
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_main, homeFragment).commit();
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account_settings) {
            // Handle the camera action
        }
//        else if (id == R.id.nav_scan_qr) { }
        else if (id == R.id.nav_receive_from) {
            Intent intent = new Intent(MainActivity.this, EnterAmountActivity.class);
            intent.putExtra("FaceGraphic", 3);
            startActivity(intent);
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(MainActivity.this, LogoutScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
