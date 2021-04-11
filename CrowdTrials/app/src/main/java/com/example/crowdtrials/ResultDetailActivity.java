package com.example.crowdtrials;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/* REFERENCES
 * AndroidMad / Mushtaq M A, 2016,  MIT License, https://github.com/androidmads/QRGenerator
 */

/**
 * This class represents the activity to view a result for a certain experiment
 */
public class ResultDetailActivity extends AppCompatActivity {
    Button back;
    Experiment exp;
    User user;
    ImageView qr;
    String type;
    ListView outcomesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultdetails);
        outcomesList = findViewById(R.id.outcome_list);
        exp = (Experiment) getIntent().getSerializableExtra("exp");
        user = (User) getIntent().getSerializableExtra("user");
        type = (String) getIntent().getSerializableExtra("typeres");
        back = findViewById(R.id.backbuttondispoutcomes);
        qr = findViewById(R.id.qrView);
        qr.setVisibility(View.INVISIBLE);
        if(type.equals("int")){
            IntResult res = (IntResult) getIntent().getSerializableExtra("res");
            ArrayAdapter<Integer> outcomes = new ArrayAdapter<Integer>(this,R.layout.outcomeslistview,R.id.outcome_text,res.values);
            outcomesList.setAdapter(outcomes);
        }
        else if(type.equals("float")){
            FloatResult res = (FloatResult) getIntent().getSerializableExtra("res");
            ArrayAdapter<Float> outcomes = new ArrayAdapter<Float>(this,R.layout.outcomeslistview,R.id.outcome_text,res.measurements);
            outcomesList.setAdapter(outcomes);
        }
        else{
            BoolResult res = (BoolResult) getIntent().getSerializableExtra("res");
            ArrayAdapter<Boolean> outcomes = new ArrayAdapter<Boolean>(this,R.layout.outcomeslistview,R.id.outcome_text,res.outcomes);
            outcomesList.setAdapter(outcomes);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent = new Intent(ResultDetailActivity.this, DetailActivity.class);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        outcomesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                generateQR(position);
            }
        });

    }

    /**
     * This method is used to generate a qr code on clicking a certain trial
     * @param position
     * The position selected by the user
     */
    private void generateQR(int position) {
        qr.setVisibility(View.VISIBLE);
        outcomesList.setVisibility(View.GONE);

        String type = (String) getIntent().getSerializableExtra("typeres");
        String trial = null;
        if(type.equals("int")){
            IntResult res = (IntResult) getIntent().getSerializableExtra("res");
            ArrayList<Integer> outcomes = res.values;
            trial = String.valueOf(outcomes.get(position));

        }
        else if(type.equals("float")){
            FloatResult res = (FloatResult) getIntent().getSerializableExtra("res");
            ArrayList<Float> outcomes = res.measurements;
            trial = String.valueOf(outcomes.get(position));
        }
        else{
            BoolResult res = (BoolResult) getIntent().getSerializableExtra("res");
            ArrayList<Boolean> outcomes = res.outcomes;
            trial = String.valueOf(outcomes.get(position));
        }
        QRGEncoder qrgEncoder = new QRGEncoder(trial,null, QRGContents.Type.TEXT,275);
        try {
            Bitmap bitmap = qrgEncoder.getBitmap();
            qr.setImageBitmap(bitmap);
        }
        catch (Exception e) {
            Log.v("Exception",e.toString());
        }
    }
}
