package com.example.onlineparkingbookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Register extends AppCompatActivity {
    private EditText fullName;
    private EditText email;
    private EditText phone;

    private EditText address;
    private EditText password;
    private Button btn_register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.number);
        address= findViewById(R.id.address);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.loginButton);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
                startActivity(new Intent(Register.this,MainActivity.class));
                Log.d("data","Data sent");
            }
        });
    }
    public void sendData(){


        final String[] Token = new String[1];

        String url = "http://192.168.1.69:8080/rohit/user";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("fullName", fullName.getText().toString());
            jsonRequest.put("email", email.getText().toString());
            jsonRequest.put("mobile", Long.parseLong(phone.getText().toString()));
            jsonRequest.put("address",address.getText().toString());
            jsonRequest.put("password", password.getText().toString());
        } catch (JSONException e) {
            email.setText(e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Log.e("Response",response.toString());

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
}
