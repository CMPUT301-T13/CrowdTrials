package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;

public class ResultDetailActivity extends AppCompatActivity {
    Button back;
    Experiment exp;
    User user;
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

    }
}
