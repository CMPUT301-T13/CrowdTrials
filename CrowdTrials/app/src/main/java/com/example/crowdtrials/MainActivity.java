package com.example.crowdtrials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CreateUserFragment.OnFragmentInteractionListener {

    ListView experimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;
    Database database;
    FirebaseFirestore db;
    CollectionReference collectionReference;
    User user;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database();

        experimentList = findViewById(R.id.experiment_list);
        experimentDataList = new ArrayList<>();
        experimentAdapter = new ExperimentList(this, experimentDataList);
        experimentList.setAdapter(experimentAdapter);

        //writeToDatabase();
        // get user from intent
        username = (String) getIntent().getSerializableExtra("user");
        // query database to see if username exists
        // query database with the passed in username
        Task<DocumentSnapshot> usersRef = collectionReference.document(username).get();
        user = (User) usersRef.getResult().getData();
        if(user!=null){
            // get experiments
        }
        else{
            new CreateUserFragment().show(getSupportFragmentManager(), "CREATE_USER");
            // I need to finish this part
        }

        experimentAdapter.notifyDataSetChanged();

        FirebaseApp.initializeApp(this);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabItem tabHome = findViewById(R.id.tab1);
        TabItem tabSubsriptions = findViewById(R.id.tab2);

        ViewPager viewPager = findViewById(R.id.ViewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





    }
    public void writeToDatabase(Experiment experiment){
        database.writeExperiments(experiment);
    }


    Experiment testExperimentCreation(String name, String phoneNumber,String description) {
        ContactInfo contactInfo = new ContactInfo(name,phoneNumber);
        User owner = new User("randomUserName",contactInfo);
        Date date = new Date();
        Location newRegion = new Location("");
        Experiment newExperiment = new BinomialExp(owner,newRegion,description,date,1);
        return newExperiment;
    }
    @Override
    public void onOkPressed(String phoneNum,String name) {
        user = new User(username,new ContactInfo(name,phoneNum));
    }
}