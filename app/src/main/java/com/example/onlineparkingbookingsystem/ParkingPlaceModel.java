package com.example.onlineparkingbookingsystem;


import android.widget.ImageButton;

public class ParkingPlaceModel {
    String acceptButtonText;

    String placeName;
    String location;
    boolean Bookbutton; //yo ta visible grna lagi ho

    public ParkingPlaceModel(String placeName,String location) {
        // Initialize other properties as before
        this.placeName = placeName;
        this.location=location;
        // this.acceptButtonVisible = true;// Set the button initially visible yo t button visible garna lagi ho but hamilai button cahiyo
// Initialize other properties as before


    }
    //yo hamro visibility ko lagi  ho
//    public boolean isAcceptButtonVisible() {
//        return acceptButtonVisible;
//    }
//    public void setAcceptButtonVisible(boolean acceptButtonVisible) {
//        this.acceptButtonVisible = acceptButtonVisible;
//    }


}


