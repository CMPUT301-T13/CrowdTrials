package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * This class represents the activity used for statistics of an experiment
 */
public class StatsActivity extends AppCompatActivity implements ResultsCallback{
    String type;
    Database database;
    Experiment exp;
    Button back;
    TextView median;
    TextView mean;
    TextView standardDeviation;
    TextView Q1;
    TextView Q3;

    ArrayList<ResultArr> resultArrArrayList;
    Button plotButton;
    Statistics statistics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);

        back=findViewById(R.id.backbuttonstats);
        median=findViewById(R.id.medianTextView);
        mean=findViewById(R.id.meantextView);
        Q1=findViewById(R.id.textView4);
        Q3= findViewById(R.id.textView5);
        plotButton=findViewById(R.id.plotButton);
        type=(String) getIntent().getSerializableExtra("type");
        standardDeviation= findViewById(R.id.sdTextView);

        statistics = new Statistics();
        if(type.equals("meas")){
            exp = (MeasurementExp) getIntent().getSerializableExtra("exp");
            Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);



        }
        else if(type.equals("bin")){
            exp = (BinomialExp) getIntent().getSerializableExtra("exp");
            Log.e("Results callback", "onCallback: " );
            Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);

        }
        else if(type.equals("ncount")){
            exp = (NonNegativeCountExp) getIntent().getSerializableExtra("exp");
            Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);

        }
        else{
            exp = (CountExp) getIntent().getSerializableExtra("exp");
            Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);

        }
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        plotButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, PlotActivity.class);
                intent.putExtra("exp",exp);
                intent.putExtra("type",exp.type);
                startActivity(intent);
            }

        });
    }

    public void onCallback(ArrayList<ResultArr> value,Experiment exp, int whichCase){
        resultArrArrayList = value;
        float tempMean;
        float tempMedian;
        Boolean tempBool;
        float sd;
        Log.e("Results callback", "onCallback: " );

        if(type.equals("meas")){

            ArrayList<Float> tempArray = new ArrayList<Float>();
            for (ResultArr result:value){
                FloatResult floatResult = (FloatResult)result;

                tempArray.addAll((floatResult).measurements);

            }
            tempMean = statistics.getfloatMean(tempArray);


            tempMedian = statistics.getfloatMedian(tempArray);
            sd = statistics.getStandardDeviation(tempArray,tempMean);

            mean.setText("The mean is: " + tempMean);
            median.setText("The median is: "+ tempMedian);
            standardDeviation.setText("The standard Deviation is: " + sd);

        }else if (type.equals("bin")){
            ArrayList<Boolean> tempArray = new ArrayList<Boolean>();
            for (ResultArr result:value){
                BoolResult boolResult = (BoolResult)result;
                Log.e("Results callback", "onCallback: " + boolResult.outcomes );
                tempArray.addAll(boolResult.outcomes);

            }
            Log.e("Results callback", "onCallback: " + tempArray);
            tempMean = statistics.getBinomialMean(tempArray);
            tempBool = statistics.getMedian(tempArray);
            sd = statistics.getbooleanStandardDeviation(tempArray,tempMean);

            mean.setText("The mean is: " + tempMean);
            median.setText("The median is: "+ tempBool);
            standardDeviation.setText("The standard Deviation is: " + sd);


        }else{
            ArrayList<Integer> tempArray = new ArrayList<Integer>();
            for (ResultArr result:value){
                IntResult intResult = (IntResult) result;
                Log.e("Results callback", "onCallback: " + intResult.values );
                if (intResult.values != null){
                    tempArray.addAll(intResult.values);
                }


            }
            Log.e("Results callback", "onCallback: " + tempArray);
            tempMean = statistics.getIntegerMean(tempArray);
            tempMedian = statistics.getIntegerMedian(tempArray);
            sd = statistics.getIntegerStandardDeviation(tempArray,tempMean);

            mean.setText("The mean is: " + tempMean);
            median.setText("The median is: "+ tempMedian);
            standardDeviation.setText("The standard Deviation is: " + sd);

            Log.e("Results callback", "onCallback: " );
        }
    }


}