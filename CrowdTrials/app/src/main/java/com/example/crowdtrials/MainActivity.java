package com.example.crowdtrials;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView experimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;
    Database database;

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

        writeToDatabase();



    }
    public void writeToDatabase(Experiment experiment){
        database.writeExperiments(experiment);
    }


    Experiment testExperimentCreation(String name, String phoneNumber,String description) {
        ContactInfo contactInfo = new ContactInfo(name,phoneNumber);
        Owner owner = new Owner("randomUserName",contactInfo);
        Date date = new Date();
        Location newRegion = new Location("");
        Experiment newExperiment = new BinomialExp(owner,newRegion,description,date,1);
        return newExperiment;
    }
}