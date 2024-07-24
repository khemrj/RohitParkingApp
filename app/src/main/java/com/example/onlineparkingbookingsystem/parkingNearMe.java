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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    private EditText location,to, from ;
    private String lat, lon;
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