package main.java.com.example.crowdtrials;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class ExperimentList extends ArrayAdapter<Experiment> {
    // based on CustomList in Lab3 made by CMPUT 301 TAs
    // this list essentially just populates content xml page with the experiments in the arraylist experiments
    private ArrayList<Experiment> experiments;
    private Context context;
    public ExperimentList(Context context, ArrayList<Experiment> experiments){
        super(context,0,experiments);
        this.experiments = experiments;
        this.context = context;
    }




    }

