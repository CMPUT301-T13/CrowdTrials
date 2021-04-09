package com.example.crowdtrials;

import android.util.Log;

import java.util.ArrayList;

/**
 * This is class that holds the boolean outcomes of an experiment
 */
public class BoolResult extends ResultArr{
    public ArrayList<Boolean> outcomes = new ArrayList<>();

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
        return correctAnswer;
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
