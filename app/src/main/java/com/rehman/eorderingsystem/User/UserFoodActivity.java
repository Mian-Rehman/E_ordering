package com.rehman.eorderingsystem.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rehman.eorderingsystem.Aadapter.UserFoodAdapter;
import com.rehman.eorderingsystem.Model.FoodModel;
import com.rehman.eorderingsystem.R;

import java.util.ArrayList;

public class UserFoodActivity extends AppCompatActivity {

    RecyclerView recycleView;
    ImageView back_image;
    ArrayList<FoodModel> mDataList;
    UserFoodAdapter adapter;
    String tableNo;
    TextView back_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_food);

        initViews();
        getIntentValue();
        getFoodList();
    }

    private void getIntentValue()
    {
        Intent intent = getIntent();
        tableNo = intent.getStringExtra("tableNo");

        back_text.setText("Food List: Table No "+tableNo);
    }

    private void initViews()
    {
        back_image = findViewById(R.id.back_image);
        recycleView = findViewById(R.id.recyclerView);
        back_text = findViewById(R.id.back_text);
    }

    private void getFoodList()
    {
        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("KitchenFood");

        mDataList=new ArrayList<>();
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new UserFoodAdapter(this,mDataList);
        recycleView.setAdapter(adapter);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                        FoodModel model = snapshot.getValue(FoodModel.class);
                        assert model != null;
                        mDataList.add(model);
                        adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}