package com.rehman.eorderingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ComplainActivity extends AppCompatActivity {

    EditText ed_text;
    Button btn_send;
    String message,accountCreationKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        ed_text = findViewById(R.id.ed_text);
        btn_send = findViewById(R.id.btn_send);

        SharedPreferences sp=getSharedPreferences("CURRENT",MODE_PRIVATE);
        accountCreationKey = sp.getString("accountCreationKey","");

        btn_send.setOnClickListener(v -> {
            message = ed_text.getText().toString().trim();
            if (message.isEmpty())
            {
                ed_text.setError("field required");
                return;
            }else
            {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Complains");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String key  =reference.push().getKey();
                        Map<String,String> map = new HashMap<>();
                        map.put("key",key);
                        map.put("message",message);
                        map.put("accountCreationKey",accountCreationKey);

                        assert key != null;
                        reference.child(key).setValue(map);
                        Toast.makeText(ComplainActivity.this, "Complain send", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}