package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements ResultsCallback{
    Database db;
    ListView reslist;
    ArrayList<ResultArr> resultArrArrayList;
    ArrayAdapter<ResultArr> resAdapter;
    Button back;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewdetact);
        Experiment exp;
        //db.getSingleExperiment();
        Log.e("working","not working.");
        reslist=findViewById(R.id.res_list);
        back = findViewById(R.id.backbuttondispres);
        type=(String) getIntent().getSerializableExtra("type");


        if(type.equals("meas")){
            exp = (MeasurementExp) getIntent().getSerializableExtra("exp");
            Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);
            resAdapter = new ResultList(this,exp.results);
            reslist.setAdapter(resAdapter);

        }
        else if(type.equals("bin")){
            exp = (BinomialExp) getIntent().getSerializableExtra("exp");
            Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);
            resAdapter = new ResultList(this,exp.results);
            reslist.setAdapter(resAdapter);
        }
        else if(type.equals("ncount")){
            exp = (NonNegativeCountExp) getIntent().getSerializableExtra("exp");
            Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);
            resAdapter = new ResultList(this,exp.results);
            reslist.setAdapter(resAdapter);
        }
        else{
            exp = (CountExp) getIntent().getSerializableExtra("exp");
            Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);
            resAdapter = new ResultList(this,exp.results);
            reslist.setAdapter(resAdapter);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent;
                if(type.equals("meas")){
                    intent = new Intent(DetailActivity.this, MeasurementActivity.class);

                }
                else if(type.equals("bin")){
                    intent = new Intent(DetailActivity.this, BinomialActivity.class);
                }
                else if(type.equals("ncount")){
                    intent = new Intent(DetailActivity.this, CountActivity.class);
                }
                else{
                    intent = new Intent(DetailActivity.this, NonNegativeCountActivity.class);
                }

                setResult(RESULT_OK,intent);
                finish();


            }
        });
        // want to have it setup so that clicking a result tells you who it is from and basic info about the result


    }
    public void onCallback(ArrayList<ResultArr> value, int whichCase){
        resultArrArrayList = value;
        for (ResultArr result :value){
            //Log.e("IN DETAIL ACTIVITY","" + result.experimenter.username );
        }
    }
}
