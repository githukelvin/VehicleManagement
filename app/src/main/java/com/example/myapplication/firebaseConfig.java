package com.example.myapplication;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class firebaseConfig  {
    public void start(MainActivity mainActivity){
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey("AIzaSyBENd2V65479IQxXngZ-G45XxmW4yUDhig")
                .setApplicationId("1:843309216359:android:7bc546019d1a1bb23b9ac2")
                .setProjectId("vehicle-management-967c9")
                .build();

        FirebaseApp.initializeApp(mainActivity, options, "myapplication");
    }

}
