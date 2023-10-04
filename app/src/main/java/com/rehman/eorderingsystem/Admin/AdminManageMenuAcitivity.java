package com.rehman.eorderingsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rehman.eorderingsystem.Aadapter.FoodAdapter;
import com.rehman.eorderingsystem.Model.FoodModel;
import com.rehman.eorderingsystem.R;

import java.util.ArrayList;
import java.util.List;

public class AdminManageMenuAcitivity extends AppCompatActivity {

    ImageView back_image;
    RecyclerView recyclerView;
    FoodAdapter adapter;
    private List<FoodModel> mDataList;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_menu_acitivity);

        initViews();

        back_image.setOnClickListener(v -> {
            onBackPressed();
        });

        mDataList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<FoodModel> options =
                new FirebaseRecyclerOptions.Builder<FoodModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("KitchenFood"),FoodModel.class)
                        .build();

        adapter=new FoodAdapter(options,this);
        recyclerView.setAdapter(adapter);
    }

    private void initViews()
    {
        back_image = findViewById(R.id.back_image);
        recyclerView = findViewById(R.id.recyclerView);
    }
}