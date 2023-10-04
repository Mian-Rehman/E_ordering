package com.rehman.eorderingsystem.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rehman.eorderingsystem.Model.FoodModel;
import com.rehman.eorderingsystem.R;

import java.io.IOException;
import java.io.InputStream;

public class AdminAddMenuActivity extends AppCompatActivity {


    ImageView back_image,food_image;
    TextView tv_vendorName;
    Button btn_addImage,btn_save;
    EditText ed_foodName,ed_foodPrice,ed_foodQuantity,ed_foodQDes;
    TextView ed_foodCategory;
    String foodName,foodCategory,foodPrice,foodQuantity,foodDescription = "";

    private static int IMAGE_REQUEST_CODE = 100;
    Bitmap bitmap;
    Uri uri;
    FirebaseStorage storage;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_menu);

        initViews();


        back_image.setOnClickListener(v -> {
            startActivity(new Intent(AdminAddMenuActivity.this,AdminMainActivty.class));
            finish();
        });

        ed_foodCategory.setOnClickListener(v -> {
            Intent intent = new Intent(this, FoodCategoryActivity.class);
            startActivityForResult(intent, 1);
        });


        btn_addImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_REQUEST_CODE);
        });

        btn_save.setOnClickListener(v -> {
            progressDialog = ProgressDialog.show(this, "Please wait", "Processing", true);
            foodName = ed_foodName.getText().toString().trim();
            foodCategory = ed_foodCategory.getText().toString().trim();
            foodPrice = ed_foodPrice.getText().toString().trim();
            foodQuantity = ed_foodQuantity.getText().toString().trim();
            foodDescription = ed_foodQDes.getText().toString().trim();

            if (isValid(foodName,foodCategory,foodPrice,foodQuantity))
            {
                if (uri!=null)
                {
                    saveToDatabaseStorage();
                }else
                {
                    Toast.makeText(this, "Add Food Image", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        });

    }

    private boolean isValid(String foodName, String foodCategory,
                            String foodPrice, String foodQuantity)
    {
        if (foodName.isEmpty())
        {
            ed_foodName.setError("field required");
            ed_foodName.requestFocus();
            progressDialog.dismiss();
            return false;
        }

        if (foodCategory.isEmpty())
        {
            Toast.makeText(this, "select category", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return false;
        }

        if (foodPrice.isEmpty())
        {
            ed_foodPrice.setError("field required");
            ed_foodPrice.requestFocus();
            progressDialog.dismiss();
            return false;
        }


        if (foodQuantity.isEmpty())
        {
            ed_foodQuantity.setError("field required");
            ed_foodQuantity.requestFocus();
            progressDialog.dismiss();
            return false;
        }

        return true;
    }

    private void saveToDatabaseStorage()
    {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("foodImage");
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("FoodImage");
        StorageReference storage = storageReference.child("foodImage");
        storage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {

                storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String imageUri = uri.toString();
//                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("KitchenFood");
//                        ref.setValue(uri.toString());
                        Log.d("pnnn", "onSuccess: "+uri.toString());
                        saveDataToRealTime(imageUri);

                    }
                });

                dialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float perecent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                dialog.setMessage("Uploaded :"+(int)perecent + "%");
            }
        });
    }

    private void saveDataToRealTime(String imageUri)
    {
        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("KitchenFood");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key  =reference.push().getKey();
                FoodModel model = new FoodModel(foodName,foodCategory,foodPrice,foodQuantity
                        ,foodDescription,key,imageUri);
                assert key != null;
                reference.child(key).setValue(model);
                Toast.makeText(AdminAddMenuActivity.this, "Kitchen Food Added", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void initViews()
    {
        back_image = findViewById(R.id.back_image);
        food_image = findViewById(R.id.food_image);
        btn_addImage = findViewById(R.id.btn_addImage);
        ed_foodName = findViewById(R.id.ed_foodName);
        ed_foodCategory = findViewById(R.id.ed_foodCategory);
        ed_foodPrice = findViewById(R.id.ed_foodPrice);
        ed_foodQuantity = findViewById(R.id.ed_foodQuantity);
        ed_foodQDes = findViewById(R.id.ed_foodQDes);
        btn_save = findViewById(R.id.btn_save);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST_CODE && resultCode==RESULT_OK && data!=null) {
            uri = data.getData();
            try {

                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                food_image.setImageBitmap(bitmap);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                assert data != null;
                foodCategory = data.getStringExtra("categoryListIntent");
                ed_foodCategory.setText(foodCategory);
            }
        }
    }
}