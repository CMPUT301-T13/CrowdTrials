package com.example.crowdtrials;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the activity for a Non negative counts Experiment.
 */
public class NonNegativeCountActivity extends AppCompatActivity {
    // this will be the page that displays when adding results to an experiment/creating results
    NonNegativeCountExp exp;
    User user;
    Button back;
    Button viewDetails;
    Button qRScan;
    TextView plaintextLastRes;
    TextView lastRes;
    TextView title;
    TextView warning;
    EditText non_result;
    IntResult result;
    Button statsButton;
    TextView newRegion;
    Location addRegion;
    int qr;
    String qrTrial;
    int pos;

    Database database = Database.getSingleDatabaseInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        user = (User) getIntent().getSerializableExtra("user");
        exp = (NonNegativeCountExp) getIntent().getSerializableExtra("exp");
        pos = (Integer) getIntent().getSerializableExtra("pos");
        back = findViewById(R.id.backbutton_non);
        viewDetails = findViewById(R.id.detail_non_button);
        statsButton = findViewById(R.id.statsButton2);
        //plaintextLastRes=findViewById(R.id.plaintext_lastres_non);
        title = findViewById(R.id.title_non);
        lastRes = findViewById(R.id.lastresultnon);
        non_result = findViewById(R.id.editText_result_non);
        qRScan = findViewById(R.id.count_scan);
        warning = findViewById(R.id.warningnon);
        newRegion = (EditText) findViewById(R.id.nonnegexp_region);
        makeTheEditTextsUnEditable();

        title.setText(exp.name);
        // plaintextLastRes.setText("Last result");
        lastRes.setText("");
        result = new IntResult(user);
        exp.experimenters.add(user);
        final Button confirmButton = findViewById(R.id.button_confirm);
        confirmButton.setEnabled(false);
        Log.e("geo",Boolean.toString(exp.isGeoLocationEnabled));
        if (!exp.isGeoLocationEnabled) {
            warning.setVisibility(View.GONE);
            newRegion.setVisibility(View.INVISIBLE);
            confirmButton.setEnabled(true);
        }

        //If geolocation is required, do not let user generate result until geolocation is entered
        newRegion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    confirmButton.setEnabled(false);
                } else {
                    confirmButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer res = Integer.parseInt(non_result.getText().toString());
                addRegion = new Location(newRegion.getText().toString());
                if (res <= 0) {

                    if (res < 0) {

                        lastRes.setText("LAST RESULT WAS NEGATIVE, INVALID!");
                    } else {
                        non_result.getText().clear();
                        lastRes.setText(res.toString());
                        result.values.add(res);
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent = new Intent(NonNegativeCountActivity.this, MainActivity.class);
                if(result.values.size()!=0) {
                    exp.addResult(result);
                    database.updateWithResults(result, exp.name);

                }
                intent.putExtra("exp", exp);
                intent.putExtra("user", user);
                intent.putExtra("pos", pos);
                setResult(RESULT_OK, intent);
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
                database.updateWithResults(result, exp.name);
                Intent intent = new Intent(NonNegativeCountActivity.this, DetailActivity.class);

                intent.putExtra("exp",exp);
                intent.putExtra("type","ncount");
                intent.putExtra("user",user);
                intent.putExtra("region", addRegion);

                startActivity(intent);


            }
        });


        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)

                Intent intent = new Intent(NonNegativeCountActivity.this, StatsActivity.class);
                intent.putExtra("exp", exp);
                intent.putExtra("type", "ncount");
                startActivity(intent);

            }
        });


        qRScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NonNegativeCountActivity.this,QRScannerActivity.class);
                intent.putExtra("experiment",exp);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            qr = 1;

            exp = (NonNegativeCountExp) data.getSerializableExtra("exp");
            qrTrial = (String) data.getSerializableExtra("trial");
            qrUpdate();

        }
    }

    public void qrUpdate() {
        Integer res = Integer.parseInt(qrTrial);
        lastRes.setText(res.toString());
        result.values.add(res);

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
