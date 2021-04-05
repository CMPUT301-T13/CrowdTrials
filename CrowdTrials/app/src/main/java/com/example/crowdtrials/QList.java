package com.example.crowdtrials;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class QList extends ArrayAdapter<QnA> {
    private ArrayList<QnA> questions;
    private Context context;
    public QList(Context context, ArrayList<QnA> questions){
        super(context,0,questions);
        this.questions=questions;
        this.context=context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        // return super.getView(position, convertView, parent);
        View view=convertView;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.questionandanswer, parent,false);
        }
        QnA qna = questions.get(position);
        TextView qtext = view.findViewById(R.id.question_text);
        TextView numReplies = view.findViewById(R.id.numreplies_text);
        qtext.setText(qna.question);
        numReplies.setText(Integer.toString(qna.answers.size()));
        return view;
    }


}
