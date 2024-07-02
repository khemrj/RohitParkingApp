package com.example.onlineparkingbookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
                GeoCodeLocation locationAddress = new GeoCodeLocation();
                locationAddress.getAddressFromLocation("Dhangadhi", getApplicationContext(), new GeoCoderHandler());
                Intent intent = new Intent(parkingNearMe.this, donorPage.class);
                startActivity(intent);
            }
        });

    }
    private class GeoCoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            SharedPreferences sharedPreferences = getSharedPreferences("Rohit", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String[] parts = locationAddress.split(" ");
            editor.putString("latitude11",parts[0]);
            editor.putString("longitude11",parts[1]);
            editor.apply();
            Log.d("Location1",locationAddress);

        }


    }
}