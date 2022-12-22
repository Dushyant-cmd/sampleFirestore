package com.example.firestoresample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //1st dummy commit

    //Firebase Firestore product shared instance global variable
    FirebaseFirestore firebaseFirestore;
    String tag;
    EditText username;
    EditText email;
    EditText password;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseFirestore = FirebaseFirestore.getInstance();//Creating FirebaseFirestore
        //database instance
        username = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_buton);
        tag = "MainActivity.java";
        //as;lfsd

        //It hide the ActionBar and status bar from MainActivity layout window
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);


                Toast.makeText(MainActivity.this, "Data Saved" + formattedDate, Toast.LENGTH_LONG).show();
                //Create HashMap contains user details
                HashMap<String, String> user = new HashMap<>();
                user.put("username", username.getText().toString());
                user.put("emailId", email.getText().toString());
                user.put("password", password.getText().toString());
                user.put("date", formattedDate + "");
                //now add data in collection then add document with user(HashMap) fields then call
                //addOnSuccessListener() method and pass OnSuccessListener<DocumentReference> interface
                //Anonymous class declaration and in implementation Override and implement onSuccess()
                //method and import DocumentReference class object which is the reference of document
                //in which we add fields on Firebase Cloud Firestore database and reference contains
                //document id and much more.
                firebaseFirestore.collection("user").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.v(tag, "" + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(tag, e.getMessage() + "");
                    }
                });
            }
        });
    }
}