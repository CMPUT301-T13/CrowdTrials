package com.example.crowdtrials;

import java.util.ArrayList;

/**
 * This is class that holds the int results of an experiment
 */
public class IntResult extends ResultArr {

    public ArrayList<Integer> values = new ArrayList<>();

    /**
     * This assigns given user to be the experimenter
     * @param experimenter
     * This is the user who is the current experimenter
     */
    public IntResult(User experimenter) {
        super(experimenter);
    }


}
