package com.example.crowdtrials;

import android.location.Location;

import java.util.Date;

public class CountExp extends Experiment {
    public CountExp(Owner owner, Location region, String description, Date date, int minTrials) {
        super(owner, region, description, date, minTrials);
    }
}
