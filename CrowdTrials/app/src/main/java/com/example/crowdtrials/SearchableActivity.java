package com.example.crowdtrials;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchableActivity extends AppCompatActivity implements MyCallback{
    Database database;
    ListView experimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        experimentList = (ListView)findViewById(R.id.searched_List);
        experimentDataList = new ArrayList<>();
        experimentAdapter = new ExperimentList(this, experimentDataList);

        experimentList.setAdapter(experimentAdapter);
        handleIntent(getIntent());



    }
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchByName(query);
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    public void  searchByName(String query) {
        database = Database.getSingleDatabaseInstance();
        database.searchExperiments(this::onCallback, query);
        Log.e("SEARCH","This was searched" + query);
    }

    public void onCallback(ArrayList<Experiment> value, int whichCase) {

        for (Experiment experiment:value){
            experimentDataList.add(experiment);
        }
        experimentAdapter.notifyDataSetChanged();

    }
}