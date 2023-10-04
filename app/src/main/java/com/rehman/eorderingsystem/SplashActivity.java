package com.rehman.eorderingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.rehman.eorderingsystem.Start.ConfirmAcivity;
import com.rehman.eorderingsystem.Start.QRScanActiivty;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    ImageView sp_image;
    TextView sp_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(1024, 1024);

        sp_image = findViewById(R.id.sp_image);
        sp_text = findViewById(R.id.sp_text);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.fade_in);

        sp_image.startAnimation(animation);
        sp_text.startAnimation(animation1);

        startMain();
    }
    public void startMain() {
        new Handler(Looper.myLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, ConfirmAcivity.class));
            finish();
        }, 3000);
    }
}