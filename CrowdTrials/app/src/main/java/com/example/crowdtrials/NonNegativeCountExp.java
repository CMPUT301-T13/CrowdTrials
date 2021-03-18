package com.example.crowdtrials;

//import android.location.Location;

import java.io.Serializable;
import java.util.Date;

public class NonNegativeCountExp extends Experiment implements Serializable {
    //String type = "NonNegativeCountExp";
    public NonNegativeCountExp(User owner, Location region, String description, Date date, int minTrials,String experimentName) {
        super(owner, region, description, date, minTrials,experimentName);

        this.type = "NonNegativeCountExp";
    }

    public NonNegativeCountExp() {
        this.type = "NonNegativeCountExp";
    }
}
