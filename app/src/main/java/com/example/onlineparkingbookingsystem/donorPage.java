package com.example.onlineparkingbookingsystem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
public class donorPage extends AppCompatActivity {
    ArrayList<ParkingPlaceModel> arrDonor=new ArrayList<>();
    ImageButton imageButton;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_page);


        RecyclerView recyclerView=findViewById(R.id.recyclerdonor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrDonor.add(new ParkingPlaceModel("Suman Thakur","Dhangadhi"));
        arrDonor.add(new ParkingPlaceModel("Suman Thakur","Dhangadhi"));
        arrDonor.add(new ParkingPlaceModel("Suman Thakur","Dhangadhi"));
        arrDonor.add(new ParkingPlaceModel("Suman Thakur","Dhangadhi"));
        arrDonor.add(new ParkingPlaceModel("Suman Thakur","Dhangadhi"));
        arrDonor.add(new ParkingPlaceModel("Suman Thakur","Dhangadhi"));

        RecyclerDonorAdapter adapter=new RecyclerDonorAdapter(this,arrDonor);
        recyclerView.setAdapter(adapter);

    }


}