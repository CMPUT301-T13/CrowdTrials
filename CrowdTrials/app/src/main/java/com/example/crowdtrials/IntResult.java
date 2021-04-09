package com.example.crowdtrials;

import java.util.ArrayList;

/**
 * This is class that holds the int results of an experiment
 */
public class IntResult extends ResultArr {
    public ArrayList<Integer> values = new ArrayList<>();

    @Override
    public double averageResult() {
        double summ=0;
        for(int i=0;i<values.size();i++){
            summ+=values.get(i);
        }
        if(values.size()==0){
            return 0;
        }
        double correctAnswer= (double) summ/values.size();
        return correctAnswer;
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
