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
        // query database with the passed in username
        Task<DocumentSnapshot> usersRef = collectionReference.document(username).get();
        User u = (User) usersRef.getResult().getData();
        // query database if we found a user put that user in the intent push him to main
        // also push in if we have an existing user because if the user didn't exist a dialog asking basic info needs to pop up
        if(u!=null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("user",user);
            intent.putExtra("exists",true);
            startActivityForResult(intent,1);
        }
        else{
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("user", new User(username,new ContactInfo("nameless","no-number")) {
            });
            intent.putExtra("exists",false);
            startActivityForResult(intent,1);
        }




    }
}
