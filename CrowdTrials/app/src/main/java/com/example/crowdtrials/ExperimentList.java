package com.example.crowdtrials;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

/**
 * This class represents the list of experiments
 */
public class ExperimentList extends ArrayAdapter<Experiment> {

    private ArrayList<Experiment> experiments;
    private Context context;

    /**
     * This is a constructor for the list of experiments
     * @param context
     * The activity context
     * @param experiments
     * The list of experiments
     */
    public ExperimentList(Context context, ArrayList<Experiment> experiments) {
        super(context,0,experiments);
        this.experiments = experiments;
        this.context = context;
    }

    /**
     * This method returns the View of a certain experiment
     * @param position
     * The position of the selected experiment
     * @param convertView
     * The view to be converted
     * @param parent
     * The parent view
     * @return
     * The view of the selected experiment in list
     */
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
        TextView experimentName = view.findViewById(R.id.experiment_name);
        subscribedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscribedButton.setText("Subscribed");
                subscribedButton.setTextColor(0xFF00BCD4);
                addResult.setTextColor(Color.BLACK);


                MainActivity mainActivity = (MainActivity) context;
                mainActivity.subscribedButtonPressed(experiments.get(position));
            }
        });

        addResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addResult.setText("Add Result");
                addResult.setTextColor(0xFF00BCD4);
                addResult.setTextColor(Color.BLACK);
                AddResult activity = (AddResult) context;
                activity.addResultPressed(experiments.get(position),position);

            }
        });


        userNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity mainActivity = (MainActivity) context;
                mainActivity.userNameWasPressed(experiments.get(position).owner.username);

            }
        });



        // Add Descriptions here for the properties to display in the view rather than hardcoding it into
        // the strings
        experimentDescription.setText("Experiment Description: " + experiment.getDescription());
        userNameTextView.setText(experiment.getOwner().username);
        experimentName.setText(experiment.getName());




        // Handle the case when the numberOfSuccess is zero so we dont divide by zero



        return view;
    }
}
