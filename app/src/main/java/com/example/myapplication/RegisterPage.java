package com.example.myapplication;

import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPage extends AppCompatActivity {
    TextInputEditText username,fullnames,phoneNumber,emailRegister,passowrdRegister;
    Button Register;
    private FirebaseAuth mAuth;
    ProgressBar bar;
// ...
// Initialize Firebase Auth
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button buttonMainActivity = findViewById(
                R.id.Loginhere
        );
        username = findViewById(R.id.userName);
        fullnames = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phone);
        emailRegister = findViewById(R.id.Remail);
        passowrdRegister = findViewById(R.id.Rpassword);
        Button registerMe = findViewById(R.id.RegisterBtn);
        bar = findViewById(R.id.progressbar);

//        click to go back to login
        buttonMainActivity.setOnClickListener(view -> {
            Intent mainActivityIntent = new Intent(
                    getApplicationContext(), MainActivity.class
            );
            startActivity(mainActivityIntent);
            finish();
        });
//        click to register
        registerMe.setOnClickListener(view -> {
            bar.setVisibility(view.VISIBLE);
            String uname,fnames,pnumber,Remail,Rpass;

           uname=  username.getText().toString();

            fnames = fullnames.getText().toString();

           pnumber = phoneNumber.getText().toString();

            Remail= emailRegister.getText().toString();

            Rpass= passowrdRegister.getText().toString();


            if(TextUtils.isEmpty(Remail)){
                Toast.makeText(RegisterPage.this,"Enter Email is needed",Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(Rpass)){
                Toast.makeText(RegisterPage.this,"Enter Password is needed",Toast.LENGTH_LONG).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(Remail, Rpass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    bar.setVisibility(view.GONE);

                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(RegisterPage.this, "Account Created Successfully",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterPage.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });


    }
}