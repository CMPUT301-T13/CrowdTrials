package com.example.crowdtrials;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity  {

    ListView experimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;
    Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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




        /*
        experimentDataList.add(testExperimentCreation("John","58712342123","First Experiment Added to database"));
        writeToDatabase(experimentDataList.get(0));
        experimentDataList.add(testExperimentCreation("Jack","78012342123","Second Experiment Added to database"));
        writeToDatabase(experimentDataList.get(1));
        experimentDataList.add(testExperimentCreation("Adam","7801234566","Third Experiment Added to database"));
        writeToDatabase(experimentDataList.get(2));
        experimentAdapter.notifyDataSetChanged();
        */

        //database.readExperiments(this::onCallback);



    }


}