package com.example.crowdtrials;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * This activity represents the adapter for a list of results
 */
public class ResultList extends ArrayAdapter<ResultArr> {
    private ArrayList<ResultArr> results;
    private Context context;
    public ResultList(Context context, ArrayList<ResultArr> results){
        super(context,0,results);
        this.results=results;
        this.context=context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        // return super.getView(position, convertView, parent);
        View view=convertView;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.displayresults, parent,false);
        }
        ResultArr res = results.get(position);
        TextView rtext = view.findViewById(R.id.result_text);
        TextView author = view.findViewById(R.id.author_text);
        rtext.setText(Double.toString(res.averageResult()));

        //Log.d("AVGRESULT",Double.toString(res.averageResult()));

        //Log.d("AVGRESULT",Double.toString(res.averageResult()));
        author.setText(res.experimenter.username);
        return view;
    }


}
