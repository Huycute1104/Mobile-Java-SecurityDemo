package com.example.security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText fullname, email, password, phone;
    Button registerBtn;
    TextView tologin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullname = findViewById(R.id.fullName);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        registerBtn = findViewById(R.id.registerBtn);
        tologin = findViewById(R.id.toLogin);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            finish();
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getText().toString().trim();
                String p = password.getText().toString().trim();
                String fn = fullname.getText().toString();
                String ph = phone.getText().toString();

                if(TextUtils.isEmpty(e)){
                    email.setError("email is required");
                    return;
                }

                if(TextUtils.isEmpty(p)){
                    password.setError("password is required");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(e, p)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

//                                    FirebaseUser firebaseUser = fAuth.getCurrentUser();
//                                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            Toast.makeText(RegisterActivity.this, "verification mail has been sent", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d("fail sending verification mail", "error: " + e.getMessage());
//                                        }
//                                    });

                                    Toast.makeText(RegisterActivity.this, "user created", Toast.LENGTH_SHORT).show();
                                    userID = fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fStore.collection("users").document(userID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("fname", fn);
                                    user.put("email", e);
                                    user.put("phone", ph);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d("success adding user to fire store", "user profile is created for " + userID);
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, "error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.e("error with firebase: ", task.getException().toString());
                                }
                            }
                        });
            }
        });
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}