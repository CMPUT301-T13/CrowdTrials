package com.example.crowdtrials;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

/**
 * The class handles the login activity of the application
 */
public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {

    User user;
    FirebaseFirestore db;
    CollectionReference collectionReference;
    SharedPreferences  sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sharedPreferences = this.getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String attemptToFindUser=sharedPreferences.getString("user",null);
        db = FirebaseFirestore.getInstance();// Access a Cloud Firestore instance from your Activity
        collectionReference= db.collection("Users");
        Button login = findViewById(R.id.loginbutton);
        boolean isThisTheFirstRun=sharedPreferences.getBoolean("firstRun",true);

        if (attemptToFindUser!=null && attemptToFindUser.length()!=0){
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("user",attemptToFindUser);
            startActivityForResult(intent,1);

            Log.e("user",attemptToFindUser);
            finish();
        }
        else{
            Random random = new Random();
            int num = random.nextInt(4000000 - 1) + 1; // create the user a username
            String username="nameless"+Integer.toString(num);
            editor.putString("user",username);
            editor.commit();
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("user",username);
            startActivityForResult(intent,1);
            finish();
        }
        //Log.d("Found User",attemptToFindUser);
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
        editor.putString("user",username);
        editor.commit();
        startActivityForResult(intent,1);

    }
}
