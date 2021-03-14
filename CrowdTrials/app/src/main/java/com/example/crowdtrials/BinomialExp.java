package com.example.crowdtrials;

import android.location.Location;

import java.util.Date;

public class BinomialExp extends Experiment {
    float probability;
    public BinomialExp(User owner, String name, Location region, String description, Date date, int minTrials) {
        super(owner,name, region, description, date, minTrials);
        this.type = "Binomial Exp";
    }


    public BinomialExp() {

    }
    public boolean genResult()
    {
        return Math.random() >= 1.0 - probability;
    }
}
