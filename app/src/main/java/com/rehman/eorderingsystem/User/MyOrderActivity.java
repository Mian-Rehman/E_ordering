package com.rehman.eorderingsystem.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rehman.eorderingsystem.Model.OrderModel;
import com.rehman.eorderingsystem.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyOrderActivity extends AppCompatActivity {

    TextView tv_name,tv_foodName,tv_totalBill,tv_date;
    String accountCreationKey;
    String name,foodName,totalBill,date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        SharedPreferences sp=getSharedPreferences("CURRENT",MODE_PRIVATE);
        accountCreationKey = sp.getString("accountCreationKey","");

        tv_name = findViewById(R.id.tv_name);
        tv_foodName = findViewById(R.id.tv_foodName);
        tv_totalBill = findViewById(R.id.tv_totalBill);
        tv_date = findViewById(R.id.tv_date);


        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("orders");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    OrderModel model = snapshot.getValue(OrderModel.class);
                    if (accountCreationKey.equals(model.getAccountCreationKey()))
                    {
                        name = snapshot.child("name").getValue(String.class);
                        foodName = snapshot.child("orderKey").getValue(String.class);
                        date = snapshot.child("currentDate").getValue(String.class);

                        tv_name.setText("Name: "+name);
                        tv_foodName.setText("Order Key: "+foodName);
                        tv_date.setText("Date"+date);
                    }
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

    private String getCurrentdate()
    {
        return new SimpleDateFormat("dd/LLL/yyyy", Locale.getDefault()).format(new Date());
    }
}