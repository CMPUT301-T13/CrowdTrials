package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity implements AddQuestionFragment.OnFragmentInteractionListener,RespondQuestionFragment.OnFragmentInteractionListener {
    Database db;
    ArrayAdapter<QnA> ques;
    ListView queslist;
    Button addQ;
    Button viewAnswer;
    Button respondToQuestion;
    Experiment exp;
    User user;
    QnA lastclicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionlv);
        queslist=findViewById(R.id.question_list);
        exp = (Experiment) getIntent().getSerializableExtra("exp");
        user = (User) getIntent().getSerializableExtra("user");
        ques=new QList(this,exp.qnalist);
        queslist.setAdapter(ques);
        addQ=findViewById(R.id.add_question_button);
        viewAnswer=findViewById(R.id.viewanswers);
        respondToQuestion=findViewById(R.id.answerquestion);
        // YOU MUST CLICK ON A QUESTION THEN CLICK THE ANSWER OR VIEW IN ORDER TO INTERACT WITH THAT PARTICULAR QUESTION.
        addQ.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AddQuestionFragment().show(getSupportFragmentManager(), "ADD_QUESTION");
            }
        });
        viewAnswer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(QuestionActivity.this, ViewAnswersActivity.class);
                intent.putExtra("qna",lastclicked);
                startActivity(intent);


            }
        });
        respondToQuestion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lastclicked!=null) {
                    new RespondQuestionFragment().show(getSupportFragmentManager(), "ADD_QUESTION");
                }
            }
        });
        queslist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastclicked=ques.getItem(i);

            }


        });

    }

    @Override
    public void onOkPressed(String newquestion) {
        exp.qnalist.add(new QnA(newquestion));
    }
    @Override
    public void onAddAnswerOkPressed(String newanswer) {
        lastclicked.answers.add(newanswer);
    }
}
