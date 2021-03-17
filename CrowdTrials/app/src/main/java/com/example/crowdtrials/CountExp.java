package com.example.crowdtrials;

import android.location.Location;

import java.util.Date;

public class CountExp extends Experiment {

    public CountExp(){

    }



    public CountExp(User owner, Location region, String description, Date date, int minTrials,String experimentName) {
        super(owner, region, description, date, minTrials,experimentName);


        this.type = "CountExp";
    }
}
