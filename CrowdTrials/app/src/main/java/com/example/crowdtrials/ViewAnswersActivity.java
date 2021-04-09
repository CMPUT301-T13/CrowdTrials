package com.example.crowdtrials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewAnswersActivity extends AppCompatActivity {
    Button back;
    ListView answersList;
    QnA qna;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewanswers);
        answersList = findViewById(R.id.answer_list);
        qna = (QnA) getIntent().getSerializableExtra("qna");
        back = findViewById(R.id.backbuttonans);
        ArrayAdapter<String> answers = new ArrayAdapter<>(this,R.layout.answerlistview,R.id.answer_text,qna.answers);
        answersList.setAdapter(answers);

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
}
