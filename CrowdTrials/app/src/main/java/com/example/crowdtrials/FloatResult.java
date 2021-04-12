package com.example.crowdtrials;

import android.util.Log;

import java.util.ArrayList;

/**
 * This is class that holds the float measurements of an experiment
 */
public class FloatResult extends ResultArr{
    public ArrayList<Float> measurements = new ArrayList<>();


    /**
     * This methods returns the average float result for a measurement experiment
     * @return
     * The average float result of a measurement experiment
     */
    @Override
    public double averageResult() {
        double summ=0;
        for(int i=0;i<measurements.size();i++){
            Log.e("always working"," "+ String.valueOf(measurements.get(i)));
            String summer =  String.valueOf( measurements.get(i));
            summ += Double.parseDouble(summer);
            //summ+=measurements.get(i);
        }
        if(measurements.size()==0){
            return 0;
        }
        double correctAnswer= (double) summ/measurements.size();
        return correctAnswer;
    }

    /**
     * This assigns given user to be the experimenter
     * @param experimenter
     * This is the user who is the current experimenter
     */
    public FloatResult(User experimenter) {
        super(experimenter);
        this.type="float";

    }
}
