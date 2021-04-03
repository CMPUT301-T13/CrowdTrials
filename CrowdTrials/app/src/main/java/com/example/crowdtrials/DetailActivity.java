package com.example.crowdtrials;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    Database db;
    ListView reslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewdetact);
        Experiment exp = null;
        String type=(String) getIntent().getSerializableExtra("type");
        if(type.equals("meas")){
            exp = (MeasurementExp) getIntent().getSerializableExtra("exp");
        }
        else if(type.equals("bin")){
            exp = (BinomialExp) getIntent().getSerializableExtra("exp");
        }
        else if(type.equals("ncount")){
            exp = (NonNegativeCountExp) getIntent().getSerializableExtra("exp");
        }
        else if (type.equals("count")){
            exp = (CountExp) getIntent().getSerializableExtra("exp");
        }
        ArrayAdapter<ResultArr> resultAdapter = new ArrayAdapter<>(this,R.layout.displayresults,exp.results);
        reslist.setAdapter(resultAdapter);


    }
}
