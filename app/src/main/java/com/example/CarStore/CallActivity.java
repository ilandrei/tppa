package com.example.CarStore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String number = bundle.getString(Intent.EXTRA_PHONE_NUMBER);
        TextView phoneNumber = findViewById(R.id.phoneNumber);
        phoneNumber.setText(number);
    }
}
