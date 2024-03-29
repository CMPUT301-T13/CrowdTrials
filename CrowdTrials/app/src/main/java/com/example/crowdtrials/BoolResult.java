package com.example.crowdtrials;

import android.util.Log;

import java.util.ArrayList;

/**
 * This is class that holds the boolean outcomes of an experiment
 */
public class BoolResult extends ResultArr{
    public ArrayList<Boolean> outcomes = new ArrayList<>();

    /**
     * This class calculates the result of the experiment by total success / total outcomes.
     * @return
     * This is the success Rate of the Binomial Experiment
     */
    @Override
    public double averageResult() {
        // calculate success rate for this sequence of results
        int totalsuccesses=0;
        for(int i=0;i<outcomes.size();i++){
            if(outcomes.get(i)){
                totalsuccesses++;
            }
        }
        if(outcomes.size()==0){
            return 0;
        }
        Log.d("Are we being called","Yes!");
        Log.d("How many outcomes",Integer.toString(outcomes.size()));
        Log.d("How many successes",Integer.toString(totalsuccesses));
        Log.d("returnvalue",Float.toString(totalsuccesses/outcomes.size()));
        double correctAnswer= (double) totalsuccesses/outcomes.size();
        double roundOff = Math.round(correctAnswer * 100.0) / 100.0;
        return roundOff;
    }

    /**
     * This assigns given user to be the experimenter
     * @param experimenter
     * This is the user who is the current experimenter
     */
    public BoolResult(User experimenter) {
        super(experimenter);
        this.type="bool";

    }
}
