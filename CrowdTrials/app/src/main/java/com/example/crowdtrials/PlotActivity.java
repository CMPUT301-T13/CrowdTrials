package com.example.crowdtrials;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the plot activity for a experiment
 */
public class PlotActivity extends AppCompatActivity implements ResultsCallback{
    FirebaseFirestore db;
    CollectionReference collectionReference;
    String type;
    Experiment exp;
    BarChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plot_activity);
        //collectionReference = db.collection("Experiments");
        chart = (BarChart) findViewById(R.id.chart);
        type=(String) getIntent().getSerializableExtra("type");
        if(type.equals("Measurement Experiment")){
            exp = (MeasurementExp) getIntent().getSerializableExtra("exp");
        }
        else if(type.equals("Binomial Experiment")){
            exp = (BinomialExp) getIntent().getSerializableExtra("exp");
            Log.e("Results callback", "onCallback: " );

        }
        else if(type.equals("NonNegative Count Experiment")){
            exp = (NonNegativeCountExp) getIntent().getSerializableExtra("exp");

        }
        else{
            exp = (CountExp) getIntent().getSerializableExtra("exp");


        }
        //attachListener(this::onCallback);
        Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);

    }


    public void attachListener(ResultsCallback myCallback){
        collectionReference.document(exp.getName()).collection("Trials")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("Plot activity", "Listen failed.", e);
                            return;
                        }

                        Database.getSingleDatabaseInstance().getAllResults(exp,myCallback);
                };
                });


    }
    public void onCallback(ArrayList<ResultArr> value,Experiment exp, int whichCase){
        if(type.equals("Measurement Experiment")){

            ArrayList<Float> tempArray = new ArrayList<Float>();
            for (ResultArr result:value){
                FloatResult floatResult = (FloatResult)result;

                tempArray.addAll((floatResult).measurements);

            }

            List<BarEntry> entries = new ArrayList<>();

            final int[] result = new int[10];
            final double binSize = (Collections.min(tempArray) - Collections.max(tempArray))/10;

            for(Float x:tempArray){
                int bin = (int) ((x - Collections.min(tempArray)) / binSize);
                if (bin < 0) {
                    /* this data is smaller than min */ }
                else if (bin >= 10) {
                    /* this data point is bigger than max */ }
                else {
                    result[bin] += 1;
                }

            }

            for(int i=0; i<10; i++) {

                entries.add(new BarEntry(i+ 1,result[i] ));
            }

            BarDataSet set = new BarDataSet(entries, "BarDataSet");
            BarData data = new BarData(set);
            data.setBarWidth(0.9f); // set custom bar width
            chart.setData(data);
            chart.setFitBars(true); // make the x-axis fit exactly all bars
            chart.invalidate(); // refresh




        }else if (type.equals("Binomial Experiment")){
            ArrayList<Boolean> tempArray = new ArrayList<Boolean>();
            for (ResultArr result:value){
                BoolResult boolResult = (BoolResult)result;
                Log.e("Results callback", "onCallback: " + boolResult.outcomes );
                tempArray.addAll(boolResult.outcomes);

            }

            Log.e("HOW MANY ARE YOU!!"," " + tempArray.size());
            List<BarEntry> entries = new ArrayList<>();

            final int[] result = new int[2];
            for(int i = 0; i< tempArray.size(); i++){
                if(tempArray.get(i)){
                    result[1]+= 1;
                }else{
                    result[0] +=1;
                }
            }

            entries.add(new BarEntry(1,  result[0]));
            entries.add(new BarEntry(2,  result[1]));


            BarDataSet set = new BarDataSet(entries, "BarDataSet");
            BarData data = new BarData(set);
            data.setBarWidth(1f); // set custom bar width
            chart.setData(data);
            chart.setFitBars(true); // make the x-axis fit exactly all bars
            chart.invalidate(); // refresh


        }else{
            ArrayList<Integer> tempArray = new ArrayList<Integer>();
            ArrayList<?> array2;

            for (ResultArr result:value){
                IntResult intResult = (IntResult) result;

                if (intResult.values != null){
                    for(int i =0; i<intResult.values.size(); i++){
                        Number temp = (Number)intResult.values.get(i);
                        tempArray.add(temp.intValue());
                    }
                }


            }
            Log.e("Results callback", "after temparary: " + tempArray );

            List<BarEntry> entries = new ArrayList<>();

            final int[] result = new int[10];

            final double binSize = (double)(Collections.max(tempArray) - Collections.min(tempArray))/10;

            for( int i=0; i<tempArray.size();i++){


                int bin = (int) ((tempArray.get(i) - Collections.min(tempArray)) / binSize);

                if (bin < 0) {
                    /* this data is smaller than min */ }
                else if (bin > 10) {
                    /* this data point is bigger than max */ }
                else if(bin==10){
                    result[9] +=1;
                }
                else {

                    result[bin] += 1;
                }

            }

            for(int i=0; i<10; i++) {

                entries.add(new BarEntry(i+ 1,result[i] ));
            }

            BarDataSet set = new BarDataSet(entries, "BarDataSet");
            BarData data = new BarData(set);
            data.setBarWidth(0.9f); // set custom bar width
            chart.setData(data);
            chart.setFitBars(true); // make the x-axis fit exactly all bars
            chart.invalidate(); // refresh

        }
    }


}

