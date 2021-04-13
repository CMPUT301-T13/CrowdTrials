package com.example.crowdtrials;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the activity for a Measurement Experiment.
 * INSTRUCTION: PRESS CONFIRM TO ADD OUTCOME TO RESULT
 * PRESS STORE RESULT TO CREATE A NEW RESULT AND STORE THE OLD ONE (ONLY WORKS IF NON ZERO)
 */
public class MeasurementActivity extends AppCompatActivity {
    // this will be the page that displays when adding results to an experiment/creating results
    MeasurementExp exp;
    User user;
    Button back;
    Button viewDetails;
    Button store;
    TextView plaintextLastRes;
    TextView lastRes;
    TextView title;
    EditText meas_result;
    FloatResult result;
    Button statsButton;
    TextView warning;
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
        store=findViewById(R.id.meas_storebutton);
        viewDetails=findViewById(R.id.detail_meas_button);
        plaintextLastRes=findViewById(R.id.displayLastRes_meas);
        title=findViewById(R.id.title_meas);
        lastRes=findViewById(R.id.lastresultmeas);
        meas_result=findViewById(R.id.editText_result);
        statsButton = findViewById(R.id.statsbutton3);
        makeTheEditTextsUnEditable();
        warning=findViewById(R.id.warningmeas);
        if(!exp.isGeoLocationEnabled){
            warning.setVisibility(View.GONE);
        }
        title.setText(exp.name);
        plaintextLastRes.setText("Last result");
        lastRes.setText("NaN");
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
                intent.putExtra("exp",exp);
                intent.putExtra("user",user);
                intent.putExtra("pos",pos);
                setResult(RESULT_OK,intent);
                finish();
                result=new FloatResult(user);


            }
        });
        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent = new Intent(MeasurementActivity.this, DetailActivity.class);
                intent.putExtra("exp",exp);
                intent.putExtra("type","meas");
                intent.putExtra("user",user);
                startActivity(intent);
                result=new FloatResult(user);

            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(result.measurements.size()!=0) {
                    database.updateWithResults(result, exp.name);
                    result = new FloatResult(user);
                }

            }
        });
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                //result = new FloatResult(user);
                Intent intent = new Intent(MeasurementActivity.this, StatsActivity.class);
                intent.putExtra("exp", exp);
                intent.putExtra("type", "meas");
                startActivity(intent);
                result=new FloatResult(user);

            }
        });

        final Button barcode_scan = findViewById(R.id.barcode_scan);
        barcode_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeasurementActivity.this,BarcodeScannerActivity.class);
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String scannedCode = data.getSerializableExtra("scannedCode").toString();
                meas_result.setText(scannedCode);
            }
        }
    }


    public void makeTheEditTextsUnEditable() {
        EditText experimentNameEditText = findViewById(R.id.name_editText);
        experimentNameEditText.setFocusable(false);
        experimentNameEditText.setEnabled(false);
        experimentNameEditText.setCursorVisible(false);
        experimentNameEditText.setBackgroundColor(Color.TRANSPARENT);
        experimentNameEditText.setText(exp.name);
        EditText experimentDescriptionEditText = findViewById(R.id.description_editText);
        experimentDescriptionEditText.setText(exp.description);
        experimentDescriptionEditText.setFocusable(false);
        experimentDescriptionEditText.setEnabled(false);
        experimentDescriptionEditText.setCursorVisible(false);
        experimentDescriptionEditText.setBackgroundColor(Color.TRANSPARENT);
        EditText minTrialsEditText = findViewById(R.id.minTrialsEditText);
        minTrialsEditText.setText(" " +exp.minTrials);
        minTrialsEditText.setFocusable(false);
        minTrialsEditText.setEnabled(false);
        minTrialsEditText.setCursorVisible(false);
        minTrialsEditText.setBackgroundColor(Color.TRANSPARENT);
        EditText region_editText = findViewById(R.id.region_editText);
        region_editText.setText(exp.region.name);
        region_editText.setFocusable(false);
        region_editText.setEnabled(false);
        region_editText.setCursorVisible(false);
        region_editText.setBackgroundColor(Color.TRANSPARENT);
        Spinner optionsSpinner =findViewById(R.id.experiment_type_Spinner);
        String[] options = {exp.type};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        optionsSpinner.setAdapter(adapter);


    }
}