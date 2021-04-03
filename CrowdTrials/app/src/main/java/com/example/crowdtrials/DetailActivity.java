package com.example.crowdtrials;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayresults);
        String type=(String) getIntent().getSerializableExtra("type");
        if(type.equals("meas")){
            MeasurementExp exp = (MeasurementExp) getIntent().getSerializableExtra("exp");
        }
        if(type.equals("bin")){
            BinomialExp exp = (BinomialExp) getIntent().getSerializableExtra("exp");
        }
        if(type.equals("ncount")){
            NonNegativeCountExp exp = (NonNegativeCountExp) getIntent().getSerializableExtra("exp");
        }
        if(type.equals("count")){
            CountExp exp = (CountExp) getIntent().getSerializableExtra("exp");
        }

    }
}
