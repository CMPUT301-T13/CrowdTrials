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

/**
 * This class represents the adapter for a list of questions of an experiment
 */
public class QuestionsList  extends  ArrayAdapter<QnA>{

        int res;
        private ArrayList<QnA> questions;
        private Context context;
        TextView questionText;
        TextView numberOfQuestionText;
        Button respondToQuestion;
        static TextView numberOfReplies;



    /**
     * This method is a constructor for the adapter for list of questions
     * @param context
     * The current context
     * @param questions
     * The list of questions
     */
        public QuestionsList(Context context, ArrayList<QnA> questions) {
            super(context,0,questions);
            this.questions = questions;
            this.context = context;
        }

    /**
     * This method is used to return a View based on user selection
     * @param position
     * The position selected by user
     * @param convertView
     * The view being converted
     * @param parent
     * The parent view
     * @return
     * The view associated to the position selected by the user
     */
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if (view == null){
                view = LayoutInflater.from(context).inflate(R.layout.questionandanswer,parent,false);
                questionText = view.findViewById(R.id.question_text);
                numberOfQuestionText = view.findViewById(R.id.replies_text);
                respondToQuestion = view.findViewById(R.id.replyToQuestion);
                numberOfReplies=view.findViewById(R.id.replies_text);
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


