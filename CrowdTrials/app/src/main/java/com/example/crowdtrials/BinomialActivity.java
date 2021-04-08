package com.example.crowdtrials;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    TextView plaintextLastRes;
    TextView lastRes;
    TextView plaintextProb;
    TextView prob;
    TextView title;
    ProgressBar pb;
    BoolResult result;
    Database database =  Database.getSingleDatabaseInstance();
    int pos;
    boolean res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binactivity);
        user=(User) getIntent().getSerializableExtra("user");
        exp = (BinomialExp) getIntent().getSerializableExtra("exp");
        exp.probability=0.5;
        pos=(Integer) getIntent().getSerializableExtra("pos");
        back=findViewById(R.id.backbutton_bin);
        viewDetails=findViewById(R.id.detail_bin_button);
        genResult=findViewById(R.id.gen_button);
        plaintextLastRes=findViewById(R.id.plaintext_lastres_bin);
        plaintextProb= findViewById(R.id.plaintext_prob);
        title=findViewById(R.id.title_bin);
        prob=findViewById(R.id.probabilityViewer);
        lastRes=findViewById(R.id.lastresultbin);
        pb=(ProgressBar)findViewById(R.id.progressBar1);
        pb.setVisibility(View.GONE);
       // pb = new ProgressBar(this);
        makeTheEditTextsUnEditable();

        title.setText(exp.name);
        prob.setText(Double.toString(exp.probability));
        plaintextProb.setText("Probability");
        plaintextLastRes.setText("Last result");
        lastRes.setText("");
        result=new BoolResult(user);
        //exp.addResult(result);
       // exp.probability=0.5;

        exp.experimenters.add(user);
        genResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        while(System.currentTimeMillis()-startTime<2500){
                        }
                        result.outcomes.add(res);
                        Log.d("RESULT ACTIVITY", "run: " + result);


                        database.updateWithResults(result,exp.name);
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent = new Intent(BinomialActivity.this, MainActivity.class);
                //result.outcomes.get(0);
                //String ok=exp.name;
                exp.addResult(result);
                Log.d("RESULT ACTIVITY back", "run: " + exp.results);
                Log.d("RESULT ACTIVITY backu", "run: " + exp.experimenters.get(0).username);
                database.updateWithResults(result,exp.name);
                //exp.addResult(result);
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
                result.outcomes.add(res);
                exp.addResult(result);
                database.updateWithResults(result,exp.name);
                Intent intent = new Intent(BinomialActivity.this, DetailActivity.class);
                intent.putExtra("exp",exp);
                intent.putExtra("type","bin");
                startActivity(intent);

            }
        });
}

 public void makeTheEditTextsUnEditable(){
     EditText experimentNameEditText = findViewById(R.id.name_editText);
     experimentNameEditText.setFocusable(false);
     experimentNameEditText.setEnabled(false);
     experimentNameEditText.setCursorVisible(false);
     experimentNameEditText.setBackgroundColor(Color.TRANSPARENT);
     EditText experimentDescriptionEditText = findViewById(R.id.description_editText);
     experimentDescriptionEditText.setFocusable(false);
     experimentDescriptionEditText.setEnabled(false);
     experimentDescriptionEditText.setCursorVisible(false);
     experimentDescriptionEditText.setBackgroundColor(Color.TRANSPARENT);
     EditText minTrialsEditText = findViewById(R.id.minTrialsEditText);
     minTrialsEditText.setFocusable(false);
     minTrialsEditText.setEnabled(false);
     minTrialsEditText.setCursorVisible(false);
     minTrialsEditText.setBackgroundColor(Color.TRANSPARENT);
     EditText region_editText = findViewById(R.id.region_editText);
     region_editText.setFocusable(false);
     region_editText.setEnabled(false);
     region_editText.setCursorVisible(false);
     region_editText.setBackgroundColor(Color.TRANSPARENT);



 }


}
