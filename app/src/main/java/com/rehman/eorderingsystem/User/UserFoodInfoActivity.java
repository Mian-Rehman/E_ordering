package com.rehman.eorderingsystem.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import javax.sql.StatementEvent;

public class UserFoodInfoActivity extends AppCompatActivity {

    Button btn_confirm,btn_divider,btn_sendMessage;
    EditText ed_name,ed_phoneNumber,ed_divider,ed_num1,ed_num2,ed_num3,ed_num4,ed_num5;
    String date,Time,name,phone,orderKey;
    String timeWithAMPM,currentDate,currentDateOnly,dateWithMonth;
    LinearLayout input_Layout,time_layout,ll_divider;
    TextView tv_text,text_title,tv_foodName,tv_foodPrice,tv_foodQuantityPrice,tv_totalPrice,tv_dividerCal;
    String foodName,foodKey,foodPrice,foodQuantity,foodCategory,foodImage;
    int totalTime = 0,quantity = 0;
    int price,totalQuantity;

    String billDivider;

    String accountCreationKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_food_info);

        SharedPreferences sp=getSharedPreferences("CURRENT",MODE_PRIVATE);
        accountCreationKey = sp.getString("accountCreationKey","");

        btn_confirm = findViewById(R.id.btn_confirm);
        ed_name = findViewById(R.id.ed_name);
        ed_phoneNumber = findViewById(R.id.ed_phoneNumber);
        input_Layout = findViewById(R.id.input_Layout);
        time_layout = findViewById(R.id.time_layout);
        tv_text = findViewById(R.id.tv_text);
        text_title = findViewById(R.id.text);
        tv_foodName = findViewById(R.id.tv_foodName);
        tv_foodPrice = findViewById(R.id.tv_foodPrice);
        tv_foodQuantityPrice = findViewById(R.id.tv_foodQuantityPrice);
        tv_totalPrice = findViewById(R.id.tv_totalPrice);
        ed_divider = findViewById(R.id.ed_divider);
        btn_divider = findViewById(R.id.btn_divider);
        tv_dividerCal = findViewById(R.id.tv_dividerCal);
        ll_divider = findViewById(R.id.ll_divider);
        btn_sendMessage = findViewById(R.id.btn_sendMessage);
        ed_num1 = findViewById(R.id.ed_num1);
        ed_num2 = findViewById(R.id.ed_num2);
        ed_num3 = findViewById(R.id.ed_num3);
        ed_num4 = findViewById(R.id.ed_num4);
        ed_num5 = findViewById(R.id.ed_num5);

        time_layout.setVisibility(View.GONE);
        ll_divider.setVisibility(View.GONE);
        ed_num1.setVisibility(View.GONE);
        ed_num2.setVisibility(View.GONE);
        ed_num3.setVisibility(View.GONE);
        ed_num4.setVisibility(View.GONE);
        ed_num5.setVisibility(View.GONE);


        getIntentValue();

        btn_confirm.setOnClickListener(v -> {
            name = ed_name.getText().toString().trim();
            phone = ed_phoneNumber.getText().toString().trim();

            if (isValid(name,phone))
            {
                placeOrder();
            }
        });


        btn_divider.setOnClickListener(v -> {
            billDivider = ed_divider.getText().toString().trim();
            double divider = Double.parseDouble(billDivider);
            double totalDivider  = Double.parseDouble(tv_totalPrice.getText().toString().trim()) / divider;
            tv_dividerCal.setText(String.valueOf(totalDivider));

            if (billDivider.equals("1"))
            {
                ed_num1.setVisibility(View.VISIBLE);
            } else if (billDivider.equals("2")) {
                ed_num1.setVisibility(View.VISIBLE);
                ed_num2.setVisibility(View.VISIBLE);
            }else if (billDivider.equals("3")) {
                ed_num1.setVisibility(View.VISIBLE);
                ed_num2.setVisibility(View.VISIBLE);
                ed_num3.setVisibility(View.VISIBLE);
            } else if (billDivider.equals("4")) {
                ed_num1.setVisibility(View.VISIBLE);
                ed_num2.setVisibility(View.VISIBLE);
                ed_num3.setVisibility(View.VISIBLE);
                ed_num4.setVisibility(View.VISIBLE);
            } else if (billDivider.equals("5")) {
                ed_num1.setVisibility(View.VISIBLE);
                ed_num2.setVisibility(View.VISIBLE);
                ed_num3.setVisibility(View.VISIBLE);
                ed_num4.setVisibility(View.VISIBLE);
                ed_num5.setVisibility(View.VISIBLE);
            }

        });


        btn_sendMessage.setOnClickListener(v -> {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            {
                if(checkSelfPermission(Manifest.permission.SEND_SMS)==
                        PackageManager.PERMISSION_GRANTED)
                {
                    sendSMS();
                }else{
                    requestPermissions(new
                            String[]{Manifest.permission.SEND_SMS},1);

                }

            }
        });

    }

    private void sendSMS()
    {
        String MobileNo=ed_num1.getText().toString().trim();
        String SMSMessage= "Total Bill is : RS " + tv_totalPrice.getText().toString().trim() + " and You will pay: RS  "+ tv_dividerCal.getText().toString().trim();

        try
        {
            SmsManager smgr=SmsManager.getDefault();
            smgr.sendTextMessage(MobileNo,null,SMSMessage,null,null);

            Toast.makeText(UserFoodInfoActivity.this, " SMS send Successfully",
                    Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(UserFoodInfoActivity.this, "SMS Failed to send",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void getIntentValue()
    {

        Intent intent = getIntent();
        foodName = intent.getStringExtra("foodName");
        foodKey = intent.getStringExtra("foodKey");
        foodPrice = intent.getStringExtra("foodPrice");
        foodQuantity = intent.getStringExtra("foodQuantity");
        foodCategory = intent.getStringExtra("foodCategory");
        foodImage = intent.getStringExtra("foodImage");

        tv_foodName.setText(foodName);
        tv_foodPrice.setText(foodPrice);
        tv_foodQuantityPrice.setText(foodQuantity);


        totalQuantity = Integer.parseInt(foodQuantity);
        price = Integer.parseInt(foodPrice);

        int total = totalQuantity * price;
        tv_totalPrice.setText(String.valueOf(total));

        quantity = Integer.parseInt(foodQuantity);
        totalTime = quantity * 1000;
    }

    private void countdownTimer()
    {
        new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_text.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tv_text.setText("Order Time Complete!");
            }
        }.start();

    }

    private void placeOrder()
    {
        timeWithAMPM = getTimeWithAmPm();
        currentDate = getCurrentdate();
        currentDateOnly = getCurrentdateOnly();
        dateWithMonth = getdateWithMonth();

        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("orders");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                time_layout.setVisibility(View.VISIBLE);
                ll_divider.setVisibility(View.VISIBLE);
                btn_confirm.setVisibility(View.GONE);
            String key =  reference.push().getKey();
                OrderModel model = new OrderModel(name,phone,timeWithAMPM,currentDate
                ,currentDateOnly,dateWithMonth,key,accountCreationKey);
                assert key != null;
                reference.child(key).setValue(model);
                input_Layout.setVisibility(View.GONE);
                Toast.makeText(UserFoodInfoActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                countdownTimer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean isValid(String name, String phone)
    {
        if (name.isEmpty())
        {
            ed_name.setError("field required");
            ed_name.requestFocus();
            return false;
        }

        if(phone.isEmpty())
        {
            ed_phoneNumber.setError("field required");
            ed_phoneNumber.requestFocus();
            return  false;
        }

        return true;

    }
    private String getTimeWithAmPm()
    {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getCurrentdate()
    {
        return new SimpleDateFormat("dd/LLL/yyyy", Locale.getDefault()).format(new Date());
    }

    private String getCurrentdateOnly()
    {
        return new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
    }

    private String getdateWithMonth()
    {
        return new SimpleDateFormat("LLL", Locale.getDefault()).format(new Date());
    }
}