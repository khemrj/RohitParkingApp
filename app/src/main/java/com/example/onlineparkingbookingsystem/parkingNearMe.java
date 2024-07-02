package com.example.onlineparkingbookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class parkingNearMe extends AppCompatActivity {
    private Spinner spinner;
    private EditText location,to, from ;

    Button button ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_near_me);
        spinner = findViewById(R.id.spinner);
        location = findViewById(R.id.location);
        to = findViewById(R.id.to);
        from = findViewById(R.id.from);
        button = findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parkingNearMe.this, donorPage.class);
                startActivity(intent);
            }
        });

    }
}