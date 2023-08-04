package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button buttonMainActivity = findViewById(
                R.id.Loginhere
        );
        Button registerMe = findViewById(R.id.RegisterBtn);
//        click to go back to login
        buttonMainActivity.setOnClickListener(view -> {
            Intent mainActivityIntent = new Intent(
                    getApplicationContext(), MainActivity.class
            );
            startActivity(mainActivityIntent);
        });
//        click to register

    }
}