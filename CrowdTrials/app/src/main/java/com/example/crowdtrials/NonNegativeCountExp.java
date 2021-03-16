package com.example.crowdtrials;

import android.location.Location;

import java.util.Date;

public class NonNegativeCountExp extends Experiment{

    public NonNegativeCountExp(User owner,String name, Location region, String description, Date date, int minTrials) {
        super(owner, name, region, description, date, minTrials);
        this.type = "NonNegativeCountExp";
    }

    public NonNegativeCountExp() {

    }
}
