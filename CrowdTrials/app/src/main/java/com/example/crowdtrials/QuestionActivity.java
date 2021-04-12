package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * This activity represents the activity to view a question
 */
public class QuestionActivity extends AppCompatActivity implements AddQuestionFragment.OnFragmentInteractionListener,RespondQuestionFragment.OnFragmentInteractionListener ,QuestionsCallback{
    Database db;
    ArrayAdapter<QnA> ques;
    ListView queslist;
    FloatingActionButton addQ;
    //Button viewAnswer;
    //Button respondToQuestion;
    TextView numreplies;
    Button back;
    Experiment exp;
    User user;
    ArrayList<QnA> questions;
    QnA lastclicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionlv);
        numreplies=findViewById(R.id.replies_text);
        queslist=(ListView)findViewById(R.id.question_list);
        exp = (Experiment) getIntent().getSerializableExtra("exp");
        user = (User) getIntent().getSerializableExtra("user");
        questions = exp.qnalist;
        ques=new QuestionsList(this,questions);
        queslist.setAdapter(ques);
        addQ=findViewById(R.id.add_question_button);
        //viewAnswer=findViewById(R.id.viewanswers);
        back= findViewById(R.id.backbuttonquest);
        //respondToQuestion=findViewById(R.id.answerquestion);
        db = Database.getSingleDatabaseInstance();
        db.getQuestions(exp,this::onCallback);
        // YOU MUST CLICK ON A QUESTION THEN CLICK THE ANSWER OR VIEW IN ORDER TO INTERACT WITH THAT PARTICULAR QUESTION.
        addQ.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AddQuestionFragment().show(getSupportFragmentManager(), "ADD_QUESTION");
            }
        });




        // IMPLEMENT BACK BUTTON

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(QuestionActivity.this, DetailActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("exp",exp);
                setResult(RESULT_OK,intent);
                finish();


            }
        });

        queslist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startViewAnswersIntent(ques.getItem(i),i);


            }


        });

    }

    public void startViewAnswersIntent(QnA question,int position){
        Log.e("I've been called","In itemClickListener");
        Intent intent = new Intent(QuestionActivity.this, ViewAnswersActivity.class);
        intent.putExtra("qna",ques.getItem(position));
        intent.putExtra("exp",exp);
        startActivity(intent);
    }

    public void onCallback(ArrayList<QnA> value, int whichCase){
        //Log.e("Called in questions", " " + value.get(0).question);
        questions.clear();
        questions.addAll(value);
        ques.notifyDataSetChanged();

    }

    @Override
    public void onOkPressed(String newStringquestion) {
        QnA newQuestion = new QnA(newStringquestion);
        exp.qnalist.add(newQuestion);
        db.addQuestion(newQuestion,exp.getName());
    }
    @Override
    public void onAddAnswerOkPressed(String newanswer) {
        lastclicked.answers.add(newanswer);
        numreplies.setText(lastclicked.answers.size() + " replies");
        db.addAnswer(lastclicked,exp.getName(),newanswer);
    }

    public void startRespondFragment(QnA question,int position){
        lastclicked = question;
        new RespondQuestionFragment().show(getSupportFragmentManager(), "ADD_QUESTION");
    }
}
