package com.example.locationgetter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.first_scratch.R;
import com.example.first_scratch.countryname;
import com.example.first_scratch.verify;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup extends AppCompatActivity {


    //Declare an instance of FirebaseAuth.
    private FirebaseAuth mAuth;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore firestoredb = null;
private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialize firestore
        firestoredb = FirebaseFirestore.getInstance();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        CollectionReference profiles = firestoredb.collection("users");


        //for the spinner
        spinner = findViewById(R.id.spinnercountry);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                countryname.countrydata));
         EditText addphone = findViewById(R.id.eduphone);






//        // Write a message to the realtime database for name and phone number
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference nameReferences = database.getReference("signnames");
//        DatabaseReference phoneReferences = database.getReference("signphone");

// after pressing submit data will be sent to the database
        findViewById(R.id.btnsubmit).setOnClickListener(e->{

            String code = countryname.countrycode[spinner.getSelectedItemPosition()];
          String phonenumner=  addphone.getText().toString().trim();

          if(phonenumner.isEmpty() || phonenumner.length()<10){
              addphone.setError("number is required");
              addphone.requestFocus();
              return;
          }
          String phone = "+" + code + phonenumner;
          Intent intent = new Intent(getApplicationContext(), verify.class);
          intent.putExtra("phonenumber",phone );
          startActivity(intent);












            //for email amd password
            EditText email = findViewById(R.id.editText_email_sign_up);
            EditText password = findViewById(R.id.editText_password_sign_up);
            EditText addname = findViewById(R.id.eduname);


            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString());

            //User newuser = new User(addname);

            User newUser = new User(addname.getText().toString());


            profiles.document(mAuth.getCurrentUser().getUid().toString()).set(newUser)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()){
                            Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });


            //for the name and phone number
        // EditText addname = findViewById(R.id.eduname);
           // EditText addphone = findViewById(R.id.eduphone);

          //  nameReferences.child(nameReferences.push().getKey()).setValue(addname.getText().toString());
           // phoneReferences.child(phoneReferences.push().getKey()).setValue(addphone.getText().toString());


            startActivity(new Intent(getApplicationContext(), awal_act.class));
        });

// after pressing submit we will get back to the login activity

    }
}
