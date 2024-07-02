package com.example.onlineparkingbookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {
private Button registerParkingArea;
private Button findParkingNearMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        registerParkingArea = findViewById(R.id.btn_register);
        findParkingNearMe = findViewById(R.id.parkingNearMe);
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