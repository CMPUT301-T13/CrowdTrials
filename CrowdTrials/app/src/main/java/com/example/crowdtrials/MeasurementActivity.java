package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the activity for a Measurement Experiment.
 */
public class MeasurementActivity extends AppCompatActivity {
    // this will be the page that displays when adding results to an experiment/creating results
    MeasurementExp exp;
    User user;
    Button back;
    Button viewDetails;
    TextView plaintextLastRes;
    TextView lastRes;
    TextView title;
    EditText meas_result;
    FloatResult result;
    Database database =  Database.getSingleDatabaseInstance();
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measureactivity);
        user=(User) getIntent().getSerializableExtra("user");
        exp = (MeasurementExp) getIntent().getSerializableExtra("exp");
        pos=(Integer) getIntent().getSerializableExtra("pos");
        back=findViewById(R.id.backbutton_meas);
        viewDetails=findViewById(R.id.detail_meas_button);
        //plaintextLastRes=findViewById(R.id.plaintext_lastres_meas);
        title=findViewById(R.id.title_meas);
        lastRes=findViewById(R.id.lastresultmeas);
        meas_result=findViewById(R.id.editText_result);

        title.setText(exp.name);
        plaintextLastRes.setText("Last result");
        lastRes.setText("");
        result=new FloatResult(user);
        exp.experimenters.add(user);
        final Button confirmButton = findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Float res = Float.parseFloat(meas_result.getText().toString());
                meas_result.getText().clear();
                lastRes.setText(res.toString());
                result.measurements.add(res);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent = new Intent(MeasurementActivity.this, MainActivity.class);
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
                exp.addResult(result);
                //exp.results.add(result);
                database.updateWithResults(result,exp.name);
                Intent intent = new Intent(MeasurementActivity.this, DetailActivity.class);
                intent.putExtra("exp",exp);
                intent.putExtra("type","meas");
                startActivity(intent);


            }
        });
    }}
