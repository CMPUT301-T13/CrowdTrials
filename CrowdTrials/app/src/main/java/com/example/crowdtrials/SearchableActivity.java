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

/**
 * This class represents the activity for the functionality of searching for an experiment
 */
public class SearchableActivity extends AppCompatActivity implements MyCallback,AddResult,SubscribeButtonImplementer {
    Database database;
    ListView experimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;
    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        experimentList = (ListView)findViewById(R.id.searched_List);
        experimentDataList = new ArrayList<>();
        experimentAdapter = new ExperimentList(this, experimentDataList);

        experimentList.setAdapter(experimentAdapter);
        //handleIntent(getIntent());
        Log.e("SEARCH","This was searched" + "Called in Oncreate");

        Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
        if (appData != null) {
            String name;
            String phoneNum;
            String username = appData.getString("username");
            user = new User(username);
            if(appData.getString("name") != null && appData.getString("phoneNum") != null){
                name = appData.getString("name");
                phoneNum = appData.getString("phoneNum");
                ContactInfo contactInfo = new ContactInfo( name,phoneNum);
                user.contactInfo = contactInfo;

            }

           Log.e("Searchable Activity",  "" + user.username);
        }




    }

    public void addResultPressed(Experiment exp,int pos){
        Log.d("My Activity", "get failed with " + exp.getDescription());
        if(exp instanceof BinomialExp) {
            Intent intent = new Intent(this, BinomialActivity.class);
            intent.putExtra("exp", exp);
            intent.putExtra("user", user);
            intent.putExtra("pos", pos);
            startActivityForResult(intent,2);
        }
        else if(exp instanceof MeasurementExp){
            Intent intent = new Intent(this, MeasurementActivity.class);
            intent.putExtra("exp", exp);
            intent.putExtra("user", user);
            intent.putExtra("pos", pos);
            startActivityForResult(intent,3);
        }
        else if(exp instanceof CountExp){
            Intent intent = new Intent(this, CountActivity.class);
            intent.putExtra("exp", exp);
            intent.putExtra("user", user);
            intent.putExtra("pos", pos);
            startActivityForResult(intent,4);
        }
        else if(exp instanceof NonNegativeCountExp){
            Intent intent = new Intent(this, CountActivity.class);
            intent.putExtra("exp", exp);
            intent.putExtra("user", user);
            intent.putExtra("pos", pos);
            startActivityForResult(intent,5);
        }

    }
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.e("SEARCH","This was searched" + "Called in handleIntent");
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
        experimentDataList.clear();
        for (Experiment experiment:value){
            experimentDataList.add(experiment);

        }
        experimentAdapter.notifyDataSetChanged();

    }

    public void subscribedButtonPressed(Experiment exp) {



        database.subscribeTo(exp,user);
    }
}