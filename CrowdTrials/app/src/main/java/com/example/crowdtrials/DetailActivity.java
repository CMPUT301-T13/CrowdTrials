package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * This class represents the Detail Activity of a certain experiment that shows the result of the experiment and gives an option to look at all outcomes.
 */
public class DetailActivity extends AppCompatActivity implements ResultsCallback,IgnoreResultFragment.OnFragmentInteractionListener{
    Database db;
    ListView reslist;
    ArrayList<ResultArr> resultArrArrayList;
    ArrayAdapter<ResultArr> resAdapter;
    Button back;
    Experiment exp;
    User user;
    Button ignoreResultsFrom;
    Button questionsbutton;
    Button editExperiment;
    String type;
    ArrayList<ResultArr> viewResultsFrom = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewdetact);
        //Experiment exp;
        //db.getSingleExperiment();
        Log.e("working","not working.");
        editExperiment=findViewById(R.id.editexperimentbutton);
        reslist=findViewById(R.id.res_list);
        back = findViewById(R.id.backbuttondispres);
        type=(String) getIntent().getSerializableExtra("type");
        ignoreResultsFrom = findViewById(R.id.ignoreResultsFromButton);
        user=(User) getIntent().getSerializableExtra("user");
        questionsbutton = findViewById(R.id.viewquestions);

        if(type.equals("meas")){
            exp = (MeasurementExp) getIntent().getSerializableExtra("exp");
            Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);
            for(int i=0;i<exp.results.size();i++){
                if(!exp.ignoredUsers.contains(exp.results.get(i).experimenter.username)){
                    viewResultsFrom.add(exp.results.get(i));
                }
            }
            resAdapter = new ResultList(this,viewResultsFrom);
            reslist.setAdapter(resAdapter);

        }
        else if(type.equals("bin")){
            exp = (BinomialExp) getIntent().getSerializableExtra("exp");
            Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);
            for(int i=0;i<exp.results.size();i++){
                if(!exp.ignoredUsers.contains(exp.results.get(i).experimenter.username)){
                    viewResultsFrom.add(exp.results.get(i));
                }
            }
            resAdapter = new ResultList(this,viewResultsFrom);
            reslist.setAdapter(resAdapter);
        }
        else if(type.equals("ncount")){
            exp = (NonNegativeCountExp) getIntent().getSerializableExtra("exp");
            Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);
            for(int i=0;i<exp.results.size();i++){
                if(!exp.ignoredUsers.contains(exp.results.get(i).experimenter.username)){
                    viewResultsFrom.add(exp.results.get(i));
                }
            }
            resAdapter = new ResultList(this,viewResultsFrom);
            reslist.setAdapter(resAdapter);
        }
        else{
            exp = (CountExp) getIntent().getSerializableExtra("exp");
            Database.getSingleDatabaseInstance().getAllResults(exp,this::onCallback);
            for(int i=0;i<exp.results.size();i++){
                if(!exp.ignoredUsers.contains(exp.results.get(i).experimenter.username)){
                    viewResultsFrom.add(exp.results.get(i));
                }
            }
            resAdapter = new ResultList(this,viewResultsFrom);
            reslist.setAdapter(resAdapter);
        }

        if(exp.owner==null || !exp.owner.username.equals(user.username)){
            //ignoreResultsFrom.setVisibility(GONE);
            //editExperiment.setVisibility(GONE);
        }
        editExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, EditExperimentActivity.class);
                intent.putExtra("exp",exp);
                intent.putExtra("user",user);
                startActivity(intent);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent;
                if(type.equals("meas")){
                    intent = new Intent(DetailActivity.this, MeasurementActivity.class);

                }
                else if(type.equals("bin")){
                    intent = new Intent(DetailActivity.this, BinomialActivity.class);
                }
                else if(type.equals("ncount")){
                    intent = new Intent(DetailActivity.this, CountActivity.class);
                }
                else{
                    intent = new Intent(DetailActivity.this, NonNegativeCountActivity.class);
                }

                setResult(RESULT_OK,intent);
                finish();
            }
        });
        // want to have it setup so that clicking a result tells you who it is from and basic info about the result
        ignoreResultsFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IgnoreResultFragment().show(getSupportFragmentManager(), "IGNORE");

            }
        });
        reslist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DetailActivity.this, ResultDetailActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("exp",exp);
                intent.putExtra("res",resAdapter.getItem(i));
                intent.putExtra("typeres",resAdapter.getItem(i).type);
                Log.e("type",resAdapter.getItem(i).type);
                startActivity(intent);

            }


        });


        questionsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, QuestionActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("exp",exp);
                startActivity(intent);
            }
        });

    }
    public void onCallback(ArrayList<ResultArr> value,Experiment exp, int whichCase){
        resultArrArrayList = value;
        for (ResultArr result :value){
            viewResultsFrom.add(result);


        }
        resAdapter.notifyDataSetChanged();
    }

    @Override
    public void onOkPressed(String username) {
        exp.ignoredUsers.add(username);

        for(int i=0;i<viewResultsFrom.size();i++){
            if (viewResultsFrom.get(i).experimenter.username.equals(username)){
                viewResultsFrom.remove(i);
            }
        }
        resAdapter.notifyDataSetChanged();

    }
}
