package com.example.crowdtrials;

import android.util.Log;

import java.util.ArrayList;

/**
 * This is class that holds the int results of an experiment
 */
public class IntResult extends ResultArr {
    public ArrayList<Integer> values = new ArrayList<>();


    /**
     * This methods calculates the Average counts for a CountExp or Non negative Count exp
     * @return
     * The average counts of the experiment
     */
    @Override
    public double averageResult() {

        // There is a weird bug where it can't cast to from Long to integer when the method is called
        // so I used the one I made in statistics
        Statistics stats = new Statistics();
        Log.e("result???",Float.toString(stats.getIntegerMean(values)));
        double roundOff = Math.round(stats.getIntegerMean(values) * 100.0) / 100.0;
        return roundOff;
        /*
        double summ=0;
        for(int i=0;i<values.size();i++){
            summ+=values.get(i);
        }
        if(values.size()==0){
            return 0;
        }
        double correctAnswer= (double) summ/values.size();
        return correctAnswer;


         */
    }

    /**
     * This assigns given user to be the experimenter
     * @param experimenter
     * This is the user who is the current experimenter
     */
    public IntResult(User experimenter) {
        super(experimenter);
        this.type="int";
    }


}

