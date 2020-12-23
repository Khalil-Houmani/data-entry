package com.example.first_scratch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verify extends AppCompatActivity {



    private  String verificationid;
    private FirebaseAuth mAthu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        mAthu= FirebaseAuth.getInstance();

        String phonenumber = getIntent().getStringExtra("phonenumber");

        sendVerificationCode(phonenumber);
        findViewById(R.id.sign_btn).setOnClickListener(v -> {


        });



    }
    private void verifycode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        SignInWithCredential(credential);
    }

    private void SignInWithCredential(PhoneAuthCredential credential) {

        mAthu.signInWithCredential(credential)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        Intent intent = new Intent()

                    }else
                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                });



    }

    private  void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                        verificationid=s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        String code =phoneAuthCredential.getSmsCode();

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(verify.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });        // OnVerificationStateChangedCallbacks

    }

}