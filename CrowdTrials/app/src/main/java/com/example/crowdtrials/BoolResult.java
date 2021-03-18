package com.example.crowdtrials;

import java.util.ArrayList;

/**
 * This is class that holds the boolean outcomes of an experiment
 */
public class BoolResult extends ResultArr{
    public ArrayList<Boolean> outcomes = new ArrayList<>();

    /**
     * This assigns given user to be the experimenter
     * @param experimenter
     * This is the user who is the current experimenter
     */
    public BoolResult(User experimenter) {
        super(experimenter);

    }
}
