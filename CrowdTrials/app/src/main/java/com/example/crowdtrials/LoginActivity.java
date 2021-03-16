package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {

    User user;
    FirebaseFirestore db;
    CollectionReference collectionReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        db = FirebaseFirestore.getInstance();// Access a Cloud Firestore instance from your Activity
        collectionReference= db.collection("Users");
        Button login = findViewById(R.id.loginbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // login
                new LoginFragment().show(getSupportFragmentManager(), "LOGIN");

            }
        });

    }


    @Override
    public void onOkPressed(String username) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("user",username);


        startActivityForResult(intent,1);

    }
}
