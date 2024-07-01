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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterParking extends AppCompatActivity {
    private Button registerParkingArea;
    private EditText ownerName,address,phoneNo,parkingPlaceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_parking);
        registerParkingArea = findViewById(R.id.registerParkingArea);
        ownerName = findViewById(R.id.ownerName);
        address = findViewById(R.id.parkingAddress);
        phoneNo = findViewById(R.id.phone);
        parkingPlaceName = findViewById(R.id.parkingName);

        registerParkingArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             sendData();

            }
        });
    }
    public void sendData(){


        final String[] Token = new String[1];

        String url = "http://192.168.1.75:8080/rohit/place";
        GeoCodeLocation locationAddress = new GeoCodeLocation();
        locationAddress.getAddressFromLocation(address.getText().toString(), getApplicationContext(), new GeoCoderHandler());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("Rohit", Context.MODE_PRIVATE);
        String latitude =sharedPreferences.getString("latitude",null);
        String longitude =sharedPreferences.getString("longitude",null);
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("ownerName",ownerName.getText().toString());
            jsonRequest.put("placeName",parkingPlaceName.getText().toString());
            jsonRequest.put("phoneNumber",phoneNo.getText().toString());
            jsonRequest.put("address",address.getText().toString());
            jsonRequest.put("latitude",latitude );
            jsonRequest.put("longitude",longitude);
        } catch (JSONException e) {
            Log.d("parkingArea",e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(RegisterParking.this, "Registered", Toast.LENGTH_SHORT).show();
                    Log.v("Response",response.toString());
                    Intent intent = new Intent(RegisterParking.this,Dashboard.class);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError",error.toString());

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);




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
            editor.putString("latitude",parts[0]);
            editor.putString("longitude",parts[1]);
            editor.apply();


            Log.d("Location1",locationAddress);

        }


    }
}