package com.example.crowdtrials;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        Button subscribedButton = view.findViewById(R.id.subscribeButton);
        Button addResult = view.findViewById(R.id.addResultButton);
        subscribedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscribedButton.setText("Subscribed");
                subscribedButton.setTextColor(0xFF00BCD4);


                MainActivity mainActivity = (MainActivity) context;
                mainActivity.subscribedButtonPressed(experiments.get(position));
            }
        });

        addResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult.setText("Add Result");
                addResult.setTextColor(0xFF00BCD4);
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.addResultPressed(experiments.get(position),position);

            }
        });



        // Add Descriptions here for the properties to display in the view rather than hardcoding it into
        // the strings
        experimentDescription.setText("Experiment Description: " + experiment.getDescription());
        userNameTextView.setText(experiment.getOwner().username);



        // Handle the case when the numberOfSuccess is zero so we dont divide by zero



        return view;
    }
}
