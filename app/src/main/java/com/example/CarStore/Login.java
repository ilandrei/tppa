package com.example.CarStore;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Login extends AppCompatActivity {
    private Button loginButton;
    private EditText userEdit;
    private EditText passEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginButton = findViewById(R.id.loginButton);
        this.userEdit = findViewById(R.id.editUser);
        this.passEdit = findViewById(R.id.editPass);
        this.loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

}
