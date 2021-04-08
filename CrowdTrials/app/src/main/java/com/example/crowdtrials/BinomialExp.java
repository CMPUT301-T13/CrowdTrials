package com.example.crowdtrials;

//import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class holds a Binomial Experiment.
 */
public class BinomialExp extends Experiment implements Serializable {
    double probability;
    //ArrayList<BoolResult> results = new ArrayList<>();
    //String type = "Binomial Exp";

    /**
     * This is a constructor for a Binomial Experiment object
     * @param owner
     * This is the user that created the experiment
     * @param region
     * This is the region in which the experiment will take place
     * @param description
     * This is the description of the experiment
     * @param date
     * This is the published date of the experiment
     * @param minTrials
     * This is the minimum trials required for an experiment
     * @param experimentName
     * This is the name of the experiment
     */
    public BinomialExp(User owner, Location region, String description, Date date, int minTrials,String experimentName) {
        super(owner, region, description, date, minTrials, experimentName);
        this.setType("Binomial Exp");
    }

    /**
     * This is a default constructor for a Binomial Experiment
     */
    public BinomialExp() {
        this.setType("Binomial Exp");
    }

    /**
     * This generates the result for the experiment
     * @return
     * This is the result of the Binomial Experiment
     */
    public boolean genResult()
    {
        return Math.random() >= 1.0 - probability;
    }
}
