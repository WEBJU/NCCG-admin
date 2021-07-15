package com.example.smartparkadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.spec.ECField;
import java.util.List;
import java.util.Locale;

import models.ParkingDetails;

public class AddParkingDetailsActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference parking_ref;
    Geocoder geocoder;
    List<Address> coordinates;
    EditText etLocation,etCost,etSecurity,etHours,etOther;
    String location,cost,security,hours,other;
    double  lat,lon;
    Button btnComplete,btnBack;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking_details);
        database=FirebaseDatabase.getInstance();
        etLocation=findViewById(R.id.edtLocation);
        etCost=findViewById(R.id.edtCost);
        etSecurity=findViewById(R.id.edtSecurity);
        etHours=findViewById(R.id.hours);
        etOther=findViewById(R.id.other);

        geocoder=new Geocoder(AddParkingDetailsActivity.this,Locale.getDefault());
//        parking_ref=database.getReference("parking");
        btnBack=findViewById(R.id.btn_back);
        btnComplete=findViewById(R.id.btnComplete);
        progressBar=findViewById(R.id.progress);

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addParking(lat, lon,cost,security,hours,other);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddParkingDetailsActivity.this,MapsActivity.class));
            }
        });
    }

    private void addParking(double lat, double lon, String cost, String security, String hours, String other) {
        parking_ref=database.getReference();
        location=etLocation.getText().toString().trim();
        cost=etCost.getText().toString();
        security=etSecurity.getText().toString().trim();
        hours=etHours.getText().toString().trim();
        other=etOther.getText().toString().trim();

        if (TextUtils.isEmpty(location) || TextUtils.isEmpty(cost) || TextUtils.isEmpty(security) || TextUtils.isEmpty(hours) || TextUtils.isEmpty(other)){
            Toast.makeText(this, "Please fill all the information!!", Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.VISIBLE);

        try{

            coordinates=geocoder.getFromLocationName(location,1);
            if (coordinates != null){
                if (coordinates.size() > 0){
                    lat=coordinates.get(0).getLatitude();
                    lon=coordinates.get(0).getLongitude();

                    ParkingDetails details=new ParkingDetails(lat,lon,cost,security,hours,other);
                    parking_ref.child("parking_lot").child(location).push().setValue(details).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(AddParkingDetailsActivity.this, "New parking lot added successfully!!", Toast.LENGTH_SHORT).show();
                                etLocation.setText("");
                                etCost.setText("");
                                etHours.setText("");
                                etOther.setText("");
                                etSecurity.setText("");
                                return;
                            }else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(AddParkingDetailsActivity.this, "Operation failed!!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(AddParkingDetailsActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            return;
                        }
                    });
                }
            }else{
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void addParking(String location, String cost, String security, String hours, String other) {

    }


}