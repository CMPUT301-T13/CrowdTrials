package com.example.crowdtrials;

import android.location.Location;

import java.util.Date;

public class MeasurementExp extends Experiment{

    public MeasurementExp(){

    }

    public MeasurementExp(User owner,String name, Location region, String description, Date date, int minTrials) {
        super(owner,name, region, description, date, minTrials);

        this.type = "MeasurementExp";

    }
}
