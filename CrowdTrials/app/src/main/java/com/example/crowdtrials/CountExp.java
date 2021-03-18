package com.example.crowdtrials;

//import android.location.Location;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a Counts Experiment that keeps track of how many times a trial occurs.
 */
public class CountExp extends Experiment implements Serializable {
    //String type = "CountExp";

    /**
     * This is a default constructor for a Counts Experiment
     */
    public CountExp(){
        this.type = "CountExp";
    }

    /**
     * This is a constructor that sets the type of the experiment to CountExp
     * @param owner
     * The owner of the experiment
     * @param region
     * The region in which experiment takes place
     * @param description
     * The description of the experiment
     * @param date
     * The date when experiment is done
     * @param minTrials
     * The minimum number of trials to be done for the experiment
     * @param experimentName
     * The name of the experiment
     */
    public CountExp(User owner, Location region, String description, Date date, int minTrials,String experimentName) {
        super(owner, region, description, date, minTrials,experimentName);


        this.type = "CountExp";
    }
}
