package com.rehman.eorderingsystem.Start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.rehman.eorderingsystem.R;
import com.rehman.eorderingsystem.User.UserFoodActivity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class QRScanActiivty extends AppCompatActivity {

    private PreviewView cameraPreview;
    TextView ed_username;
    Button btn_next;

    private ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan_actiivty);

        cameraPreview = findViewById(R.id.cameraPreview);
        ed_username = findViewById(R.id.ed_username);
        btn_next = findViewById(R.id.btn_next);

        btn_next.setVisibility(View.GONE);


        //checking camera permission
        if (ContextCompat.checkSelfPermission(QRScanActiivty.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        {
            initFunction();

        }else{
            ActivityCompat.requestPermissions(QRScanActiivty.this,new String[]{Manifest.permission.CAMERA},101);
        }


        btn_next.setOnClickListener(v -> {

            String tableNo = ed_username.getText().toString().trim();
            if (tableNo.isEmpty() || tableNo.equals(""))
            {
                Toast.makeText(this, "Scan QR Code to get Table No", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(QRScanActiivty.this, UserFoodActivity.class);
                intent.putExtra("tableNo",tableNo);
                startActivity(intent);
            }


        });

    }

    private void initFunction()
    {
        cameraProviderListenableFuture = ProcessCameraProvider.getInstance(QRScanActiivty.this);
        cameraProviderListenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();
                    bindImageAnalyse(cameraProvider);

                } catch (ExecutionException | InterruptedException e) {

                }
            }
        },ContextCompat.getMainExecutor(QRScanActiivty.this));
    }

    private  void bindImageAnalyse(ProcessCameraProvider processCameraProvider)
    {
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().setTargetResolution(new Size(1280,720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(QRScanActiivty.this), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {
                @SuppressLint("UnsafeOptInUsageError")
                Image mediaImage = image.getImage();

                if (mediaImage!=null)
                {
                    InputImage image2 = InputImage.fromMediaImage(mediaImage,image.getImageInfo().getRotationDegrees());


                    BarcodeScanner scanner = BarcodeScanning.getClient();

                    Task<List<Barcode>> results = scanner.process(image2);

                    results.addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {

                            for(Barcode barcode : barcodes)
                            {
                                final String getValue = barcode.getRawValue();

                                ed_username.setText(getValue);
                            }
                            btn_next.setVisibility(View.VISIBLE);
                            image.close();
                            mediaImage.close();

                        }
                    });



                }
            }
        });

        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(cameraPreview.getSurfaceProvider());
        processCameraProvider.bindToLifecycle(this,cameraSelector,imageAnalysis,preview);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            initFunction();
        }else{
            Toast.makeText( QRScanActiivty.this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}