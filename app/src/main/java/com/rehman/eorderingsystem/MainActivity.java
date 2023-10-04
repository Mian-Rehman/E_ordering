package com.rehman.eorderingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.rehman.eorderingsystem.Start.ConfirmAcivity;
import com.rehman.eorderingsystem.Start.QRScanActiivty;
import com.rehman.eorderingsystem.User.MyOrderActivity;
import com.rehman.eorderingsystem.User.UserFoodActivity;

public class MainActivity extends AppCompatActivity {

    NavigationView navMenu;
    ActionBarDrawerToggle toggle;
    DrawerLayout drayerLayout;

    CardView foodDelivery_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodDelivery_card = findViewById(R.id.card1);


        foodDelivery_card.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QRScanActiivty.class);
            intent.putExtra("checkScreen","direct");
            startActivity(intent);
        });


        Toolbar toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        navMenu=findViewById(R.id.navMenu);
        drayerLayout=findViewById(R.id.drawerlayout);


        toggle=new ActionBarDrawerToggle(this,drayerLayout,toolbar,R.string.app_name,R.string.app_name);
        drayerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {

                    case R.id.nav_order:
                        startActivity(new Intent(MainActivity.this, MyOrderActivity.class));
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                        case R.id.nav_complain:
                        startActivity(new Intent(MainActivity.this, ComplainActivity.class));
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:
                        Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        Intent logoutIntent=new Intent(MainActivity.this, ConfirmAcivity.class);
                        startActivity(logoutIntent);
                        finish();
                        drayerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return false;
            }
        });
    }
}