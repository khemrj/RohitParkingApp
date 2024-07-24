package com.example.onlineparkingbookingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class parkingNearMe extends AppCompatActivity {
    private Spinner spinner;
    private EditText location ;
    EditText et_to, et_from;
    String from, to,catName;
    private String lat, lon;
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_near_me);
        spinner = findViewById(R.id.spinner);
        String[] items = {"Bus", "Car", "Auto-rikshaw", "Bike","Train"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Step 4: Initialize the variable based on the selected item
                catName = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        location = findViewById(R.id.location);
        et_to = findViewById(R.id.to);

        et_from = findViewById(R.id.from);
        button = findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                to = et_to.getText().toString();
                from = et_from.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("url_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("to",to);
                editor.putString("from",from);
                editor.apply();
                GeoCodeLocation locationAddress = new GeoCodeLocation();
                Log.d("locationone"," m"+location.getText().toString());
                locationAddress.getAddressFromLocation(location.getText().toString(), getApplicationContext(), new GeoCoderHandler());
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
            String[] parts = locationAddress.split(" ");

            Intent intent = new Intent(parkingNearMe.this, donorPage.class);
            intent.putExtra("latitude", parts[0]);
            intent.putExtra("longitude",parts[1]);



            startActivity(intent);
            Log.d("Location1",locationAddress);

        }


    }


}