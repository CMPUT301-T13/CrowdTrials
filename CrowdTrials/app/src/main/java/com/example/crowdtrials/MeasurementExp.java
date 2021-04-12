package com.example.crowdtrials;

//import android.location.Location;

import java.io.Serializable;
import java.util.Date;

/**
 * This class holds a Measurement Experiment.
 */
public class MeasurementExp extends Experiment implements Serializable {
   // String type="MeasurementExp";

    /**
     * This is a default constructor for a measurement experiment
     */
    public MeasurementExp(){
        this.setType("Measurement Experiment");

    }

    /**
     * This is a constructor for a Measurement Experiment object
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
    public MeasurementExp(User owner, Location region, String description, Date date, int minTrials,String experimentName) {
        super(owner, region, description, date, minTrials,experimentName);
        this.setType("Measurement Experiment");

    }

}
