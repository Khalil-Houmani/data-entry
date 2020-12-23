package com.example.first_scratch;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.locationgetter.Bluetooth;
import com.example.locationgetter.awal_act;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView emailTextView;
    Button logOutBtn;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnbluetooth).setOnClickListener(e->{
            startActivity(new Intent(getApplicationContext(), Bluetooth.class));
        });

        logOutBtn = findViewById(R.id.logOutBtn);
        emailTextView = findViewById(R.id.emailTextView);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        CollectionReference whoIMet = firestore.collection("users").document(auth.getCurrentUser().getUid()).collection("whoiamet");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        emailTextView.setText(auth.getCurrentUser().getEmail());

        logOutBtn.setOnClickListener(v -> {

            auth.signOut();
            startActivity(new Intent(getApplicationContext(), awal_act.class));

        });


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // no permission , then get it
            getPermission();
            return;
        }





        findViewById(R.id.getLocationBtn).setOnClickListener(e -> {

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // String locationInStr = "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude();
                            // PUT CODE HERE
                            // location.getLatitude();
                            // location.getLongitude();
                            String locationInStr = "Latitude : "  + location.getLatitude() + ", Longitude:" + location.getLongitude();
                            Log.i("Location",locationInStr);


                            // writing to database



                            String name =((EditText)findViewById(R.id.edname)).getText().toString() ;
                            String phone= ((EditText)findViewById(R.id.edphone)).getText().toString();



                            whoIMet.document().set(new Person(name,phone,location.getLatitude(),location.getLongitude()))

                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            ;




                            // end writing
                            TextView locationTextView = findViewById(R.id.locationTextView);
                            locationTextView.setText(locationInStr);

                        }
                    });



        });

    }


    void getPermission() { // if no permission is granted get the permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { // Get permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }


    String getDate(){
        Date c = Calendar.getInstance().getTime();
       // System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    String getTime(){
        Date c = Calendar.getInstance().getTime();
        return c.toString();
    }

}
