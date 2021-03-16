package com.example.crowdtrials;

import android.location.Location;

import java.util.Date;

public class BinomialExp extends Experiment {
    float probability;
    public BinomialExp(User owner, Location region, String description, Date date, int minTrials,String experimentName) {
        super(owner, region, description, date, minTrials, experimentName);
        this.type = "Binomial Exp";
    }


    public BinomialExp() {

    }
}
