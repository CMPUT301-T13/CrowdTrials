package com.example.crowdtrials;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CreateUserFragment.OnFragmentInteractionListener, MyCallback {

    ListView experimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;
    Database database;
    FirebaseFirestore db;
    CollectionReference collectionReference;
    User user;
    String username;
    PagerAdapter pagerAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        database =  Database.getSingleDatabaseInstance();
        experimentDataList = new ArrayList<>();
        // get user from intent
        username = (String) getIntent().getSerializableExtra("user");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        // query database to see if username exists
        // query database with the passed in username

        /*
        Task<DocumentSnapshot> usersRef = collectionReference.document(username).get();
        user = (User) usersRef.getResult().getData();
        if(user!=null){
            // get experiments
        }
        else{
            new CreateUserFragment().show(getSupportFragmentManager(), "CREATE_USER");
            // I need to finish this part
        }

         */
        database.readUser(username,this::onCallback);


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabItem tabHome = findViewById(R.id.tab1);
        TabItem tabSubsriptions = findViewById(R.id.tab2);

        ViewPager viewPager = findViewById(R.id.ViewPager);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
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
        database.readAllExperiments(this::onCallback);
        database.readSubscribedExperiments(this::onCallback);


        /*
        experimentDataList.add(testExperimentCreation("John","58712342123","First Experiment Added to database","Experiment 1"));
        writeToDatabase(experimentDataList.get(0));
        experimentDataList.add(testExperimentCreation("Jack","78012342123","Second Experiment Added to database","Experiment 2"));
        writeToDatabase(experimentDataList.get(1));
        experimentDataList.add(testExperimentCreation("Adam","7801234566","Third Experiment Added to database","Experiment 3"));
        writeToDatabase(experimentDataList.get(2));

        */



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        TextView textView = (TextView)menu.findItem(R.id.profileName);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfileActivity();


            }
        });

        return true;
    }
    public void goToProfileActivity() {
        //This is where you start the Profile activity
    }


    public void onCallback(ArrayList<Experiment> value,int whichCase) {
        Log.e("My Actvitiy", "I've been called" + whichCase);
        switch (whichCase){
            case 0:
                Log.d("My Actvitiy", "get failed with 0" + value);
                pagerAdapter.homeFragment.getList(value);
                break;
            case 1:
                Log.d("My Actvitiy", "get failed with 1" + value);
                pagerAdapter.subscriptionsFragment.getList(value);
                break;
            default:
                //do nothing
        }


    }

    public void writeToDatabase(Experiment experiment){
        database.writeExperiments(experiment);
    }


    public Experiment testExperimentCreation(String contactName, String phoneNumber,String description,String experimentName) {
        ContactInfo contactInfo = new ContactInfo(contactName,phoneNumber);
        User owner = new User("randomUserName",contactInfo);
        Date date = new Date();
        Location newRegion = new Location("");

        Experiment newExperiment = new BinomialExp(owner,newRegion,description,date,1,experimentName);
        return newExperiment;

    }
    @Override
    public void onOkPressed(String phoneNum,String name) {
        user = new User(username,new ContactInfo(name,phoneNum));
    }

    public void subscribedButtonPressed(Experiment experiment) {
        Log.d("My Actvitiy", "get failed with " + experiment.getDescription());
        database.subscribeTo(experiment);

    }
}