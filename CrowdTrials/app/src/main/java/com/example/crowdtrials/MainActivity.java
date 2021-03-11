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

public class MainActivity extends AppCompatActivity {

    ListView experimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;
    Database database;
    FirebaseFirestore db;
    CollectionReference collectionReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database();

        experimentList = findViewById(R.id.experiment_list);
        experimentDataList = new ArrayList<>();
        experimentAdapter = new ExperimentList(this, experimentDataList);

        experimentList.setAdapter(experimentAdapter);
        experimentDataList.add(testExperimentCreation("John","58712342123","First Experiment Added to database"));
        writeToDatabase(experimentDataList.get(0));
        experimentDataList.add(testExperimentCreation("Jack","78012342123","Second Experiment Added to database"));
        writeToDatabase(experimentDataList.get(1));
        experimentDataList.add(testExperimentCreation("Adam","7801234566","Third Experiment Added to database"));
        writeToDatabase(experimentDataList.get(2));
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

        //writeToDatabase();
        // get user from intent
        String username = (String) getIntent().getSerializableExtra("user");
        // query database to see if username exists
        // query database with the passed in username
        Task<DocumentSnapshot> usersRef = collectionReference.document(username).get();
        User u = (User) usersRef.getResult().getData();
        if(u!=null){
            // get experiments
        }
        else{
            new CreateUserFragment().show(getSupportFragmentManager(), "CREATE_USER");
            // I need to finish this part
        }



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
}