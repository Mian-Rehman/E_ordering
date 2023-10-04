package com.rehman.eorderingsystem.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rehman.eorderingsystem.R;

public class CartActivity extends AppCompatActivity {

    String vendorKey,foodName,foodDescription,foodPrice,foodCategory,foodDay,foodImage
            ,foodQuantity,foodKey,phoneNumber;

    ImageView back_image,food_Image,minus_image,plus_image;
    TextView tv_foodName,tv_foodPrice,tv_foodDes,tv_count;
    EditText ed_instruction;
    Button btn_addCard,btn_ViewCart;
    int count,price;
    ProgressDialog progressDialog;

    @Override
    protected void onResume() {
        super.onResume();
        if (progressDialog!=null)
        {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initViews();
        getIntentValue();

        back_image.setOnClickListener(v -> {
            onBackPressed();
        });

        tv_count.setText("1");


        plus_image.setOnClickListener(v -> {
            count = Integer.parseInt(tv_count.getText().toString());
            count = count + 1;
            tv_count.setText(String.valueOf(count));
        });

        minus_image.setOnClickListener(v -> {
            count = Integer.parseInt(tv_count.getText().toString());
            if (count == 1)
            {
                tv_count.setText("1");
            }else
            {
                count = count - 1;
                tv_count.setText(String.valueOf(count));
            }
        });


        btn_addCard.setOnClickListener(v -> {
            progressDialog = ProgressDialog.show(CartActivity.this, "", "Please wait", true);
            Intent intent =new Intent(CartActivity.this, UserFoodInfoActivity.class);
            intent.putExtra("foodName",foodName);
            intent.putExtra("foodKey",foodKey);
            intent.putExtra("foodPrice",foodPrice);
            intent.putExtra("foodQuantity",String.valueOf(tv_count.getText().toString()));
            intent.putExtra("foodCategory",foodCategory);
            intent.putExtra("foodImage",foodImage);
            startActivity(intent);
        });
    }

    private void initViews()
    {
        btn_addCard = findViewById(R.id.btn_addCard);
        btn_ViewCart = findViewById(R.id.btn_ViewCart);
        tv_count = findViewById(R.id.tv_count);
        plus_image = findViewById(R.id.plus_image);
        minus_image = findViewById(R.id.minus_image);
        ed_instruction = findViewById(R.id.ed_instruction);
        tv_foodDes = findViewById(R.id.tv_foodDes);
        tv_foodPrice = findViewById(R.id.tv_foodPrice);
        tv_foodName = findViewById(R.id.tv_foodName);
        back_image = findViewById(R.id.back_image);
        food_Image = findViewById(R.id.food_Image);
    }

    private void getIntentValue()
    {

        Intent intent = getIntent();
        foodName = intent.getStringExtra("foodName");
        foodDescription = intent.getStringExtra("foodDescription");
        foodPrice = intent.getStringExtra("foodPrice");
        foodCategory = intent.getStringExtra("foodCategory");
        foodImage = intent.getStringExtra("foodImage");
        foodQuantity = intent.getStringExtra("foodQuantity");
        foodKey = intent.getStringExtra("foodKey");


        Glide.with(CartActivity.this).load(foodImage).into(food_Image);
        tv_foodName.setText(foodName);
        tv_foodDes.setText(foodDescription);
        tv_foodPrice.setText("RS: "+foodPrice);

    }

    private void getVendorDetails(String vendorKey)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("KitchenOwner");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.exists())
                {
                    String key = snapshot.child("accountCreationKey").getValue(String.class);

                    if (key.equals(vendorKey))
                    {
                        phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
//                        Glide.with(UserFoodInfoActivity.this).load(vendorImage).into();
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
}