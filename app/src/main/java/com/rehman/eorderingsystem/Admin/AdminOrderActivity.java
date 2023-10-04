package com.rehman.eorderingsystem.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rehman.eorderingsystem.Aadapter.OrderAdapter;
import com.rehman.eorderingsystem.Aadapter.UserFoodAdapter;
import com.rehman.eorderingsystem.Model.FoodModel;
import com.rehman.eorderingsystem.Model.OrderModel;
import com.rehman.eorderingsystem.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdminOrderActivity extends AppCompatActivity {

    Spinner month_spinner;
    Button btn_today;
    String value,date;
    String buttonStatus = "";

    RecyclerView recycleView;
    ImageView back_image;
    ArrayList<OrderModel> mDataList;
    OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);

        month_spinner = findViewById(R.id.month_spinner);
        btn_today = findViewById(R.id.btn_today);
        recycleView = findViewById(R.id.recycleView);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.MonthArray, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(adapter);

        month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                value = parent.getItemAtPosition(position).toString();

                Toast.makeText(parent.getContext(), value, Toast.LENGTH_SHORT).show();

                buttonStatus = "spinner";
                showOrder(buttonStatus,value);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_today.setOnClickListener(v -> {
              value = getCurrentdate();
              buttonStatus = "button";
              showOrder(buttonStatus,value);
        });
    }

    private void showOrder(String buttonStatus, String value)
    {
        mDataList=new ArrayList<>();
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new OrderAdapter(this,mDataList);
        recycleView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.exists())
                {
                    if (buttonStatus.equals("button"))
                    {
                        OrderModel model  = snapshot.getValue(OrderModel.class);
                        assert model != null;
                        if (value.equals(model.getCurrentDate()))
                        {
                            mDataList.add(model);
                            adapter.notifyDataSetChanged();

                        }
                    }

                    if (buttonStatus.equals("spinner"))
                    {
                        OrderModel model  = snapshot.getValue(OrderModel.class);
                        assert model != null;
                        if (value.equals(model.getDateWithMonth()))
                        {
                            mDataList.add(model);
                            adapter.notifyDataSetChanged();

                        }
                    }else
                    {
                        Toast.makeText(AdminOrderActivity.this, "Month Not Found", Toast.LENGTH_SHORT).show();
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