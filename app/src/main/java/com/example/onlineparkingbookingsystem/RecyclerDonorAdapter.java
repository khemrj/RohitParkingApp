package com.example.onlineparkingbookingsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class RecyclerDonorAdapter extends RecyclerView.Adapter<RecyclerDonorAdapter.ViewHolder> {
    @NonNull
    Context context;
    String imageButton;//EX09april
    Button accept;
    TimePicker from, to;

    ArrayList<ParkingPlaceModel> arrDonor;
    RecyclerDonorAdapter(@NonNull Context context, ArrayList<ParkingPlaceModel> arrDonor) {
        this.context = context;
        this.arrDonor = arrDonor;
    }
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.donor_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //here problem is showning don't treat position as fixed ;only use immedately and call 'holder.getAdapterPosition()' to look it up later in this fun public void onBindViewHolder(@NonNull ViewHolder holder, int position)
        int adapterPosition = holder.getAdapterPosition();
        holder.txtName.setText("PlaceName:"+arrDonor.get(position).placeName);
        holder.txtPrice.setText( arrDonor.get(position).distance+ " KM");
        holder.txtLocation.setText("Location: " + arrDonor.get(position).location);

    }

    @Override
    public int getItemCount() {
        return arrDonor.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtLocation,txtPrice;
        ImageView imgContact;

        Button acceptButton;
        ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgContact = itemView.findViewById(R.id.imageContact);
            txtName = itemView.findViewById(R.id.name);
            txtLocation = itemView.findViewById(R.id.location);
            txtPrice = itemView.findViewById(R.id.distance);
            accept = itemView.findViewById(R.id.acceptButton);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   // BookParkingPlace();
                }
            });
        }
    }
    public void BookParkingPlace(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("url_prefs", Context.MODE_PRIVATE);
       String from = sharedPreferences.getString("from",null);
       String to = sharedPreferences.getString("to",null);
        String url = "http://192.168.1.21:8080/rohit/bookPlace";
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("from", from);
            jsonRequest.put("to", to);
            jsonRequest.put("amount", );
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
}
