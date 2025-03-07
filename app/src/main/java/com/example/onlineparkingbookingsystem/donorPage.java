package com.example.onlineparkingbookingsystem;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class donorPage extends AppCompatActivity {
    private Double lat1,lon1;
    private String  from, to,catName;;
    Spinner spinner;
    ArrayList<ParkingPlaceModel> arrDonor=new ArrayList<>();
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_page);

        Intent intent = getIntent();
        if(intent!= null) {
            lat1 = Double.parseDouble(intent.getStringExtra("latitude"));
            lon1 = Double.parseDouble(intent.getStringExtra("longitude"));
            catName = intent.getStringExtra("catName");
        }
        else{
            Log.d("errorIntent","empty intent");
        }


  getNearestPlaces();



    }
    public void  getNearestPlaces(){
        Log.d("intentlocation","lat"+lat1+" "+lon1);
      //  SharedPreferences sharedPreferences = getSharedPreferences("Rohit", Context.MODE_PRIVATE);
//        String lat1 = sharedPreferences.getString("latitude11",null);
//        String lon1 = sharedPreferences.getString("longitude11",null);
        Log.d("lat1","lat"+lat1+" "+lon1);
        SharedPreferences sharedPreferences = getSharedPreferences("url_prefs", Context.MODE_PRIVATE);
        String catId = sharedPreferences.getString("catId",null);
        String URL ="http://192.168.1.21:8080/rohit/nearestPlace/"+lat1+"/"+lon1+"/"+ catId;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            // Get each JSONObject
                            try {
                                Log.d("parkingPlaceResponce",response.toString());
                                JSONObject jsonObject = response.getJSONObject(i);
                                RecyclerView recyclerView=findViewById(R.id.recyclerdonor);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                String price = jsonObject.getString("pricePerHour");
                                String distance = trimAfterDecimal(jsonObject.getString("distance"),2);
                                arrDonor.add(new ParkingPlaceModel(jsonObject.getString("placeName"),jsonObject.getString("address"),distance,price,jsonObject.getString("parkingPlaceId")));
                                RecyclerDonorAdapter adapter=new RecyclerDonorAdapter(getApplicationContext(),arrDonor);
                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        Log.d("responseParkingPlace", "Response: " + response.toString());
                        Toast.makeText(donorPage.this, response.toString(), Toast.LENGTH_LONG).show();
                        // Handle the JSON array response here
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

    public static String trimAfterDecimal(String str, int numDigitsAfterDecimal) {
        int decimalIndex = str.indexOf(".");

        if (decimalIndex == -1) {
            // No decimal point found, return original string
            return str;
        }

        // Calculate the end index for the substring
        int endIndex = decimalIndex + numDigitsAfterDecimal + 1;

        if (endIndex > str.length()) {
            // If endIndex exceeds string length, return original string
            return str;
        }

        return str.substring(0, endIndex);
    }




}