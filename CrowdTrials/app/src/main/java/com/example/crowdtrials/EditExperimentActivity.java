package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;

public class EditExperimentActivity extends AppCompatActivity {
    TextView published;
    TextView ended;
    TextView desc;
    TextView tit;
    TextView own;
    Button publish;
    Button end;
    Button back;
    User user;
    Experiment exp;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayexp);
        database = Database.getSingleDatabaseInstance();
        publish=findViewById(R.id.publish_button);
        end=findViewById(R.id.end_button);
        tit=findViewById(R.id.exp_title_tv);
        desc=findViewById(R.id.exp_desc_tv);
        published=findViewById(R.id.published_status_tv);
        ended=findViewById(R.id.ended_status_tv);
        own=findViewById(R.id.owner_tv);
        back=findViewById(R.id.back_exp);
        user = (User) getIntent().getSerializableExtra("user");
        exp = (Experiment) getIntent().getSerializableExtra("exp");
        Log.e("expowneruser",exp.owner.username);
        if(!exp.owner.username.equals(User.staticInstanceOfUser.username)){
            publish.setVisibility(View.GONE);
            end.setVisibility(View.GONE);
        }
        tit.setText(exp.name);
        desc.setText(exp.description);
        own.setText(exp.owner.username);
        published.setText("Published status: "+exp.published);
        ended.setText("Ended status: "+exp.ended);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditExperimentActivity.this, DetailActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("exp",exp);
                setResult(RESULT_OK,intent);
                finish();



            }
        });
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(exp.published){
                    exp.published=false;
                    publish.setText("Publish");
                    published.setText("Published status: "+exp.published);

                }
                else{
                    if(exp.minTrials<=exp.results.size()) {
                        exp.published = true;
                        publish.setText("Depublish");
                        published.setText("Published status: " + exp.published);
                    }
                    else{
                        published.setText("Did not meet minimum number of trials, still unpublished");
                    }
                }




            }
        });
        ended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(exp.ended){
                    exp.ended=false;
                    publish.setText("End");
                    ended.setText("Ended status: "+exp.ended);
                    //database.updateEnd(exp,exp.ended);
                }
                else{
                    exp.ended=true;
                    publish.setText("Restart");
                    ended.setText("Ended status: "+exp.ended);
                    //database.updateEnd(exp,exp.ended);
                }



            }
        });
    }
}
