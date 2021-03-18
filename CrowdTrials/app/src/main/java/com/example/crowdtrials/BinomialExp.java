package com.example.crowdtrials;

//import android.location.Location;

import java.io.Serializable;
import java.util.Date;

public class BinomialExp extends Experiment implements Serializable {
    float probability;
    //String type = "Binomial Exp";
    public BinomialExp(User owner, Location region, String description, Date date, int minTrials,String experimentName) {
        super(owner, region, description, date, minTrials, experimentName);

        this.type = "Binomial Exp";
    }


    public BinomialExp() {
        this.type = "Binomial Exp";
    }
    public boolean genResult()
    {
        return Math.random() >= 1.0 - probability;
    }
}
