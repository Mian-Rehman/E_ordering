package com.rehman.eorderingsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rehman.eorderingsystem.R;

public class FoodCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_chicken,tv_vegetable,tv_daal;
    String selectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_category);

        tv_chicken = findViewById(R.id.tv_chicken);
        tv_vegetable = findViewById(R.id.tv_vegetable);
        tv_daal = findViewById(R.id.tv_daal);

        tv_chicken.setOnClickListener(this::onClick);
        tv_vegetable.setOnClickListener(this::onClick);
        tv_daal.setOnClickListener(this::onClick);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id)
        {
            case R.id.tv_chicken:
                selectText = tv_chicken.getText().toString();
                break;

            case R.id.tv_vegetable:
                selectText = tv_vegetable.getText().toString();
                break;

            case R.id.tv_daal:
                selectText = tv_daal.getText().toString();
                break;
        }
        Intent intent = new Intent();
        intent.putExtra("categoryListIntent", selectText);
        setResult(RESULT_OK, intent);
        finish();
    }
}