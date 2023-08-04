package com.example.myapplication;

import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextInputEditText editEmailField,editPasswordField;
    Button loginButton;
    private FirebaseAuth mAuth;

    public MainActivity(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editEmailField = findViewById(R.id.enterEmail);
        editPasswordField = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginBtn);

        // Initialize the button and set a click listener
        Button myButton = findViewById(R.id.registerBtn);
        myButton.setOnClickListener(v -> {
            // Define what happens when the button is clicked
            // For example, you can start a new activity here
            Intent intent = new Intent(MainActivity.this, RegisterPage.class);
            startActivity(intent);
            finish();
        });
//        Login button
        loginButton.setOnClickListener(view -> {
            String email,password;
            email = editEmailField.getText().toString();
            password = editPasswordField.getText().toString();

            if(TextUtils.isEmpty(email)){
                Toast.makeText(MainActivity.this,"Enter Email is needed",Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(MainActivity.this,"Enter Password is needed",Toast.LENGTH_LONG).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
//                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }
                        }
                    });

        });
    }
}