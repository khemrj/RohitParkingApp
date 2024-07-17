package com.example.onlineparkingbookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Dashboard extends AppCompatActivity {
private Button registerParkingArea;
private Button findParkingNearMe;
ImageButton profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        registerParkingArea = findViewById(R.id.btn_register);
        findParkingNearMe = findViewById(R.id.parkingNearMe);
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,UserProfile.class);
                startActivity(intent);
            }
        });
        findParkingNearMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,parkingNearMe.class);
                startActivity(intent);
            }
        });
        registerParkingArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,RegisterParking.class);
                startActivity(intent);
            }
        });


    }
}