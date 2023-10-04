package com.rehman.eorderingsystem.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rehman.eorderingsystem.Aadapter.ComplainAdapter;
import com.rehman.eorderingsystem.Aadapter.OrderAdapter;
import com.rehman.eorderingsystem.Model.ComplainModel;
import com.rehman.eorderingsystem.Model.OrderModel;
import com.rehman.eorderingsystem.R;

import java.util.ArrayList;

public class AdminCompainAcitvity extends AppCompatActivity {

    RecyclerView recycleView;


    ArrayList<ComplainModel> mDataList;
    ComplainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_compain_acitvity);

        recycleView = findViewById(R.id.recycleView);

        showOrder();
    }

    private void showOrder()
    {
        mDataList=new ArrayList<>();
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ComplainAdapter(this,mDataList);
        recycleView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Complains");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.exists())
                {
                        ComplainModel model  = snapshot.getValue(ComplainModel.class);
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