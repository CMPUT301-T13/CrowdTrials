package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * This class represents the activity to view all answers of a question
 */
public class ViewAnswersActivity extends AppCompatActivity implements QuestionsCallback{
    Button back;
    ListView answersList;
    QnA qna;
    Database db;
    Experiment exp;
    ArrayAdapter<String> answersAdapter;
    public static int size;
    public ArrayList<String> answers=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewanswers);
        answersList = findViewById(R.id.answer_list);
        qna = (QnA) getIntent().getSerializableExtra("qna");
        exp = (Experiment) getIntent().getSerializableExtra("exp");
        back = findViewById(R.id.backbuttonans);

        answersAdapter = new ArrayAdapter<>(this,R.layout.answerlistview,R.id.answer_text,this.answers);
        answersList.setAdapter(answersAdapter);
        db = Database.getSingleDatabaseInstance();
        db.getAnswers(exp,qna,this::onCallback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back to main activity put experiment and its index as extras into the intent set as result and finish activity
                // do this so we can make changes permanent (during lifespan of app until closed)
                Intent intent = new Intent(ViewAnswersActivity.this, QuestionActivity.class);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    public void onCallback(ArrayList<QnA> value, int whichCase){
        //Log.e("In Callback","FOR view answers" + value.get(0).question);
        for(QnA question: value){
            this.answers.addAll(question.answers);

        }
        size=answers.size();
        answersAdapter.notifyDataSetChanged();

    }
}
