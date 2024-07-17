package com.example.onlineparkingbookingsystem;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class donorPage extends AppCompatActivity {
    private Double lat1,lon1;
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
        }
        else{
            Log.d("errorIntent","empty intent");
        }

//        SharedPreferences sharedPreferences = getSharedPreferences("Rohit", Context.MODE_PRIVATE);
//        String address = sharedPreferences.getString("address",null);
       // Log.d("sharedAddress","  "+address);

        getNearestPlaces();
//        RecyclerView recyclerView=findViewById(R.id.recyclerdonor);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        arrDonor.add(new ParkingPlaceModel("Suman Thakur","Dhangadhi"));
//        arrDonor.add(new ParkingPlaceModel("Suman Thakur","Dhangadhi"));
//        arrDonor.add(new ParkingPlaceModel("Suman Thakur","Dhangadhi"));
//        arrDonor.add(new ParkingPlaceModel("Suman Thakur","Dhangadhi"));
//        arrDonor.add(new ParkingPlaceModel("Suman Thakur","Dhangadhi"));
//        arrDonor.add(new ParkingPlaceModel("Suman Thakur","Dhangadhi"));
//
//        RecyclerDonorAdapter adapter=new RecyclerDonorAdapter(this,arrDonor);
//        recyclerView.setAdapter(adapter);


    }
    public void  getNearestPlaces(){
        Log.d("intentlocation","lat"+lat1+" "+lon1);
      //  SharedPreferences sharedPreferences = getSharedPreferences("Rohit", Context.MODE_PRIVATE);
//        String lat1 = sharedPreferences.getString("latitude11",null);
//        String lon1 = sharedPreferences.getString("longitude11",null);
        Log.d("lat1","lat"+lat1+" "+lon1);
        String URL ="http://192.168.1.69:8080/rohit/nearestPlace/"+lat1+"/"+lon1;
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

                                arrDonor.add(new ParkingPlaceModel(jsonObject.getString("placeName"),jsonObject.getString("address"),"2"));
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




}