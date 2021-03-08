package com.example.crowdtrials;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {
    Database database;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    }
}
