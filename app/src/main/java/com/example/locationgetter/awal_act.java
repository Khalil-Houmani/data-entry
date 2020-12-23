package com.example.locationgetter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.first_scratch.MainActivity;
import com.example.first_scratch.R;
import com.google.firebase.auth.FirebaseAuth;

public class awal_act extends AppCompatActivity {

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awal_act);

        auth = FirebaseAuth.getInstance();

        findViewById(R.id.btnlogin).setOnClickListener(e->{
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
        findViewById(R.id.btnsignup).setOnClickListener(e->{
            startActivity(new Intent(getApplicationContext(), Signup.class));
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(getApplication(),MainActivity.class));
        }


    }
}
