package com.example.crowdtrials;

import android.location.Location;

import java.util.Date;

public class BinomialExp extends Experiment {
    float probability;
    public BinomialExp(){
        super();
    }

    public BinomialExp(Owner owner, Location region, String description, Date date, int minTrials) {
        super(owner, region, description, date, minTrials);
        this.type = "Binomial Exp";
    }


}
