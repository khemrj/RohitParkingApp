package com.example.onlineparkingbookingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecyclerDonorAdapter extends RecyclerView.Adapter<RecyclerDonorAdapter.ViewHolder> {
    @NonNull
    Context context;
    String imageButton;//EX09april
    Button accept;
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
                    Toast.makeText(context.getApplicationContext(), "paisa tir paila", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
