package com.example.crowdtrials;

import android.location.Location;

import java.util.Date;

public class MeasurementExp extends Experiment{

    public MeasurementExp(){

    }
    public MeasurementExp(User owner, Location region, String description, Date date, int minTrials) {
        super(owner, region, description, date, minTrials);
        this.type = "MeasurementExp";

    }
}
