package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the activity for a Counts Experiment.
 */
public class CountActivity extends AppCompatActivity {
    // this will be the page that displays when adding results to an experiment/creating results
    CountExp exp;
    User user;
    Button back;
    Button viewDetails;
    TextView plaintextLastRes;
    TextView lastRes;
    TextView title;
    EditText count_result;
    IntResult result;
    int pos;
    Database database =  Database.getSingleDatabaseInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonnegativeactivity);
        user=(User) getIntent().getSerializableExtra("user");
        exp = (CountExp) getIntent().getSerializableExtra("exp");
        pos=(Integer) getIntent().getSerializableExtra("pos");
        back=findViewById(R.id.backbutton_non);
        viewDetails=findViewById(R.id.detail_non_button);
        //plaintextLastRes=findViewById(R.id.plaintext_lastres_non);
        title=findViewById(R.id.title_non);
        lastRes=findViewById(R.id.lastresultnon);
        count_result=findViewById(R.id.editText_result_non);
        Log.e("Count Activity", "Experiment: " + exp.name);
        title.setText(exp.name);
//        plaintextLastRes.setText("Last result");
        lastRes.setText("");
        result=new IntResult(user);

        final Button confirmButton = findViewById(R.id.button_confirm_non);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer res = Integer.parseInt(count_result.getText().toString());
                count_result.getText().clear();
                lastRes.setText(res.toString());
                result.values.add(res);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent = new Intent(CountActivity.this, MainActivity.class);
                exp.addResult(result);
                database.updateWithResults(result,exp.name);
                intent.putExtra("exp",exp);
                intent.putExtra("user",user);
                intent.putExtra("pos",pos);
                setResult(RESULT_OK,intent);
                finish();


            }
        });
        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent = new Intent(CountActivity.this, DetailActivity.class);
                intent.putExtra("exp",exp);
                intent.putExtra("user",user);
                setResult(RESULT_OK,intent);
                finish();


            }
        });
    }}
