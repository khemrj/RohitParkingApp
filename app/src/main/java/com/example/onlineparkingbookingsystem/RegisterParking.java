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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterParking extends AppCompatActivity {
    private Button registerParkingArea;
    String parkingPlaceId;
    private Double lat1, lon1;
    private EditText ownerName, address, phoneNo, parkingPlaceName;
    private LinearLayout container;
    List<Integer> vehicleId = new ArrayList<>();
    private ArrayList<EditText> editTextList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_parking);

        container = findViewById(R.id.LinearLayout);
       getVehicleList();










        SharedPreferences sharedPreferences = getSharedPreferences("Rohit", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        registerParkingArea = findViewById(R.id.registerParkingArea);
        ownerName = findViewById(R.id.ownerName);
        address = findViewById(R.id.parkingAddress);
        phoneNo = findViewById(R.id.phone);
        parkingPlaceName = findViewById(R.id.parkingName);

        registerParkingArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeoCodeLocation locationAddress = new GeoCodeLocation();
                locationAddress.getAddressFromLocation(address.getText().toString(), getApplicationContext(), new GeoCoderHandler());
//                for( int i = 0; i<editTextList.size();i++){
//                    EditText editText = editTextList.get(i);// for debugging
//                    String text = editText.getText().toString();
//                    Toast.makeText(RegisterParking.this,text, Toast.LENGTH_SHORT).show();
//                }
                sendData();


            }
        });
    }

    public void sendData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Rohit", Context.MODE_PRIVATE);
        String latitude = sharedPreferences.getString("latitude", null);
        String longitude = sharedPreferences.getString("longitude", null);

        String url = "http://192.168.1.69:8080/rohit/place";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("ownerName", ownerName.getText().toString());
            jsonRequest.put("placeName", parkingPlaceName.getText().toString());
            jsonRequest.put("phoneNumber", phoneNo.getText().toString());
            jsonRequest.put("address", address.getText().toString());
            jsonRequest.put("latitude", latitude);
            jsonRequest.put("longitude", longitude);
        } catch (JSONException e) {
            Log.d("parkingArea", e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(RegisterParking.this, "Registered", Toast.LENGTH_SHORT).show();
                    Log.v("Response", response.toString());
                    parkingPlaceId = response.getString("parkingPlaceId");
                    savePrice();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
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
            editor.putString("latitude", parts[0]);
            editor.putString("longitude", parts[1]);
            editor.apply();
            Log.d("Location1", locationAddress);
        }
    }

    public void getVehicleList() {
        String URL = "http://192.168.1.69:8080/rohit/category";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("khem", "hey" + response.toString());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                EditText editText = new EditText(getApplicationContext());
                                vehicleId.add(Integer.parseInt(jsonObject.getString("categoryId")));
                                editText.setHint("Enter price for "+ jsonObject.getString("name"));
                                editTextList.add(editText);
                                container.addView(editText);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        // Update the UI after fetching the vehicle names

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("errorResponse", "Error: " + error.toString());
                        // Handle error here
                    }
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }
public void savePrice(){
    String url = "http://192.168.1.69:8080/rohit/parkingPricesave";
    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


    JSONArray jsonArray = new JSONArray();
    for( int i = 0; i<vehicleId.size();i++){
        try {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("CategoryId", vehicleId.get(i));
            jsonRequest.put("pricePerHrs", editTextList.get(i).getText());
            Log.d("parkingplaceId",parkingPlaceId);
            jsonRequest.put("parkingPlaceId",parkingPlaceId);
            jsonArray.put(jsonRequest);
        } catch (JSONException e) {
            Log.d("parkingArea", e.toString());
        }
    }

    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            try {
                Toast.makeText(RegisterParking.this, "price saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterParking.this, Dashboard.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("VolleyError", error.toString());
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    });
        Log.d("Array sent is ", jsonArray.toString());
    requestQueue.add(jsonArrayRequest);

}

}
