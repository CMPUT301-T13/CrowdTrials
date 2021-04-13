package com.example.crowdtrials;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents the activity for a Binomial Experiment.
 */
public class BinomialActivity extends AppCompatActivity {

    // this will be the page that displays when adding results to an experiment/creating results
    BinomialExp exp;
    User user;
    Button back;
    Button viewDetails;
    Button genResult;
    Button qrScan;
    TextView plaintextLastRes;
    TextView lastRes;
    TextView plaintextProb;
    TextView prob;
    TextView title;
    ProgressBar pb;
    BoolResult result;
    Button statsButton;
    Button store;
    String qrTrial = null;
    Database database = Database.getSingleDatabaseInstance();
    int pos;
    int qr = 0;
    boolean res;
    TextView warning;
    int pressed_gen=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binactivity);
        user = (User) getIntent().getSerializableExtra("user");
        exp = (BinomialExp) getIntent().getSerializableExtra("exp");
        exp.probability = 0.5;
        pos = (Integer) getIntent().getSerializableExtra("pos");
        back = findViewById(R.id.backbutton_bin);
        store=findViewById(R.id.count_storebutton);
        viewDetails = findViewById(R.id.detail_bin_button);
        genResult = findViewById(R.id.gen_button);
        plaintextLastRes = findViewById(R.id.plaintext_lastres_bin);
        plaintextProb = findViewById(R.id.plaintext_prob);
        title = findViewById(R.id.title_bin);
        prob = findViewById(R.id.probabilityViewer);
        lastRes = findViewById(R.id.lastresultbin);
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        warning = findViewById(R.id.warningbin);
        statsButton = findViewById(R.id.statsbutton);
        pb.setVisibility(View.GONE);
        qrScan = findViewById(R.id.bin_scan);
        // pb = new ProgressBar(this);
        Log.e("geo", Boolean.toString(exp.isGeoLocationEnabled));
        makeTheEditTextsUnEditable();
        Log.e("In Detail Activity", "" + exp.isGeoLocationEnabled);
        if (!exp.isGeoLocationEnabled) {
            warning.setVisibility(View.GONE);
        }

        title.setText(exp.name);
        prob.setText(Double.toString(exp.probability));
        plaintextProb.setText("Probability");
        plaintextLastRes.setText("Last result");
        lastRes.setText("");
        result = new BoolResult(user);
        //exp.addResult(result);
        // exp.probability=0.5;
        Log.e("In binomial Activity","" + exp.owner.username);

        exp.experimenters.add(user);
        genResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pressed_gen++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                // do onPreExecute stuff
                                pb.setVisibility(View.VISIBLE);
                            }
                        });
                        // do your stuff
                        boolean res = exp.genResult();
                        long startTime = System.currentTimeMillis();
                        while (System.currentTimeMillis() - startTime < 2500) {
                        }
                        result.outcomes.add(res);

                        //Log.d("RESULT ACTIVITY", "run: " + res);


                        //database.updateWithResults(result, exp.name);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                // do onPostExecute stuff
                                pb.setVisibility(View.INVISIBLE);
                                lastRes.setText(Boolean.toString(res));
                            }
                        });
                    }
                }).start();
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(result.outcomes.size()!=0) {
                    database.updateWithResults(result, exp.name);
                    result = new BoolResult(user);
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent = new Intent(BinomialActivity.this, MainActivity.class);
                //result.outcomes.get(0);
                //String ok=exp.name;
                //exp.addResult(result);
                intent.putExtra("exp", exp);
                intent.putExtra("user", user);
                intent.putExtra("pos", pos);
                setResult(RESULT_OK, intent);
                finish();
                result=new BoolResult(user);
                pressed_gen=0;


            }
        });
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)

                for (int i = 0; i < result.outcomes.size(); i++) {
                    Log.d("RESULT ACTIVITY", "run: " + result.outcomes.get(i));
                }
                //og.d("RESULT ACTIVITY", "run: " + res);
                Intent intent = new Intent(BinomialActivity.this, StatsActivity.class);
                intent.putExtra("exp", exp);
                intent.putExtra("type", "bin");
                startActivity(intent);
                result=new BoolResult(user);
                pressed_gen=0;

            }
        });



        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                for (int i = 0; i < result.outcomes.size(); i++) {
                    Log.d("RESULT ACTIVITY", "run: " + result.outcomes.get(i));
                }
                //og.d("RESULT ACTIVITY", "run: " + res);

                if(result.outcomes.size()!=0 && pressed_gen!=0) {

                    //exp.addResult(result);
                    database.updateWithResults(result, exp.name);

                }
                Intent intent = new Intent(BinomialActivity.this, DetailActivity.class);
                intent.putExtra("exp", exp);
                intent.putExtra("type", "bin");
                intent.putExtra("user", user);
                startActivity(intent);
                result=new BoolResult(user);
                lastRes.setText("");
                pressed_gen=0;
            }
        });


        qrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BinomialActivity.this, QRScannerActivity.class);
                intent.putExtra("experiment", exp);
                startActivityForResult(intent, 1);
            }
        });
    }

    public void qrUpdate() {
        if (qrTrial.equals("true")) {
            res = true;
        } else if (qrTrial.equals("false")) {
            res = false;
        }
        result.outcomes.add(res);
        database.updateWithResults(result, exp.name);
        lastRes.setText(Boolean.toString(res));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            qr = 1;

            exp = (BinomialExp) data.getSerializableExtra("exp");
            qrTrial = (String) data.getSerializableExtra("trial");
            qrUpdate();

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
