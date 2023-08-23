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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView username, carType, location, rate, plate, model;
    Button logout, add_car;
    GoogleSignInClient gClient;
    DatabaseReference reference;
    GoogleSignInOptions gOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        carType = findViewById(R.id.carType);
        username = findViewById(R.id.username);
        location = findViewById(R.id.loc);
        rate = findViewById(R.id.price);
        plate = findViewById(R.id.noPlate);
        model = findViewById(R.id.models);
        add_car = findViewById(R.id.addcars);

        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(this, gOptions);

        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (gAccount != null){
            String gName = gAccount.getDisplayName();
            // userName.setText(gName);
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
            String Model = model.getText().toString();

            if(!cartype.isEmpty() && !loc.isEmpty() && !Rate.isEmpty() && !Plate.isEmpty() && !Model.isEmpty()) {
                // Get a reference to the "Cars" node in the database
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                reference = db.getReference("Cars");

                // Create a map to hold the car information
                Map<String, Object> carInfo = new HashMap<>();
                carInfo.put("cartype", cartype);
                carInfo.put("location", loc);
                carInfo.put("rate", Rate);
                carInfo.put("plate", Plate);
                carInfo.put("model", Model);

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
                        Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                // Handle empty fields
                Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });
    }
}