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

/**
 * This class represents the list of questions adapter
 */
public class QList extends ArrayAdapter<QnA> {
    private ArrayList<QnA> questions;
    private Context context;

    /**
     * This class is a constructor for the adapter of list of questions
     * @param context
     * The activity or context
     * @param questions
     * The array list of questions
     */
    public QList(Context context, ArrayList<QnA> questions){
        super(context,0,questions);
        this.questions=questions;
        this.context=context;
    }

    /**
     * This method returns the view based on the user selection
     * @param position
     * The position that the user selected
     * @param convertView
     * The view being converted
     * @param parent
     * The parent view
     * @return
     * The view associated to the user selection
     */
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
        TextView numReplies = view.findViewById(R.id.replies_text);
        qtext.setText(qna.question);
        numReplies.setText(Integer.toString(qna.answers.size()));
        return view;
    }


}
