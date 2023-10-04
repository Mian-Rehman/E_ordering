package com.rehman.eorderingsystem.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.rehman.eorderingsystem.R;

public class ConfirmAcivity extends AppCompatActivity {

    Button btn_admin,btn_user;
    String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_acivity);

        btn_admin = findViewById(R.id.btn_admin);
        btn_user = findViewById(R.id.btn_user);

        btn_admin.setOnClickListener(v -> {
           accountType = "Admin";
            nextScneen(accountType);
        });

        btn_user.setOnClickListener(v -> {
           accountType = "Users";
            nextScneen(accountType);
        });
    }

    private void nextScneen(String accountType)
    {
        Intent intent = new Intent(ConfirmAcivity.this,LoginActivity.class);
        intent.putExtra("accountType",accountType);
        startActivity(intent);
    }
}