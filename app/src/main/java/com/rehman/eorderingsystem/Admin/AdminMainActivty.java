package com.rehman.eorderingsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.rehman.eorderingsystem.R;

public class AdminMainActivty extends AppCompatActivity {

    CardView add_menu_card,manageMenu_card,order_card,complain_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_activty);

        add_menu_card = findViewById(R.id.add_menu_card);
        manageMenu_card = findViewById(R.id.manageMenu_card);
        order_card = findViewById(R.id.order_card);
        complain_card = findViewById(R.id.complain_card);



        add_menu_card.setOnClickListener(v -> {
        startActivity(new Intent(AdminMainActivty.this,AdminAddMenuActivity.class));
        });

        manageMenu_card.setOnClickListener(v -> {
        startActivity(new Intent(AdminMainActivty.this,AdminManageMenuAcitivity.class));
        });

        order_card.setOnClickListener(v -> {
        startActivity(new Intent(AdminMainActivty.this,AdminOrderActivity.class));
        });

        complain_card.setOnClickListener(v -> {
        startActivity(new Intent(AdminMainActivty.this,AdminCompainAcitvity.class));
        });
    }
}