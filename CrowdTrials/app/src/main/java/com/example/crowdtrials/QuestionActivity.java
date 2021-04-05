package com.example.crowdtrials;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity implements AddQuestionFragment.OnFragmentInteractionListener {
    Database db;
    ArrayAdapter<QnA> ques;
    ListView queslist;
    Button addQ;
    Experiment exp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list_view);
        queslist=findViewById(R.id.question_list);
        exp = (Experiment) getIntent().getSerializableExtra("exp");
        ques=new QList(this,exp.qnalist);
        queslist.setAdapter(ques);


        addQ.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AddQuestionFragment().show(getSupportFragmentManager(), "ADD_QUESTION");
            }
        });


    }

    @Override
    public void onOkPressed(String newquestion) {
        exp.qnalist.add(new QnA(newquestion));
    }
}
