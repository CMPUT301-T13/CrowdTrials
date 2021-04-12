package com.example.crowdtrials;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This abstract class represents an array of Results for the experiment
 */
public abstract class ResultArr implements Serializable {
    public User experimenter;
    public abstract double averageResult();
    String type;
    //ArrayList<Double> values = new ArrayList<>();

    /**
     * This method returns the current experimenter
     * @return
     * The experimenter of the experimenter
     */
    public User getExperimenter() {
        return experimenter;
    }

    /**
     * The constructor of the result array of experimenters
     * @param experimenter
     * The experimenter associated to the results
     */
    public ResultArr(User experimenter) {
        this.experimenter = experimenter;
    }

}
