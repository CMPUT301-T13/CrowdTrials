package com.example.crowdtrials;

import android.location.Location;

import java.util.Date;

public class NonNegativeCountExp extends Experiment{

    public NonNegativeCountExp(User owner, Location region, String description, Date date, int minTrials) {
        super(owner, region, description, date, minTrials);
        this.type = "NonNegativeCountExp";
    }

    public NonNegativeCountExp() {

    }
}
