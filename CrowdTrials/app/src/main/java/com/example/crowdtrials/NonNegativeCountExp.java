package com.example.crowdtrials;

//import android.location.Location;

import java.io.Serializable;
import java.util.Date;

/**
 * This class holds a Non Negative Counts experiments.
 */

public class NonNegativeCountExp extends Experiment implements Serializable {
    //String type = "NonNegativeCountExp";
    /**
     * This is a constructor for a Non Negative Experiment object
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
    public NonNegativeCountExp(User owner, Location region, String description, Date date, int minTrials,String experimentName) {
        super(owner, region, description, date, minTrials,experimentName);
        this.setType("NonNegativeCountExp");
    }

    /**
     * This is a default constructor for a Non Negative Experiment
     */
    public NonNegativeCountExp() {
        this.setType("NonNegativeCountExp");

    }
}
