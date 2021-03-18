package com.example.crowdtrials;

import java.util.ArrayList;

/**
 * This is class that holds the float measurements of an experiment
 */
public class FloatResult extends ResultArr{
    public ArrayList<Float> measurements = new ArrayList<>();

    /**
     * This assigns given user to be the experimenter
     * @param experimenter
     * This is the user who is the current experimenter
     */
    public FloatResult(User experimenter) {
        super(experimenter);

    }
}
