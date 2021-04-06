package com.example.crowdtrials;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    Database db;
    ListView reslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewdetact);
        Experiment exp;
        //db.getSingleExperiment();
        Log.e("working","not working.");
        reslist=findViewById(R.id.res_list);
        String type=(String) getIntent().getSerializableExtra("type");
        if(type.equals("meas")){
            exp = (MeasurementExp) getIntent().getSerializableExtra("exp");
            ArrayList<FloatResult> res = new ArrayList<>();
            for(int i=0;i<exp.results.size();i++){
                res.add((FloatResult) exp.results.get(i));
            }
            ArrayAdapter<FloatResult> resultAdapter = new ArrayAdapter<>(this,R.layout.displayresults,res);
            reslist.setAdapter(resultAdapter);

        }
        else if(type.equals("bin")){
            exp = (BinomialExp) getIntent().getSerializableExtra("exp");
            ArrayList<BoolResult> res = new ArrayList<>();
            Log.e("working","working!");
            Log.e("working",Integer.toString(exp.experimenters.size()));
            Log.e("BININFO#",Integer.toString(exp.results.size()));
            for(int i=0;i<exp.results.size();i++){
                res.add((BoolResult) exp.results.get(i));
            }
            Log.e("BININFO#",Integer.toString(res.size()));

            ArrayAdapter<BoolResult> resultAdapter = new ArrayAdapter<>(this,R.layout.displayresults,res);
            reslist.setAdapter(resultAdapter);
        }
        else if(type.equals("ncount")){
            exp = (NonNegativeCountExp) getIntent().getSerializableExtra("exp");
            ArrayList<IntResult> res = new ArrayList<>();
            for(int i=0;i<exp.results.size();i++){
                res.add((IntResult) exp.results.get(i));
            }
            ArrayAdapter<IntResult> resultAdapter = new ArrayAdapter<>(this,R.layout.displayresults,res);
            reslist.setAdapter(resultAdapter);
        }
        else{
            exp = (CountExp) getIntent().getSerializableExtra("exp");
            ArrayList<IntResult> res = new ArrayList<>();
            for(int i=0;i<exp.results.size();i++){
                res.add((IntResult) exp.results.get(i));
            }
            ArrayAdapter<IntResult> resultAdapter = new ArrayAdapter<>(this,R.layout.displayresults,res);
            reslist.setAdapter(resultAdapter);
        }

        // want to have it setup so that clicking a result tells you who it is from and basic info about the result


    }
}
