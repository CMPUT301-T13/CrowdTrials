package com.example.crowdtrials;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crowdtrials.Experiment;

import java.util.ArrayList;

public class ExperimentList extends ArrayAdapter<Experiment> {

    private ArrayList<Experiment> experiments;
    private Context context;

    public ExperimentList(Context context, ArrayList<Experiment> experiments) {
        super(context,0,experiments);
        this.experiments = experiments;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content,parent,false);
        }

        Experiment experiment = experiments.get(position);
        //Access all Views in the layout for later use

        TextView experimentDescription = view.findViewById(R.id.experimentDescription);
        TextView userNameTextView = view.findViewById(R.id.ownerUserName);


        // Add Descriptions here for the properties to display in the view rather than hardcoding it into
        // the strings
        experimentDescription.setText("Experiment Description: " + experiment.getDescription());
        userNameTextView.setText(experiment.getOwner().username);



        // Handle the case when the numberOfSuccess is zero so we dont divide by zero



        return view;
    }
}
