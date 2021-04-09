package com.example.crowdtrials;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class QuestionsList  extends  ArrayAdapter<QnA>{


        private ArrayList<QnA> questions;
        private Context context;
        TextView questionText;
        TextView numberOfQuestionText;
        Button respondToQuestion;
        public QuestionsList(Context context, ArrayList<QnA> questions) {
            super(context,0,questions);
            this.questions = questions;
            this.context = context;
        }


        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if (view == null){
                view = LayoutInflater.from(context).inflate(R.layout.questionandanswer,parent,false);
                questionText = view.findViewById(R.id.question_text);
                numberOfQuestionText = view.findViewById(R.id.replies_text);
                respondToQuestion = view.findViewById(R.id.replyToQuestion);
                questionText.setText("Question: " + questions.get(position).question);
                numberOfQuestionText.setText(questions.get(position).answers.size() + " replies");

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QuestionActivity questionActivity = (QuestionActivity)context;
                        questionActivity.startViewAnswersIntent(questions.get(position),position);
                    }
                });
                respondToQuestion.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        QuestionActivity questionActivity = (QuestionActivity)context;
                        questionActivity.startRespondFragment(questions.get(position),position);

                    }
                });

            }

            return view;
        }

}


