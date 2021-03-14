package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    BoolResult result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        user=(User) getIntent().getSerializableExtra("user");
        exp = (BinomialExp) getIntent().getSerializableExtra("exp");
        back=findViewById(R.id.backbutton_bin);
        viewDetails=findViewById(R.id.detail_bin_button);
        genResult=findViewById(R.id.gen_button);
        plaintextLastRes=findViewById(R.id.plaintext_lastres_bin);
        plaintextProb= findViewById(R.id.plaintext_prob);
        title=findViewById(R.id.title_bin);
        prob=findViewById(R.id.probabilityViewer);
        lastRes=findViewById(R.id.lastresultbin);

        title.setText(exp.name);
        prob.setText(Float.toString(exp.probability));
        plaintextProb.setText("Probability");
        plaintextLastRes.setText("Last result");
        lastRes.setText("");
        result=new BoolResult(user);

        genResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // click this button update sucrate and number of failures
                boolean res = exp.genResult();
                lastRes.setText(Boolean.toString(res));
                result.outcomes.add(res);


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent = new Intent(BinomialActivity.this, MainActivity.class);
                exp.addResult(result);
                intent.putExtra("exp",exp);
                intent.putExtra("user",user);
                setResult(RESULT_OK,intent);
                finish();


            }
        });
        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent = new Intent(BinomialActivity.this, DetailActivity.class);
                intent.putExtra("exp",exp);
                intent.putExtra("user",user);
                setResult(RESULT_OK,intent);
                finish();


            }
        });
}}
