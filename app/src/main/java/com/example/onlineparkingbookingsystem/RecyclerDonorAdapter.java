package com.example.onlineparkingbookingsystem;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecyclerDonorAdapter extends RecyclerView.Adapter<RecyclerDonorAdapter.ViewHolder> {
    @NonNull
    Context context;
    String imageButton;//EX09april
    Button call;
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
        holder.txtName.setText(arrDonor.get(position).placeName);
        holder.txtPrice.setText(arrDonor.get(position).placeName);
        holder.txtLocation.setText(arrDonor.get(position).location);

        // Bind other data as before

        // Set the visibility of the button based on the model's property
        //---yo condition hamro button visible garauna lagi ho
//        if (arrDonor.get(position).isAcceptButtonVisible()) {
//            holder.acceptButton.setVisibility(View.VISIBLE);
//        } else {
//            holder.acceptButton.setVisibility(View.GONE);
//        }



        // Set the text of the button based on the model's property
//        holder.acceptButton.setText(arrDonor.get(position).getAcceptButtonText());
//        if ("Accepted".equals(arrDonor.get(adapterPosition).getAcceptButtonText())) {
//            // Change the background color when "Accepted" is displayed
//            //ContextCompat mean
//            holder.acceptButton.setBackgroundColor(ContextCompat.getColor(context, R.color.orange));
//        } else {
//            // Set the default background color for other cases
//            holder.acceptButton.setBackgroundColor(ContextCompat.getColor(context, com.google.android.material.R.color.design_default_color_primary));
//        }

        // Handle the "Accept" button click

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
            txtPrice = itemView.findViewById(R.id.price);


            // Set OnClickListener for the ImageButton
        }
    }
}
