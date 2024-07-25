package com.example.onlineparkingbookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText phone;
    EditText etpassword;
    Button loginButton;
    TextView signupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone = findViewById(R.id.number);
        etpassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupText= findViewById(R.id.signupText);


        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Register.class));

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringRequest();
            }
        });
    }

    public void stringRequest(){
        String url = "http://192.168.1.21:8080/rohit/user/k/"+ phone.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals ("null")){
                    Toast.makeText(getApplicationContext(),"This number does not exists",Toast.LENGTH_LONG).show();
                }
                else {
                    String[] parts = response.toString().split(" ");
                    String password = parts[0];
                    String id = parts[1];
                    String pass = parts[2];
                    SharedPreferences sharedPreferences = getSharedPreferences("url_prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userId",id);
                    editor.putString("password", pass);
                    editor.apply();

                    if(password.equals(etpassword.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,Dashboard.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        }
                ,new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("errorResponse", error.toString());
            }
        });
        queue.add(stringRequest);
    }


}
