package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView username,carType,location,rate,plate,model;
    Button logout, add_car;
    boolean firebaseInitialized;
    FirebaseDatabase db;
    GoogleSignInClient gClient;
    DatabaseReference reference;
    GoogleSignInOptions gOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (!firebaseInitialized) {
//            initializeFirebase();
//        }
        logout = findViewById(R.id.logout);
        carType = findViewById(R.id.carType);
        username = findViewById(R.id.username);
        location=findViewById(R.id.loc);
        rate=findViewById(R.id.price);
        plate=findViewById(R.id.noPlate);
        model=findViewById(R.id.models);
        add_car =findViewById(R.id.addcars);

        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(this, gOptions);

        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (gAccount != null){
            String gName = gAccount.getDisplayName();
//            userName.setText(gName);
        }
        logout.setOnClickListener(view -> gClient.signOut().addOnCompleteListener(task -> {
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }));
        add_car.setOnClickListener(view -> {
            String cartype = carType.getText().toString();
            String Username = username.getText().toString();
            String loc = location.getText().toString();
            String Rate = rate.getText().toString();
            String Plate = plate.getText().toString();
            String  Model = model.getText().toString();

            if(!cartype.isEmpty()&&!loc.isEmpty()&&!Rate.isEmpty()&&!Plate.isEmpty()&&!Model.isEmpty()){
                Map<String, Object> carInfo = new HashMap<>();
                carInfo.put("cartype", cartype);
                carInfo.put("location", loc);
                carInfo.put("rate", Rate);
                carInfo.put("plate", Plate);
                carInfo.put("model", Model);
//                Toast.makeText(MainActivity.this, carInfo, Toast.LENGTH_SHORT).show();
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Cars");
                reference.child(Username).setValue(carInfo).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Data added successfully
                        Log.d("Database", "Data added successfully");
                        carType.setText("");
                        username.setText("");
                        location.setText("");
                        rate.setText("");
                        plate.setText("");
                        model.setText("");
                        Toast.makeText(MainActivity.this, "You Have Added Your Car", Toast.LENGTH_SHORT).show();
                    } else {
                        // Error occurred
                        Log.e("Database", "Error adding data: " + task.getException());
                        Toast.makeText(MainActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else {
                carType.setError("Empty fields are not allowed");
                location.setError("Empty fields are not allowed");
                rate.setError("Empty fields are not allowed");
                plate.setError("Empty fields are not allowed");
                model.setError("Empty fields are not allowed");

            }


        });
    }

    private void initializeFirebase() {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyBENd2V65479IQxXngZ-G45XxmW4yUDhig")
                .setApplicationId("1:843309216359:android:7bc546019d1a1bb23b9ac2")
                .setProjectId("vehicle-management-967c9")
                .build();

        FirebaseApp.initializeApp(this, options);
        firebaseInitialized = true;

    }
}