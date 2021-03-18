package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the profile of a user.
 */
public class DisplayProfileActivity extends AppCompatActivity implements CreateUserFragment.OnFragmentInteractionListener{
    Button back;
    Button edit;
    TextView title;
    TextView pt_contact;
    TextView realname;
    TextView phoneNum;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayprofile);
        title = findViewById(R.id.disp_title);
        pt_contact=findViewById(R.id.contactInfo_pt);
        realname=findViewById(R.id.realname_disp);
        phoneNum=findViewById(R.id.phone_disp);
        user=(User) getIntent().getSerializableExtra("user");
        title.setText(user.username);
        pt_contact.setText("Contact information");
        realname.setText(user.contactInfo.getName());
        phoneNum.setText(user.contactInfo.getPhoneNumber());
        back=findViewById(R.id.back_disp);
        edit=findViewById(R.id.edituser);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent = new Intent(DisplayProfileActivity.this, MainActivity.class);
                intent.putExtra("user",user);
                setResult(RESULT_OK,intent);
                finish();


            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // login
                new CreateUserFragment().show(getSupportFragmentManager(), "LOGIN");

            }


        });





}
    @Override
    public void onOkPressed(String phoneNum, String name) {
        user.contactInfo.setName(name);
        user.contactInfo.setPhoneNumber(phoneNum);
    }
}